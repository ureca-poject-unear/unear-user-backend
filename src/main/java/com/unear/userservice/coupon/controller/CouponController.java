package com.unear.userservice.coupon.controller;
import com.unear.userservice.common.docs.coupon.CouponApiDocs;
import com.unear.userservice.common.response.ApiResponse;
import com.unear.userservice.common.security.CustomUser;
import com.unear.userservice.coupon.dto.response.CouponResponseDto;
import com.unear.userservice.coupon.dto.response.UserCouponDetailResponseDto;
import com.unear.userservice.coupon.dto.response.UserCouponListResponseDto;
import com.unear.userservice.coupon.dto.response.UserCouponResponseDto;
import com.unear.userservice.coupon.service.CouponService;
import com.unear.userservice.common.exception.exception.UnauthorizedException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
@Tag(name = "Coupon", description = "쿠폰 관련 API")
public class CouponController {

    private final CouponService couponService;

    @CouponApiDocs.GetCouponsByPlace
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

    @CouponApiDocs.DownloadCoupon
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

    @CouponApiDocs.DownloadFCFSCoupon
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

    @CouponApiDocs.GetMyCoupons
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserCouponListResponseDto>> getMyCoupons(
            @AuthenticationPrincipal CustomUser user
    ) {
        if (user == null || user.getUser() == null) {
            throw new UnauthorizedException("인증되지 않은 사용자입니다.");
        }
        Long userId = user.getUser().getUserId();
        UserCouponListResponseDto response = couponService.getMyCoupons(userId);
        return ResponseEntity.ok(ApiResponse.success("사용자 쿠폰 목록 조회 성공", response));
    }

    @CouponApiDocs.GetMyCouponDetail
    @GetMapping("/me/{userCouponId}")
    public ResponseEntity<ApiResponse<UserCouponDetailResponseDto>> getMyCouponDetail(
            @PathVariable Long userCouponId,
            @AuthenticationPrincipal CustomUser user
    ) {
        if (user == null || user.getUser() == null) {
            throw new UnauthorizedException("인증되지 않은 사용자입니다.");
        }
        Long userId = user.getUser().getUserId();
        UserCouponDetailResponseDto response = couponService.getMyCouponDetail(userId, userCouponId);
        return ResponseEntity.ok(ApiResponse.success("사용자 쿠폰 상세 조회 성공", response));
    }



}


