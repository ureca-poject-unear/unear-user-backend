package com.unear.userservice.user.service;


import com.unear.userservice.user.dto.response.UserInfoResponseDto;

public interface UserService {
    UserInfoResponseDto getUserInfo(Long userId);
    String getMembershipBarcode(Long userId);
}