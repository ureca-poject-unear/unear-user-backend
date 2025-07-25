package com.unear.userservice.event.controller;

import com.unear.userservice.common.docs.event.EventApiDocs;
import com.unear.userservice.common.response.ApiResponse;
import com.unear.userservice.event.dto.response.EventDetailResponseDto;
import com.unear.userservice.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @EventApiDocs.GetEventDetails
    @GetMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventDetailResponseDto>> getEventDetail(@PathVariable Long eventId) {
        EventDetailResponseDto dto = eventService.getEventDetail(eventId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }
}