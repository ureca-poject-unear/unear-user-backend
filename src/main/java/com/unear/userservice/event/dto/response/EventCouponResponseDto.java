package com.unear.userservice.event.dto.response;

import com.unear.userservice.coupon.entity.CouponTemplate;

import java.time.LocalDate;

public record EventCouponResponseDto(
        Long couponId,
        String couponName,
        LocalDate startDate,
        LocalDate endDate
) {
    public static EventCouponResponseDto from(CouponTemplate coupon) {
        return new EventCouponResponseDto(
                coupon.getCouponTemplateId(),
                coupon.getCouponName(),
                coupon.getCouponStart().toLocalDate(),
                coupon.getCouponEnd().toLocalDate()
        );
    }
}
