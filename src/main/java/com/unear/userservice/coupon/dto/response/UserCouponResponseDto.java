package com.unear.userservice.coupon.dto.response;

import com.unear.userservice.coupon.entity.UserCoupon;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserCouponResponseDto {
    private Long userCouponId;
    private String couponName;
    private String barcodeNumber;
    private String couponStatusCode;
    private LocalDateTime createdAt;
    private LocalDateTime couponEnd;

    public static UserCouponResponseDto from(UserCoupon userCoupon) {
        return UserCouponResponseDto.builder()
                .userCouponId(userCoupon.getUserCouponId())
                .couponName(userCoupon.getCouponTemplate() != null
                        ? userCoupon.getCouponTemplate().getCouponName()
                        : null)
                .barcodeNumber(userCoupon.getBarcodeNumber())
                .couponStatusCode(userCoupon.getCouponStatusCode())
                .createdAt(userCoupon.getCreatedAt())
                .couponEnd(userCoupon.getCouponTemplate() != null
                        ? userCoupon.getCouponTemplate().getCouponEnd()
                        : null)
                .build();
    }
}

