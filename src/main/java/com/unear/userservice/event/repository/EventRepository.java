package com.unear.userservice.event.repository;

import com.unear.userservice.common.enums.DiscountPolicy;
import com.unear.userservice.common.enums.EventType;
import com.unear.userservice.coupon.entity.CouponTemplate;
import com.unear.userservice.event.entity.UnearEvent;
import com.unear.userservice.place.entity.EventPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<UnearEvent, Long> {

    @Query("""
SELECT DISTINCT e
FROM UnearEvent e
LEFT JOIN FETCH e.eventPlaces ep
LEFT JOIN FETCH ep.place p
WHERE e.unearEventId = :eventId
""")
    Optional<UnearEvent> findEventWithPlaces(@Param("eventId") Long eventId);





}
