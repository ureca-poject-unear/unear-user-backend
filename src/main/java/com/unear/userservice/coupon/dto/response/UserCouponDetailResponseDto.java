package com.unear.userservice.coupon.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserCouponDetailResponseDto {

    private Long userCouponId;
    private String couponName;
    private String barcodeNumber;
    private String couponStatusCode;
    private LocalDate createdAt;
    private LocalDate couponEnd;

    private String discountCode;
    private String membershipCode;
    private Integer unitBaseAmount;
    private Integer fixedDiscount;
    private Integer discountPercent;
    private Integer minPurchaseAmount;
    private Integer maxDiscountAmount;

    private String markerCode;
}

