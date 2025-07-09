package com.unear.userservice.common.response;

import com.unear.userservice.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private int resultCode;     // 예: 200, 404, 500
    private String codeName;    // 예: SUCCESS, USER_NOT_FOUND
    private String message;     // 예: 성공 메시지, 에러 메시지
    private T data;             // 성공 시 반환할 데이터

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200,"SUCCESS", message, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return success("요청이 성공적으로 처리되었습니다.", data);
    }

    public static <T> ApiResponse<T> fail(ErrorCode errorCode) {
        return new ApiResponse<>(
                errorCode.getStatus().value(),
                errorCode.getCode(),  // name() 대신 getCode() 사용
                errorCode.getMessage(),
                null
        );
    }
}