package com.unear.userservice.event.service.impl;

import com.unear.userservice.common.enums.DiscountPolicy;
import com.unear.userservice.common.enums.EventType;
import com.unear.userservice.common.exception.BusinessException;
import com.unear.userservice.common.exception.ErrorCode;
import com.unear.userservice.coupon.repository.CouponTemplateRepository;
import com.unear.userservice.event.dto.response.EventCouponResponseDto;
import com.unear.userservice.event.dto.response.EventDetailResponseDto;
import com.unear.userservice.event.dto.response.EventPlaceResponseDto;
import com.unear.userservice.event.entity.UnearEvent;
import com.unear.userservice.event.repository.EventRepository;
import com.unear.userservice.event.service.EventService;
import com.unear.userservice.place.entity.Place;
import com.unear.userservice.place.entity.EventPlace;
import com.unear.userservice.coupon.entity.CouponTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CouponTemplateRepository couponTemplateRepository;

    @Override
    public EventDetailResponseDto findEventDetailById(Long eventId) {
        UnearEvent event = eventRepository.findEventWithPlaces(eventId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EVENT_NOT_FOUND));

        List<EventPlace> eventPlaces = event.getEventPlaces();

        Place popupStore = eventPlaces.stream()
                .filter(ep -> EventType.REQUIRE.name().equalsIgnoreCase(ep.getEventCode()))
                .map(EventPlace::getPlace)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        List<Place> generalPlaces = eventPlaces.stream()
                .filter(ep -> EventType.GENERAL.name().equalsIgnoreCase(ep.getEventCode()))
                .map(EventPlace::getPlace)
                .filter(Objects::nonNull)
                .limit(3)
                .toList();

        List<CouponTemplate> fcfsCoupons = couponTemplateRepository
                .findByEventIdAndDiscountCode(eventId, DiscountPolicy.COUPON_FCFS);

        List<EventCouponResponseDto> couponDtos = fcfsCoupons.stream()
                .filter(c -> popupStore != null && Objects.equals(c.getMarkerCode(), popupStore.getMarkerCode()))
                .map(EventCouponResponseDto::from)
                .toList();

        return EventDetailResponseDto.of(event, popupStore, generalPlaces, couponDtos);
    }
}
