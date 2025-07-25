package com.unear.userservice.auth.service.impl;

import com.unear.userservice.auth.dto.request.CompleteProfileRequestDto;
import com.unear.userservice.auth.dto.request.LoginRequestDto;
import com.unear.userservice.auth.dto.request.ResetPasswordRequestDto;
import com.unear.userservice.auth.dto.request.SignupRequestDto;
import com.unear.userservice.auth.dto.response.LoginResponseDto;
import com.unear.userservice.auth.dto.response.LogoutResponseDto;
import com.unear.userservice.auth.dto.response.ProfileUpdateResponseDto;
import com.unear.userservice.auth.dto.response.RefreshResponseDto;
import com.unear.userservice.auth.dto.response.SignupResponseDto;
import com.unear.userservice.auth.service.AuthService;
import com.unear.userservice.common.enums.LoginProvider;
import com.unear.userservice.common.jwt.JwtTokenProvider;
import com.unear.userservice.common.jwt.RefreshTokenService;
import com.unear.userservice.common.response.ApiResponse;
import com.unear.userservice.common.security.CustomUser;
import com.unear.userservice.common.security.CustomUserDetailsService;
import com.unear.userservice.common.exception.exception.DuplicatedEmailException;
import com.unear.userservice.common.exception.exception.InvalidPasswordException;
import com.unear.userservice.common.exception.exception.InvalidTokenException;
import com.unear.userservice.common.exception.exception.UserNotFoundException;
import com.unear.userservice.user.entity.User;
import com.unear.userservice.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

        log.info("일반 로그인 로그");
        System.out.println("일반 로그인 로그");
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
    public ApiResponse<LoginResponseDto> oauthLogin(User user, HttpServletResponse response) {
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
        String barcode = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

        User user = User.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .tel(dto.getTel())
                .birthdate(dto.getBirthdate())
                .gender(dto.getGender())
                .membershipCode("BASIC")
                .isProfileComplete(true)
                .barcodeNumber(barcode)
                .build();

        userRepository.save(user);

        SignupResponseDto responseDto = new SignupResponseDto(
                user.getUserId(),
                user.getEmail(),
                user.getUsername(),
                user.getBarcodeNumber()
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

    @Override
    public ApiResponse<ProfileUpdateResponseDto> completeOAuthProfile(Long userId, CompleteProfileRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));

        if (user.getProvider() == LoginProvider.EMAIL) {
            throw new RuntimeException("OAuth 유저만 가능합니다");
        }

        String barcode = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

        user.setUsername(request.getUsername());
        user.setTel(request.getTel());
        user.setBirthdate(request.getBirthdate());
        user.setGender(request.getGender());
        user.setBarcodeNumber(barcode);
        user.setProfileComplete(true);

        userRepository.save(user);

        return ApiResponse.success("프로필 완성", ProfileUpdateResponseDto.from(user));
    }

    @Override
    public User getUserFromAccessToken(String accessToken) {
        if (accessToken == null || accessToken.trim().isEmpty()) {
            throw new InvalidTokenException("액세스 토큰이 유효하지 않습니다");
        }
        Long userId = jwtTokenProvider.extractUserId(accessToken);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));
    }
}
