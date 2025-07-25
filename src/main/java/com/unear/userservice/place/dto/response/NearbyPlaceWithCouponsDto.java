package com.unear.userservice.place.dto.response;

import com.unear.userservice.coupon.dto.response.CouponResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NearbyPlaceWithCouponsDto {

    private Long placeId;
    private String name;
    private String address;
    private String categoryCode;
    private Double distanceKm;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer startTime;
    private Integer endTime;
    private Boolean favorite;
    private String markerCode;
    private String eventTypeCode;
    private String tel;

    private Long discountPolicyDetailId;
    private Long franchiseId;

    private String benefitDesc;

    private List<CouponResponseDto> coupons;
}
