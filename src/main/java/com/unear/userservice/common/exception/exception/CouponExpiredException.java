package com.unear.userservice.common.exception.exception;

import com.unear.userservice.common.exception.BusinessException;
import com.unear.userservice.common.exception.ErrorCode;

public class CouponExpiredException extends BusinessException {
    public CouponExpiredException() {
        super(ErrorCode.COUPON_EXPIRED);
    }
    public CouponExpiredException(String message) {
        super(ErrorCode.COUPON_EXPIRED, message);
    }
}