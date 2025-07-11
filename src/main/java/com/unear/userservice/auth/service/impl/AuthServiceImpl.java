package com.unear.userservice.auth.service.impl;

import com.unear.userservice.auth.dto.request.LoginRequestDto;
import com.unear.userservice.auth.dto.request.ResetPasswordRequestDto;
import com.unear.userservice.auth.dto.request.SignupRequestDto;
import com.unear.userservice.auth.dto.response.LoginResponseDto;
import com.unear.userservice.auth.dto.response.LogoutResponseDto;
import com.unear.userservice.auth.dto.response.RefreshResponseDto;
import com.unear.userservice.auth.dto.response.SignupResponseDto;
import com.unear.userservice.auth.service.AuthService;
import com.unear.userservice.common.jwt.JwtTokenProvider;
import com.unear.userservice.common.jwt.RefreshTokenService;
import com.unear.userservice.common.response.ApiResponse;
import com.unear.userservice.common.security.CustomUser;
import com.unear.userservice.common.security.CustomUserDetailsService;
import com.unear.userservice.exception.exception.DuplicatedEmailException;
import com.unear.userservice.exception.exception.InvalidPasswordException;
import com.unear.userservice.exception.exception.InvalidTokenException;
import com.unear.userservice.exception.exception.UserNotFoundException;
import com.unear.userservice.user.entity.User;
import com.unear.userservice.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public ApiResponse<LoginResponseDto> login(LoginRequestDto loginRequest, HttpServletResponse response) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 올바르지 않습니다.");
        }

        CustomUser customUser = new CustomUser(user);
        String accessToken = jwtTokenProvider.generateAccessToken(customUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(customUser);

        refreshTokenService.saveRefreshToken(user.getUserId(), refreshToken);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        LoginResponseDto dto = LoginResponseDto.of(user.getEmail(), accessToken);

        return ApiResponse.success("로그인 완료", dto);

    }

    @Override
    public ApiResponse<RefreshResponseDto> refresh(String refreshToken) {

        Long userId = jwtTokenProvider.extractUserId(refreshToken);

        if (!refreshTokenService.validateRefreshToken(userId, refreshToken)) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        CustomUser customUser = customUserDetailsService.loadUserByUserId(userId);

        String newAccessToken = jwtTokenProvider.generateAccessToken(customUser);

        RefreshResponseDto responseDto = new RefreshResponseDto(userId, newAccessToken);
        return ApiResponse.success("재발급 완료", responseDto);
    }

    @Override
    public ApiResponse<LogoutResponseDto> logout(String accessTokenHeader, HttpServletResponse response) {

        if (accessTokenHeader == null || !accessTokenHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("유효하지 않은 토큰 형식입니다");
        }

        String accessToken = accessTokenHeader.replace("Bearer ", "");
        Long userId = jwtTokenProvider.extractUserId(accessToken);

        refreshTokenService.deleteRefreshToken(userId);
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        LogoutResponseDto dto = new LogoutResponseDto(userId);

        return ApiResponse.success("로그아웃이 완료되었습니다", dto);

    }

    public ApiResponse<SignupResponseDto> signup(SignupRequestDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicatedEmailException("이미 가입된 이메일입니다.");
        }

        User user = User.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .tel(dto.getTel())
                .birthdate(dto.getBirthdate())
                .gender(dto.getGender())
                .membershipCode("001")
                .isProfileComplete(false)
                .build();

        userRepository.save(user);

        SignupResponseDto responseDto = new SignupResponseDto(
                user.getUserId(),
                user.getEmail(),
                user.getUsername()
        );

        return ApiResponse.success("회원가입 성공", responseDto);
    }

    @Override
    public void resetPassword(ResetPasswordRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        String newEncodedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(newEncodedPassword);
        userRepository.save(user);
    }

}
