package com.unear.userservice.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SignupRequestDto {

    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String username;

    @NotBlank(message = "전화번호는 필수 항목입니다.")
    private String tel;

    @NotNull(message = "생년월일은 필수 항목입니다.")
    private LocalDateTime birthdate;

    @NotBlank(message = "성별은 필수 항목입니다.")
    private String gender;

}
