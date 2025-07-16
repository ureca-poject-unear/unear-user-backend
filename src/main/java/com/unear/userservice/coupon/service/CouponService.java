package com.unear.userservice.coupon.service;

import com.unear.userservice.coupon.dto.response.CouponResponseDto;

import java.util.List;

public interface CouponService {

    List<CouponResponseDto> getCouponsByPlaceAndMarker(Long userId ,Long placeId, String markerCode);

}
