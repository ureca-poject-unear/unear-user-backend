package com.unear.userservice.place.entity;

import com.unear.userservice.event.entity.UnearEvent;
import com.unear.userservice.stamp.entity.Stamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event_places")
@Getter
@Setter
public class EventPlace {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventPlaceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unear_event_id")
    private UnearEvent event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private String eventCode;

    @OneToMany(mappedBy = "eventPlace")
    private List<Stamp> stamps = new ArrayList<>();
}

