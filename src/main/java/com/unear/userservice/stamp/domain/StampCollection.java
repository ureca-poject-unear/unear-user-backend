package com.unear.userservice.stamp.domain;

import com.unear.userservice.place.entity.Place;
import com.unear.userservice.stamp.entity.Stamp;
import com.unear.userservice.common.enums.EventType;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StampCollection {

    private final List<Stamp> stamps;

    public StampCollection(List<Stamp> stamps) {
        this.stamps = Objects.requireNonNull(stamps, "stamps는 null일 수 없습니다");
    }

    public long countByEventType(EventType eventType) {
        return stamps.stream()
                .filter(s -> s.getEventPlace().getEventCode() == eventType)
                .count();
    }

    public long distinctPlaceCountByEventType(EventType eventType) {
        return stamps.stream()
                .filter(s -> s.getEventPlace().getEventCode() == eventType)
                .map(s -> s.getEventPlace().getPlace().getPlaceId())
                .distinct()
                .count();
    }

    public List<Stamp> getStamps() {
        return stamps;
    }
}
