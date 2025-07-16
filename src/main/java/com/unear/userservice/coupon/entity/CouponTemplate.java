package com.unear.userservice.coupon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coupon_templates")
@Getter
@Setter
public class CouponTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponTemplatesId;

    private Long discountPolicyDetailId;

    private String couponName;
    private String couponDesc;
    private Integer remainingQuantity;

    private LocalDate couponStart;
    private LocalDate couponEnd;

    private String discountCode;
    private String membershipCode;
    private String markerCode;

    @OneToMany(mappedBy = "couponTemplate")
    private List<UserCoupon> userCoupons = new ArrayList<>();
}


