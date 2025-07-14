package com.unear.userservice.benefit.entity;

import com.unear.userservice.place.entity.Place;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "discount_policy_detail")
@Getter
@NoArgsConstructor
public class DiscountPolicyDetail {

    @Id
    private Long discountPolicyDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "places_id")
    private Place place;

    private Integer unitBaseAmount;
    private Integer discountValue;
    private Integer percent;
    private Integer minPurchaseAmount;
    private Integer maxDiscountAmount;

    private String discountCode;
    private String membershipCode;
    private String markerCode;
}


