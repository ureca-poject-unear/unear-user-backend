package com.unear.userservice.common.exception;

public class BusinessException extends BaseException{

    public BusinessException(ErrorCode errorCode) { super(errorCode); }

    public BusinessException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
