package com.unear.userservice.coupon.controller;


import com.unear.userservice.common.response.ApiResponse;
import com.unear.userservice.common.security.CustomUser;
import com.unear.userservice.coupon.dto.response.CouponResponseDto;
import com.unear.userservice.coupon.dto.response.UserCouponResponseDto;
import com.unear.userservice.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/{placeId}")
    public ResponseEntity<ApiResponse<List<CouponResponseDto>>> getCouponsByPlace(
            @PathVariable("placeId") Long placeId,
            @RequestParam String markerCode,
            @AuthenticationPrincipal CustomUser user
    ) {
        Long userId = (user != null && user.getUser() != null) ? user.getUser().getUserId() : null;
        List<CouponResponseDto> result = couponService.getCouponsByPlaceAndMarker(userId, placeId, markerCode);
        return ResponseEntity.ok(ApiResponse.success("쿠폰 템플릿 조회 성공", result));
    }

    @PostMapping("/{couponTemplateId}/download")
    public ResponseEntity<ApiResponse<UserCouponResponseDto>> downloadCoupon(
            @PathVariable Long couponTemplateId,
            @AuthenticationPrincipal CustomUser user
    ) {
        Long userId = (user != null && user.getUser() != null) ? user.getUser().getUserId() : null;
        UserCouponResponseDto response = couponService.downloadCoupon(userId, couponTemplateId);
        return ResponseEntity.ok(ApiResponse.success("쿠폰 다운로드 성공", response));
    }

}


