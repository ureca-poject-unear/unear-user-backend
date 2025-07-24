package com.unear.userservice.auth.handler;

import com.unear.userservice.auth.CookieUtil;
import com.unear.userservice.auth.dto.response.LoginResponseDto;
import com.unear.userservice.auth.service.AuthService;
import com.unear.userservice.common.exception.exception.UserNotFoundException;
import com.unear.userservice.common.jwt.JwtTokenProvider;
import com.unear.userservice.common.jwt.RefreshTokenService;
import com.unear.userservice.common.response.ApiResponse;
import com.unear.userservice.common.security.CustomUser;
import com.unear.userservice.user.entity.User;
import com.unear.userservice.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Value("${app.frontend.base-url:http://localhost:4000}")
    private String frontendBaseUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("유저 정보 없음"));

        CustomUser customUser = new CustomUser(user);
        String accessToken = jwtTokenProvider.generateAccessToken(customUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(customUser);

        refreshTokenService.saveRefreshToken(user.getUserId(), refreshToken);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        log.info("onAuthenticationSuccess 리다이렉트 지점 ");
        System.out.println("onAuthenticationSuccess 리다이렉트 지점 ");

        String redirectUrl = String.format("%s/login/oauth2/code/google?accessToken=%s",
                frontendBaseUrl, accessToken);

        response.sendRedirect(redirectUrl);
    }
}