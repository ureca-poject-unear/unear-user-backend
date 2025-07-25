package com.unear.userservice.common.resolver;

import com.unear.userservice.common.annotation.LoginUser;
import com.unear.userservice.common.exception.exception.InvalidTokenException;
import com.unear.userservice.user.entity.User;
import com.unear.userservice.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService; // 토큰 기반으로 유저를 찾는 로직을 작성한 서비스

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class)
                && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  org.springframework.web.bind.support.WebDataBinderFactory binderFactory) {
        HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
        
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("유효하지 않은 인증 헤더입니다");
        }

        String accessToken = authHeader.replace("Bearer ", "");
        return authService.getUserFromAccessToken(accessToken);
    }
}
