package com.unear.userservice.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class RefreshResponseDto {
    private Long userId;
    private String newAccessToken;
}

