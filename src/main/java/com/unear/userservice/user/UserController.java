package com.unear.userservice.user;

import com.unear.userservice.common.response.ApiResponse;
import com.unear.userservice.common.security.CustomUser;
import com.unear.userservice.exception.exception.UserNotFoundException;
import com.unear.userservice.user.entity.User;
import com.unear.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private static final String USER_NOT_FOUND_MESSAGE = "사용자를 찾을 수 없습니다.";
    private static final String BARCODE_SUCCESS_MESSAGE = "바코드 조회 성공";

    private final UserRepository userRepository;

    @GetMapping("/me/barcode")
    public ApiResponse<String> getMyBarcode(@AuthenticationPrincipal CustomUser user) {

        User dbuser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        return ApiResponse.success(BARCODE_SUCCESS_MESSAGE, dbuser.getBarcodeNumber());
    }


}
