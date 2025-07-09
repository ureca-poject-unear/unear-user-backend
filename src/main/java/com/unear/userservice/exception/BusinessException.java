package com.unear.userservice.exception;

public class BusinessException extends BaseException{

    public BusinessException(ErrorCode errorCode) { super(errorCode); }

    public BusinessException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
