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

    private Long discountPolicyDetailId; // 할인 정책 상세 id , erd 상으로는 연결 X , 추후에 연관관계 고려

    private String couponName;
    private String couponDesc;
    private Integer remainingQuantity;

    private LocalDate couponStart;
    private LocalDate couponEnd;

    private String discountCode;
    private String membershipCode;

    @OneToMany(mappedBy = "couponTemplate")
    private List<UserCoupon> userCoupons = new ArrayList<>();
}

