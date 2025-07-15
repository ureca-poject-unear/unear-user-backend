package com.unear.userservice.common.config;

import com.unear.userservice.auth.handler.OAuth2FailureHandler;
import com.unear.userservice.auth.handler.OAuth2SuccessHandler;
import com.unear.userservice.auth.service.impl.CustomOAuth2UserService;
import com.unear.userservice.auth.service.impl.GoogleOAuth2UserService;
import com.unear.userservice.auth.service.impl.KakaoOAuth2UserService;
import com.unear.userservice.common.jwt.JwtAuthenticationFilter;
import com.unear.userservice.common.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DelegatingOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final GoogleOAuth2UserService googleOAuth2UserService;
    private final KakaoOAuth2UserService kakaoOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;

    private static final String[] WHITE_LIST = {
            "/auth/**",
            "/oauth2/**"
    };

    /**
     * Spring Security의 필터 체인을 구성하여 인증 및 인가 정책, OAuth2 로그인, JWT 인증 필터를 설정합니다.
     *
     * 인증이 필요 없는 URL 패턴(화이트리스트)은 모두 허용하며, 그 외 모든 요청은 인증이 필요합니다.
     * OAuth2 로그인 시 커스텀 사용자 서비스와 성공/실패 핸들러를 사용하고, JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가합니다.
     *
     * @return 구성된 SecurityFilterChain 인스턴스
     * @throws Exception 보안 설정 중 오류가 발생할 경우
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // google + kakao 둘 다 지원
                        )
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return new DelegatingOAuth2UserService<>(List.of(
                googleOAuth2UserService,
                kakaoOAuth2UserService
        ));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
