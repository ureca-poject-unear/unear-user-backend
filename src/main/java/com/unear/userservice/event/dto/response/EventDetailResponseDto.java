package com.unear.userservice.event.dto.response;


import com.unear.userservice.coupon.dto.response.CouponResponseDto;
import com.unear.userservice.coupon.entity.CouponTemplate;
import com.unear.userservice.event.entity.UnearEvent;
import com.unear.userservice.place.dto.response.PlaceResponseDto;
import com.unear.userservice.place.entity.Place;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
public record EventDetailResponseDto(
        Long eventId,
        String eventName,
        String description,
        BigDecimal latitude,
        BigDecimal longitude,
        int radius,
        LocalDate startDate,
        LocalDate endDate,
        List<PlaceResponseDto> storeList,
        List<CouponResponseDto> coupons
) {

    public static EventDetailResponseDto of(
            UnearEvent event,
            Place popupStore,
            List<Place> partnerStores,
            List<CouponTemplate> coupons
    ) {
        List<PlaceResponseDto> allStores = new ArrayList<>();

        // 필수 매장 먼저
        allStores.add(PlaceResponseDto.from(popupStore, true));

        // 일반 매장들
        allStores.addAll(
                partnerStores.stream()
                        .map(place -> PlaceResponseDto.from(place, false))
                        .toList()
        );


        return new EventDetailResponseDto(
                event.getUnearEventId(),
                event.getEventName(),
                event.getEventDescription(),
                event.getLatitude(),
                event.getLongitude(),
                event.getRadiusMeter(),
                event.getStartAt(),
                event.getEndAt(),
                allStores,
                coupons.stream().map(CouponResponseDto::from).toList()
        );
    }

}
