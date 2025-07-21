package com.unear.userservice.coupon.dto.response;


import lombok.Getter;

import java.util.List;


@Getter
public class UserCouponResponseListDto {
    private int count;
    private List<UserCouponResponseDto> coupons;

    public UserCouponResponseListDto(List<UserCouponResponseDto> coupons) {
        this.count = coupons.size();
        this.coupons = coupons;
    }

}
