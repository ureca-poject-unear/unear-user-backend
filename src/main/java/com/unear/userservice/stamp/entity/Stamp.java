package com.unear.userservice.stamp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "stamps")
@Getter
@Setter
public class Stamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stampsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_places_id")
    private EventPlace eventPlace;

    private LocalDateTime stampedAt;
    private String eventCode;
    private String placeName;
}

