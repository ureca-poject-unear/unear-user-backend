package com.unear.userservice.event.service.impl;

import com.unear.userservice.common.enums.DiscountPolicy;
import com.unear.userservice.common.enums.EventType;
import com.unear.userservice.coupon.repository.CouponTemplateRepository;
import com.unear.userservice.event.dto.response.EventCouponResponseDto;
import com.unear.userservice.event.dto.response.EventDetailResponseDto;
import com.unear.userservice.event.entity.UnearEvent;
import com.unear.userservice.event.repository.EventRepository;
import com.unear.userservice.event.service.EventService;
import com.unear.userservice.place.entity.Place;
import com.unear.userservice.place.entity.EventPlace;
import com.unear.userservice.coupon.entity.CouponTemplate;
import com.unear.userservice.place.repository.EventPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventPlaceRepository eventPlaceRepository;
    private final CouponTemplateRepository couponTemplateRepository;

    @Override
    public EventDetailResponseDto getEventDetail(Long eventId) {
        // 1. 이벤트 조회
        UnearEvent event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("이벤트를 찾을 수 없습니다."));

        // 2. 팝업스토어 조회
        List<EventPlace> popupList = eventPlaceRepository.findByEventPopup(eventId, EventType.REQUIRE);
        Place popupStore = popupList.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("팝업스토어가 존재하지 않습니다."))
                .getPlace();

        // 3. 제휴처(GENERAL) 조회
        List<EventPlace> eventPlaces = eventPlaceRepository.findByEventPlace(eventId);
        List<Place> partnerStores = eventPlaces.stream()
                .filter(e -> e.getEventCode() == EventType.GENERAL)
                .map(EventPlace::getPlace)
                .toList();

        // 4. 선착순 쿠폰 조회
        List<CouponTemplate> couponList = couponTemplateRepository.findByEventCoupon(eventId, DiscountPolicy.COUPON_FCFS);


        return EventDetailResponseDto.of(event, popupStore, partnerStores, couponList);
    }
}
