package com.unear.userservice.exception.exception;


import com.unear.userservice.exception.BusinessException;
import com.unear.userservice.exception.ErrorCode;

public class EmailNotVerifiedException extends BusinessException {
    public EmailNotVerifiedException() {
        super(ErrorCode.EMAIL_NOT_VERIFIED);
    }
    public EmailNotVerifiedException(String message) {
        super(ErrorCode.EMAIL_NOT_VERIFIED, message);
    }
}
