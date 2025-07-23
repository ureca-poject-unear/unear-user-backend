package com.unear.userservice.event.dto.response;


import com.unear.userservice.event.entity.UnearEvent;
import com.unear.userservice.place.entity.Place;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record EventDetailResponseDto(
        Long eventId,
        String eventName,
        String description,
        BigDecimal latitude,
        BigDecimal longitude,
        Integer radius,
        LocalDate startDate,
        LocalDate endDate,
        EventPlaceResponseDto popupStore,
        List<EventPlaceResponseDto> partnerStores,
        List<EventCouponResponseDto> coupons
) {
    public static EventDetailResponseDto of(
            UnearEvent event,
            Place popupStore,
            List<Place> partnerPlaces,
            List<EventCouponResponseDto> coupons
    ) {
        return EventDetailResponseDto.builder()
                .eventId(event.getUnearEventId())
                .eventName(event.getEventName())
                .description(event.getEventDescription())
                .latitude(event.getLatitude())
                .longitude(event.getLongitude())
                .radius(event.getRadiusMeter())
                .startDate(event.getStartAt())
                .endDate(event.getEndAt())
                .popupStore(popupStore != null ? EventPlaceResponseDto.from(popupStore) : null)
                .partnerStores(partnerPlaces.stream().map(EventPlaceResponseDto::from).toList())
                .coupons(coupons)
                .build();
    }
}
