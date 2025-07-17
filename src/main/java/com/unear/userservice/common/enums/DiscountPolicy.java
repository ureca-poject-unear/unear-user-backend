package com.unear.userservice.common.enums;

import com.unear.userservice.exception.exception.InvalidCodeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DiscountPolicy {

    COUPON_FIXED("COUPON_FIXED", "(쿠폰) 금액 할인"),
    COUPON_PERCENT("COUPON_PERCENT", "(쿠폰) 퍼센트 할인"),
    MEMBERSHIP_UNIT("MEMBERSHIP_UNIT", "(멤버십) 금액당 할인"),
    MEMBERSHIP_FIXED("MEMBERSHIP_FIXED", "(멤버십) 금액 할인"),
    COUPON_FCFS("COUPON_FCFS", "선착순 전용 쿠폰");

    private final String code;
    private final String label;

    DiscountPolicy(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static DiscountPolicy fromCode(String code) {
        return Arrays.stream(values())
                .filter(p -> p.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new InvalidCodeException("Invalid DiscountPolicy code: " + code));
    }
}

