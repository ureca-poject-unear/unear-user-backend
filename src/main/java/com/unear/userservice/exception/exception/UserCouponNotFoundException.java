package com.unear.userservice.exception.exception;

import com.unear.userservice.exception.BusinessException;
import com.unear.userservice.exception.ErrorCode;

public class UserCouponNotFoundException extends BusinessException {
    public UserCouponNotFoundException() {
        super(ErrorCode.USER_COUPON_NOT_FOUND);
    }

    public UserCouponNotFoundException(String message) {
        super(ErrorCode.USER_COUPON_NOT_FOUND, message);
    }
}