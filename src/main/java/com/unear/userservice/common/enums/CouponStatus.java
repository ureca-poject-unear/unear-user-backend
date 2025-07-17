package com.unear.userservice.common.enums;

import com.unear.userservice.exception.exception.InvalidCodeException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CouponStatus {
    UNUSED("UNUSED", "미사용"),
    USED("USED", "사용됨"),
    EXPIRED("EXPIRED", "기간만료");

    private final String code;
    private final String label;

    CouponStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static CouponStatus fromCode(String code) {
        return Arrays.stream(values())
                .filter(c -> c.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new InvalidCodeException("Invalid coupon status code: " + code));
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}

