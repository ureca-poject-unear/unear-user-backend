package com.unear.userservice.common.exception.exception;

import com.unear.userservice.common.exception.BusinessException;
import com.unear.userservice.common.exception.ErrorCode;


public class CouponTemplateNotFoundException extends BusinessException {
    public CouponTemplateNotFoundException() {
        super(ErrorCode.COUPON_TEMPLATE_NOT_FOUND);
    }
    public CouponTemplateNotFoundException(String message) {
        super(ErrorCode.COUPON_TEMPLATE_NOT_FOUND, message);
    }
}