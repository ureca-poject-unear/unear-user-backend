package com.unear.userservice.exception.exception;

import com.unear.userservice.exception.BusinessException;
import com.unear.userservice.exception.ErrorCode;

public class CouponExpiredException extends BusinessException {
    public CouponExpiredException() {
        super(ErrorCode.COUPON_EXPIRED);
    }
    public CouponExpiredException(String message) {
        super(ErrorCode.COUPON_EXPIRED, message);
    }
}