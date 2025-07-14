package com.unear.userservice.exception.exception;

import com.unear.userservice.exception.BusinessException;
import com.unear.userservice.exception.ErrorCode;


public class PlaceNotFoundException extends BusinessException {
    public PlaceNotFoundException() {
        super(ErrorCode.PLACE_NOT_FOUND);
    }

    public PlaceNotFoundException(String message) {
        super(ErrorCode.PLACE_NOT_FOUND, message);
    }
}