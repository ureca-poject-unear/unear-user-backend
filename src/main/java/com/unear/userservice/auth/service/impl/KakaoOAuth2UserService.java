package com.unear.userservice.auth.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unear.userservice.user.entity.User;
import com.unear.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        if (!"kakao".equals(registrationId)) {
            throw new OAuth2AuthenticationException("KakaoOAuth2UserService cannot handle: " + registrationId);
        }

        OAuth2User user = super.loadUser(userRequest);
        Map<String, Object> attributes = user.getAttributes();

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String email = (String) kakaoAccount.get("email");
        String name = (String) profile.get("nickname");

        if (email == null) {
            throw new OAuth2AuthenticationException("카카오 계정에서 이메일 정보를 가져올 수 없습니다. 이메일 제공에 동의했는지 확인해주세요.");
        }

        User savedUser = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .email(email)
                                .username(name)
                                .password(null)
                                .tel(null)
                                .birthdate(null)
                                .gender(null)
                                .membershipCode("001")
                                .isProfileComplete(true)
                                .build()
                ));

        // attributes 맵에 email을 직접 추가
        Map<String, Object> customAttributes = new HashMap<>(attributes);
        customAttributes.put("email", email);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                customAttributes,
                "email" // 반드시 위에서 put한 key와 일치해야 함
        );
    }
}
