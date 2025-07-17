package com.unear.userservice.coupon.entity;

import com.unear.userservice.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "user_coupons")
@Getter
@Setter
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCouponId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "coupon_template_id", nullable = false)
    private CouponTemplate couponTemplate;

    private LocalDate createdAt;
    private LocalDate usedAt;

    private String couponStatusCode;
    private String barcodeNumber;
}


