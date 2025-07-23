package com.unear.userservice.coupon.entity;

import com.unear.userservice.common.enums.DiscountPolicy;
import com.unear.userservice.common.exception.exception.CouponSoldOutException;
import com.unear.userservice.event.entity.UnearEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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

    private LocalDateTime couponStart;
    private LocalDateTime couponEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_code")
    private DiscountPolicy discountCode;

    private String membershipCode;

    @OneToMany(mappedBy = "couponTemplate")
    private List<UserCoupon> userCoupons = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "unear_event_id")
    private UnearEvent event;

    public void decreaseQuantity() {
        if (this.remainingQuantity <= 0) {
            throw new CouponSoldOutException("쿠폰 재고가 없습니다.");
        }
        this.remainingQuantity -= 1;
    }
}


