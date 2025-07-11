package com.unear.userservice.benefit.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiscountPolicyDetailResponseDto {

    private Long placeId;
    private String placeName;
    private String address;
    private String businessHours;
    private String franchiseName;
    private String franchiseImageUrl;

    private Integer unitBaseAmount;
    private Integer discountValue;
    private Integer percent;
    private Integer minPurchaseAmount;
    private Integer maxDiscountAmount;

    private String discountCode;
    private String membershipCode;
    private String markerCode;

}

