package com.unear.userservice.auth.handler;

import com.unear.userservice.common.jwt.JwtTokenProvider;
import com.unear.userservice.common.security.CustomUser;
import com.unear.userservice.user.entity.User;
import com.unear.userservice.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저 정보 없음"));

        CustomUser customUser = CustomUser.from(user);
        String accessToken = jwtTokenProvider.generateAccessToken(customUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(customUser);

        // JSON 응답
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String json = String.format("""
        {
            "accessToken": "%s",
            "refreshToken": "%s"
        }
        """, accessToken, refreshToken);

        response.getWriter().write(json);
    }
}