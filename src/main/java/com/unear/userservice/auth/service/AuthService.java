package com.unear.userservice.auth.service;

import com.unear.userservice.auth.dto.request.LoginRequestDto;
import com.unear.userservice.auth.dto.response.LoginResponseDto;
import com.unear.userservice.auth.dto.response.LogoutResponseDto;
import com.unear.userservice.auth.dto.response.RefreshResponseDto;
import com.unear.userservice.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    ApiResponse<LoginResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response);

    ApiResponse<RefreshResponseDto> refresh(String refreshTokenHeader);

    ApiResponse<LogoutResponseDto> logout(String accessTokenHeader, HttpServletResponse response);
}
