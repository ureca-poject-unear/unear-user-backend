package com.unear.userservice.common.exception.exception;

import com.unear.userservice.common.exception.BusinessException;
import com.unear.userservice.common.exception.ErrorCode;


public class BarcodeDuplicatedException extends BusinessException {
    public BarcodeDuplicatedException() {
        super(ErrorCode.DUPLICATED_BARCODE);
    }

    public BarcodeDuplicatedException(String message) {
        super(ErrorCode.DUPLICATED_BARCODE, message);
    }
}