package com.unear.userservice.benefit.dto.response;

import com.unear.userservice.benefit.entity.GeneralDiscountPolicy;
import com.unear.userservice.place.entity.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GeneralDiscountPolicyDetailResponseDto {

    private Long placeId;
    private String placeName;
    private String address;
    private Integer startTime;
    private Integer endTime;

    private Integer unitBaseAmount;
    private Integer fixedDiscount;
    private Integer discountPercent;
    private Integer minPurchaseAmount;
    private Integer maxDiscountAmount;

    private String discountCode;
    private String membershipCode;
    private String markerCode;

    public static GeneralDiscountPolicyDetailResponseDto from(GeneralDiscountPolicy detail) {
        Place place = detail.getPlace();

        return GeneralDiscountPolicyDetailResponseDto.builder()
                .placeId(place.getPlaceId())
                .placeName(place.getPlaceName())
                .address(place.getAddress())
                .startTime(place.getStartTime())
                .endTime(place.getEndTime())
                .unitBaseAmount(detail.getUnitBaseAmount())
                .fixedDiscount(detail.getFixedDiscount())
                .discountPercent(detail.getDiscountPercent())
                .minPurchaseAmount(detail.getMinPurchaseAmount())
                .maxDiscountAmount(detail.getMaxDiscountAmount())
                .discountCode(detail.getDiscountCode())
                .membershipCode(detail.getMembershipCode())
                .markerCode(detail.getMarkerCode())
                .build();
    }
}

