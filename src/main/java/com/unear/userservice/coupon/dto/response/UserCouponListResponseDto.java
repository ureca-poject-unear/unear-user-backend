package com.unear.userservice.coupon.dto.response;


import lombok.Getter;

import java.util.List;


@Getter
public class UserCouponListResponseDto {
    private int count;
    private List<UserCouponResponseDto> coupons;

    public UserCouponListResponseDto(List<UserCouponResponseDto> coupons) {
        this.count = coupons.size();
        this.coupons = coupons;
    }

}
