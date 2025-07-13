package com.unear.userservice.auth.service.impl;

import com.unear.userservice.auth.service.impl.GoogleOAuth2UserService;
import com.unear.userservice.auth.service.impl.KakaoOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final GoogleOAuth2UserService googleOAuth2UserService;
    private final KakaoOAuth2UserService kakaoOAuth2UserService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        return switch (registrationId) {
            case "google" -> googleOAuth2UserService.loadUser(userRequest);
            case "kakao" -> kakaoOAuth2UserService.loadUser(userRequest);
            default -> throw new OAuth2AuthenticationException("Unsupported OAuth2 provider: " + registrationId);
        };
    }
}
