package com.unear.userservice.coupon.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserCouponDetailResponseDto {

    private Long userCouponId;
    private String couponName;
    private String barcodeNumber;
    private String couponStatusCode;
    private LocalDateTime createdAt;
    private LocalDateTime couponEnd;

    private String discountCode;
    private String membershipCode;
    private Integer unitBaseAmount;
    private Integer fixedDiscount;
    private Integer discountPercent;
    private Integer minPurchaseAmount;
    private Integer maxDiscountAmount;

    private String markerCode;
}

