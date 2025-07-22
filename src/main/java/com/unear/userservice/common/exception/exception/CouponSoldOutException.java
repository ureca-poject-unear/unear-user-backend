package com.unear.userservice.common.exception.exception;

import com.unear.userservice.common.exception.BusinessException;
import com.unear.userservice.common.exception.ErrorCode;

public class CouponSoldOutException extends BusinessException {
    public CouponSoldOutException() {
        super(ErrorCode.COUPON_SOLD_OUT);
    }
    public CouponSoldOutException(String message) {
        super(ErrorCode.COUPON_SOLD_OUT, message);
    }
}