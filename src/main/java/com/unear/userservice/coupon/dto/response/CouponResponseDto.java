package com.unear.userservice.coupon.dto.response;

import com.unear.userservice.coupon.entity.CouponTemplate;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDate;

@Getter
@Builder
public class CouponResponseDto {

    private Long couponTemplateId;
    private String couponName;
    private String discountCode;
    private String membershipCode;
    private String discountInfo;
    private LocalDate couponStart;
    private LocalDate couponEnd;

    private boolean isDownloaded;

    public static CouponResponseDto from(CouponTemplate entity, String discountInfo, boolean isDownloaded) {
        return CouponResponseDto.builder()
                .couponTemplateId(entity.getCouponTemplateId())
                .couponName(entity.getCouponName())
                .discountCode(entity.getDiscountCode())
                .membershipCode(entity.getMembershipCode())
                .discountInfo(discountInfo)
                .couponStart(entity.getCouponStart())
                .couponEnd(entity.getCouponEnd())
                .isDownloaded(isDownloaded)
                .build();
    }
}

