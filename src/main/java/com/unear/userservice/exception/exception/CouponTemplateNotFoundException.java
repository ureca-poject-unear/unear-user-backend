package com.unear.userservice.exception.exception;

import com.unear.userservice.exception.BusinessException;
import com.unear.userservice.exception.ErrorCode;


public class CouponTemplateNotFoundException extends BusinessException {
    public CouponTemplateNotFoundException() {
        super(ErrorCode.COUPON_TEMPLATE_NOT_FOUND);
    }
    public CouponTemplateNotFoundException(String message) {
        super(ErrorCode.COUPON_TEMPLATE_NOT_FOUND, message);
    }
}