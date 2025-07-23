package com.unear.userservice.coupon.dto.response;

import com.unear.userservice.coupon.entity.CouponTemplate;
import com.unear.userservice.coupon.entity.UserCoupon;
import com.unear.userservice.place.entity.Franchise;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserCouponResponseDto {
    private Long userCouponId;
    private String couponName;
    private String barcodeNumber;
    private String couponStatusCode;
    private LocalDateTime createdAt;
    private LocalDateTime couponEnd;

    private String name;
    private String imageUrl;
    private String categoryCode;
    private String markerCode;

    public static UserCouponResponseDto from(UserCoupon userCoupon) {
        return UserCouponResponseDto.builder()
                .userCouponId(userCoupon.getUserCouponId())
                .couponName(userCoupon.getCouponTemplate() != null
                        ? userCoupon.getCouponTemplate().getCouponName()
                        : null)
                .barcodeNumber(userCoupon.getBarcodeNumber())
                .couponStatusCode(userCoupon.getCouponStatusCode())
                .createdAt(userCoupon.getCreatedAt())
                .couponEnd(userCoupon.getCouponTemplate() != null
                        ? userCoupon.getCouponTemplate().getCouponEnd()
                        : null)
                .build();
    }

    public static UserCouponResponseDto from(UserCoupon uc, Franchise franchise) {
        CouponTemplate template = uc.getCouponTemplate();

        return UserCouponResponseDto.builder()
                .userCouponId(uc.getUserCouponId())
                .couponName(template != null ? template.getCouponName() : null)
                .barcodeNumber(uc.getBarcodeNumber())
                .couponStatusCode(uc.getCouponStatusCode())
                .createdAt(uc.getCreatedAt())
                .couponEnd(template != null ? template.getCouponEnd() : null)
                .name(franchise != null ? franchise.getName() : null)
                .markerCode(template != null ? template.getMarkerCode() : null)
                .imageUrl(franchise != null ? franchise.getImageUrl() : null)
                .categoryCode(franchise != null ? franchise.getCategoryCode() : null)
                .build();
    }

}

