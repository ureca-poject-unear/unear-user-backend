package com.unear.userservice.stamp.service.impl;

import com.unear.userservice.stamp.domain.EventStampPolicy;
import com.unear.userservice.stamp.domain.StampCollection;
import com.unear.userservice.stamp.dto.response.StampStatusResponseDto;
import com.unear.userservice.stamp.entity.Stamp;
import com.unear.userservice.stamp.repository.StampRepository;
import com.unear.userservice.stamp.service.StampService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StampServiceImpl implements StampService {

    private final StampRepository stampRepository;

    @Override
    public StampStatusResponseDto getStampStatus(Long userId, Long eventId) {
        List<Stamp> stamps = stampRepository.findByUser_UserIdAndEventPlace_Event_UnearEventId(userId, eventId);
        StampCollection collection = new StampCollection(stamps);
        EventStampPolicy policy = new EventStampPolicy();

        boolean available = policy.isSatisfiedBy(collection);
        return StampStatusResponseDto.of(collection.getStamps(), available);
    }
}
