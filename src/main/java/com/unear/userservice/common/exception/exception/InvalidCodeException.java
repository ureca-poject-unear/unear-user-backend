package com.unear.userservice.common.exception.exception;

import com.unear.userservice.common.exception.BusinessException;
import com.unear.userservice.common.exception.ErrorCode;

public class InvalidCodeException extends BusinessException {
    public InvalidCodeException() {
        super(ErrorCode.INVALID_CODE);
    }

    public InvalidCodeException(String message) {
        super(ErrorCode.INVALID_CODE, message);
    }
}
