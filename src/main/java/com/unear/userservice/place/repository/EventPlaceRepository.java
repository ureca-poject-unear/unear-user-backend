package com.unear.userservice.place.repository;

import com.unear.userservice.common.enums.EventType;
import com.unear.userservice.place.entity.EventPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventPlaceRepository extends JpaRepository<EventPlace, Long> {
    // 팝업스토어 조회
    @Query("""
        SELECT ep FROM EventPlace ep
        WHERE ep.event.unearEventId = :eventId
        AND ep.eventCode = :eventCode
    """)
    List<EventPlace> findByEventPopup(@Param("eventId") Long eventId,
                                      @Param("eventCode") EventType eventCode);

    // 전체 제휴처 조회 (팝업 포함)
    @Query("""
        SELECT ep FROM EventPlace ep
        WHERE ep.event.unearEventId = :eventId
    """)
    List<EventPlace> findByEventPlace(@Param("eventId") Long eventId);
}
