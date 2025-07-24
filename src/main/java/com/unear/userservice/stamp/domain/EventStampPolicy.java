package com.unear.userservice.stamp.domain;

import com.unear.userservice.common.enums.EventType;

public class EventStampPolicy {

    private static final int REQUIRED_COUNT = 1;
    private static final int GENERAL_COUNT = 3;

    public boolean isSatisfiedBy(StampCollection stamps) {
        return stamps.countByEventType(EventType.REQUIRE) >= REQUIRED_COUNT
                && stamps.distinctPlaceCountByEventType(EventType.GENERAL) >= GENERAL_COUNT;
    }
}
