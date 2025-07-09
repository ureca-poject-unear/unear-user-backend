package com.unear.userservice.auth.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private String message;
    private String email;
    private String accessToken;

    public static LoginResponseDto of(String email, String accessToken) {
        return new LoginResponseDto("로그인이 완료되었습니다", email, accessToken);
    }
}
