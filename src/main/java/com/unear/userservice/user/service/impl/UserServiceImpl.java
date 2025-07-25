package com.unear.userservice.user.service.impl;

import com.unear.userservice.common.exception.BusinessException;
import com.unear.userservice.common.exception.ErrorCode;
import com.unear.userservice.user.dto.response.UserInfoResponseDto;
import com.unear.userservice.user.entity.User;
import com.unear.userservice.user.repository.UserRepository;
import com.unear.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserInfoResponseDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return UserInfoResponseDto.from(user);
    }

    @Override
    public String getMembershipBarcode(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return user.getBarcodeNumber();
    }
}
