package com.unear.userservice.event.service;

import com.unear.userservice.event.dto.response.EventDetailResponseDto;

public interface EventService {
    EventDetailResponseDto getEventDetail(Long eventId);
}