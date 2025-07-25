package com.unear.userservice.event.entity;

import com.unear.userservice.coupon.entity.CouponTemplate;
import com.unear.userservice.place.entity.EventPlace;
import com.unear.userservice.roulette.entity.RouletteResult;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unear_events")
@Getter
@Setter
public class UnearEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unear_event_id")
    private Long unearEventId;

    private Long couponTemplateId;
    private String eventName;
    private String eventDescription;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    private Integer radiusMeter;
    private LocalDate startAt;
    private LocalDate endAt;

    @OneToMany(mappedBy = "event")
    private List<EventPlace> eventPlaces = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<RouletteResult> rouletteResults = new ArrayList<>();

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<CouponTemplate> couponTemplates = new ArrayList<>();

}

