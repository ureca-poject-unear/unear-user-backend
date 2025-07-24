package com.unear.userservice.user.dto.response;

import com.unear.userservice.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDto {
    private Long userId;
    private String email;
    private String username;
    private String tel;
    private LocalDateTime birthdate;
    private String gender;
    private String membershipCode;
    private String provider;
    private String barcodeNumber;
    private Boolean isProfileComplete;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserInfoResponseDto from(User user) {
        return UserInfoResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .tel(user.getTel())
                .birthdate(user.getBirthdate())
                .gender(user.getGender())
                .membershipCode(user.getMembershipCode())
                .provider(user.getProvider().name())
                .barcodeNumber(user.getBarcodeNumber())
                .isProfileComplete(user.isProfileComplete())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

