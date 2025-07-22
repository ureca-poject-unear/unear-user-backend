package com.unear.userservice.common.exception.exception;


import com.unear.userservice.common.exception.BusinessException;
import com.unear.userservice.common.exception.ErrorCode;

public class EmailNotVerifiedException extends BusinessException {
    public EmailNotVerifiedException() {
        super(ErrorCode.EMAIL_NOT_VERIFIED);
    }
    public EmailNotVerifiedException(String message) {
        super(ErrorCode.EMAIL_NOT_VERIFIED, message);
    }
}
