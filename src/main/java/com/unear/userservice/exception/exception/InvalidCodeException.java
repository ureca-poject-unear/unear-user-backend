package com.unear.userservice.exception.exception;

import com.unear.userservice.exception.BusinessException;
import com.unear.userservice.exception.ErrorCode;

public class InvalidCodeException extends BusinessException {
    public InvalidCodeException() {
        super(ErrorCode.INVALID_CODE);
    }

    public InvalidCodeException(String message) {
        super(ErrorCode.INVALID_CODE, message);
    }
}
