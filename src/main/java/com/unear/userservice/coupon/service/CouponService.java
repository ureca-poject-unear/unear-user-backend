package com.unear.userservice.coupon.service;

import com.unear.userservice.coupon.dto.response.CouponResponseDto;
import com.unear.userservice.coupon.dto.response.UserCouponListResponseDto;
import com.unear.userservice.coupon.dto.response.UserCouponResponseDto;

import java.util.List;

public interface CouponService {

    List<CouponResponseDto> getCouponsByPlaceAndMarker(Long userId ,Long placeId, String markerCode);

    UserCouponResponseDto downloadCoupon(Long userId, Long couponTemplateId);

    UserCouponResponseDto downloadFCFSCoupon(Long userId, Long couponTemplateId);

    UserCouponListResponseDto getMyCoupons(Long userId);

    UserCouponResponseDto getMyCouponDetail(Long userId, Long userCouponId);

}
