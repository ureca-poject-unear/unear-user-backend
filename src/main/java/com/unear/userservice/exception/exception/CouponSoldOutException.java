package com.unear.userservice.exception.exception;

import com.unear.userservice.exception.BusinessException;
import com.unear.userservice.exception.ErrorCode;

public class CouponSoldOutException extends BusinessException {
    public CouponSoldOutException() {
        super(ErrorCode.COUPON_SOLD_OUT);
    }
    public CouponSoldOutException(String message) {
        super(ErrorCode.COUPON_SOLD_OUT, message);
    }
}