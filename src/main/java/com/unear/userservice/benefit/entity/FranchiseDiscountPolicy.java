package com.unear.userservice.benefit.entity;

import com.unear.userservice.place.entity.Franchise;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "franchise_discount_policy")
@Getter
@NoArgsConstructor
public class FranchiseDiscountPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long franchiseDiscountPolicyId;

    private String membershipCode;
    private String discountCode;

    private Integer unitBaseAmount;
    private Integer fixedDiscount;
    private Integer discountPercent;
    private Integer minPurchaseAmount;
    private Integer maxDiscountAmount;

    @OneToOne(mappedBy = "franchise", fetch = FetchType.LAZY)
    private Franchise franchise;
}

