package com.unear.userservice.common.exception.exception;

import com.unear.userservice.common.exception.BusinessException;
import com.unear.userservice.common.exception.ErrorCode;


public class BenefitNotFoundException extends BusinessException {
    public BenefitNotFoundException() {
        super(ErrorCode.BENEFIT_NOT_FOUND);
    }

    public BenefitNotFoundException(String message) {
        super(ErrorCode.BENEFIT_NOT_FOUND, message);
    }
}