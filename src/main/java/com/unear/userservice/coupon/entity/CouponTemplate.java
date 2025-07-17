package com.unear.userservice.coupon.entity;

import com.unear.userservice.exception.exception.CouponSoldOutException;
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
    private Long couponTemplateId;

    private Long discountPolicyDetailId;

    private String markerCode;
    private String couponName;

    private Integer remainingQuantity;

    private LocalDate couponStart;
    private LocalDate couponEnd;

    private String discountCode;
    private String membershipCode;

    @OneToMany(mappedBy = "couponTemplate")
    private List<UserCoupon> userCoupons = new ArrayList<>();

    public void decreaseQuantity() {
        if (this.remainingQuantity <= 0) {
            throw new CouponSoldOutException("쿠폰 재고가 없습니다.");
        }
        this.remainingQuantity -= 1;
    }
}


