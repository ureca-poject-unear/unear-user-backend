package com.unear.userservice.coupon.controller;


import com.unear.userservice.common.response.ApiResponse;
import com.unear.userservice.common.security.CustomUser;
import com.unear.userservice.coupon.dto.response.CouponResponseDto;
import com.unear.userservice.coupon.dto.response.UserCouponResponseDto;
import com.unear.userservice.coupon.service.CouponService;
import com.unear.userservice.exception.exception.UnauthorizedException;
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
        if (user == null || user.getUser() == null) {
            throw new UnauthorizedException("인증되지 않은 사용자입니다.");
        }
        Long userId = user.getUser().getUserId();
        UserCouponResponseDto response = couponService.downloadCoupon(userId, couponTemplateId);
        return ResponseEntity.ok(ApiResponse.success("쿠폰 다운로드 성공", response));
    }

    @PostMapping("/{couponTemplateId}/fcfs")
    public ResponseEntity<ApiResponse<UserCouponResponseDto>> downloadFCFSCoupon(
            @PathVariable Long couponTemplateId,
            @AuthenticationPrincipal CustomUser user
    ) {
        if (user == null || user.getUser() == null) {
            throw new UnauthorizedException("인증되지 않은 사용자입니다.");
        }
        Long userId = user.getUser().getUserId();
        UserCouponResponseDto response = couponService.downloadFCFSCoupon(userId, couponTemplateId);
        return ResponseEntity.ok(ApiResponse.success("쿠폰 다운로드 성공", response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<UserCouponResponseDto>>> getMyCoupons(
            @AuthenticationPrincipal CustomUser user
    ) {
        if (user == null || user.getUser() == null) {
            throw new UnauthorizedException("인증되지 않은 사용자입니다.");
        }
        Long userId = user.getUser().getUserId();
        List<UserCouponResponseDto> response = couponService.getMyCoupons(userId);
        return ResponseEntity.ok(ApiResponse.success("사용자 쿠폰 목록 조회 성공", response));
    }

    @GetMapping("/me/{userCouponId}")
    public ResponseEntity<ApiResponse<UserCouponResponseDto>> getMyCouponDetail(
            @PathVariable Long userCouponId,
            @AuthenticationPrincipal CustomUser user
    ) {
        if (user == null || user.getUser() == null) {
            throw new UnauthorizedException("인증되지 않은 사용자입니다.");
        }
        Long userId = user.getUser().getUserId();
        UserCouponResponseDto response = couponService.getMyCouponDetail(userId, userCouponId);
        return ResponseEntity.ok(ApiResponse.success("사용자 쿠폰 상세 조회 성공", response));
    }


}


