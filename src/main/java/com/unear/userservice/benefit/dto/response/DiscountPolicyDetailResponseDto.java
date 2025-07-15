package com.unear.userservice.benefit.dto.response;

import com.unear.userservice.benefit.entity.DiscountPolicyDetail;
import com.unear.userservice.place.entity.Franchise;
import com.unear.userservice.place.entity.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiscountPolicyDetailResponseDto {

    private Long placeId;
    private String placeName;
    private String address;
    private Integer startTime;
    private Integer endTime;

    private Integer unitBaseAmount;
    private Integer discountValue;
    private Integer percent;
    private Integer minPurchaseAmount;
    private Integer maxDiscountAmount;

    private String discountCode;
    private String membershipCode;
    private String markerCode;

    public static DiscountPolicyDetailResponseDto from(DiscountPolicyDetail detail) {
        Place place = detail.getPlace();

        return DiscountPolicyDetailResponseDto.builder()
                .placeId(place.getPlacesId())
                .placeName(place.getPlaceName())
                .address(place.getAddress())
                .startTime(place.getStartTime())
                .endTime(place.getEndTime())
                .unitBaseAmount(detail.getUnitBaseAmount())
                .discountValue(detail.getDiscountValue())
                .percent(detail.getPercent())
                .minPurchaseAmount(detail.getMinPurchaseAmount())
                .maxDiscountAmount(detail.getMaxDiscountAmount())
                .discountCode(detail.getDiscountCode())
                .membershipCode(detail.getMembershipCode())
                .markerCode(detail.getMarkerCode())
                .build();
    }


}

