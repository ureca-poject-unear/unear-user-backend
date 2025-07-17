package com.unear.userservice.exception.exception;

import com.unear.userservice.exception.BusinessException;
import com.unear.userservice.exception.ErrorCode;


public class CouponAlreadyDownloadedException extends BusinessException {
    public CouponAlreadyDownloadedException() {
        super(ErrorCode.COUPON_ALREADY_DOWNLOADED);
    }

    public CouponAlreadyDownloadedException(String message) {
        super(ErrorCode.COUPON_ALREADY_DOWNLOADED, message);
    }
}
