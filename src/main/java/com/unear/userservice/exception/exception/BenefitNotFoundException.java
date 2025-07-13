package com.unear.userservice.exception.exception;

import com.unear.userservice.exception.BusinessException;
import com.unear.userservice.exception.ErrorCode;


public class BenefitNotFoundException extends BusinessException {
    public BenefitNotFoundException() {
        super(ErrorCode.BENEFIT_NOT_FOUND);
    }

    public BenefitNotFoundException(String message) {
        super(ErrorCode.BENEFIT_NOT_FOUND, message);
    }
}