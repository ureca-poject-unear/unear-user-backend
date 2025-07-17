package com.unear.userservice.exception.exception;

import com.unear.userservice.exception.BusinessException;
import com.unear.userservice.exception.ErrorCode;


public class BarcodeDuplicatedException extends BusinessException {
    public BarcodeDuplicatedException() {
        super(ErrorCode.DUPLICATED_BARCODE);
    }

    public BarcodeDuplicatedException(String message) {
        super(ErrorCode.DUPLICATED_BARCODE, message);
    }
}