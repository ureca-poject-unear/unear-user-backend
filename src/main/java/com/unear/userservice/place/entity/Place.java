package com.unear.userservice.place.entity;

import com.unear.userservice.benefit.entity.DiscountPolicyDetail;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "places")
@Getter @Setter
public class Place {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

    private String placeName;
    private String placeDesc;
    private String address;
    private String businessHours;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    private String markerCode;
    private String eventCode;
    private String categoryCode;

    @OneToMany(mappedBy = "place")
    private List<EventPlace> eventPlaces = new ArrayList<>();

    @OneToMany(mappedBy = "place")
    private List<DiscountPolicyDetail> discountPolicies = new ArrayList<>();
}

