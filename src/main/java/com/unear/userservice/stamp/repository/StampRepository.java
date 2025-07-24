package com.unear.userservice.stamp.repository;

import com.unear.userservice.stamp.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StampRepository extends JpaRepository<Stamp, Long> {
    List<Stamp> findByUser_UserIdAndEventPlace_Event_UnearEventId (Long userId, Long eventPlaceId);
}
