package com.unear.userservice.auth.dto.response;

import com.unear.userservice.user.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateResponseDto {
    private Long userId;
    private String email;
    private String username;
    private String tel;
    private LocalDateTime birthdate;
    private String gender;
    private String barcodeNumber;
    private boolean isProfileComplete;

    public static ProfileUpdateResponseDto from(User user) {
        return new ProfileUpdateResponseDto(
                user.getUserId(),
                user.getEmail(),
                user.getUsername(),
                user.getTel(),
                user.getBirthdate(),
                user.getGender(),
                user.getBarcodeNumber(),
                user.isProfileComplete()
        );
    }
}