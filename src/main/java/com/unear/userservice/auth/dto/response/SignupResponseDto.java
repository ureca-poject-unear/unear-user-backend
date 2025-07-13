package com.unear.userservice.auth.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponseDto {
    private Long userId;
    private String email;
    private String username;
    private String barcodeNumber;
}