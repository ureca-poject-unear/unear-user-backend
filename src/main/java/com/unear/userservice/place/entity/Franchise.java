package com.unear.userservice.place.entity;

import com.unear.userservice.benefit.entity.FranchiseDiscountPolicy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "franchise")
@Getter
@Setter
@NoArgsConstructor
public class Franchise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long franchiseId;

    private String name;
    private String categoryCode;
    private String imageUrl;

    @OneToMany(mappedBy = "franchise", cascade = CascadeType.ALL)
    private List<Place> places = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "franchise_discount_policy_id")
    private FranchiseDiscountPolicy franchiseDiscountPolicy;
}


