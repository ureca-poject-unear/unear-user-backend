package com.unear.userservice.coupon.entity;

import com.unear.userservice.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_coupons")
@Getter
@Setter
public class UserCoupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCouponsId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "coupon_templates_id", nullable = false)
    private CouponTemplate couponTemplate;

    private String couponStatusCode;
    private String barcodeNumber;
}

