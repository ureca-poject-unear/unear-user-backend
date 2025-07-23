package com.unear.userservice.event.dto.response;

import com.unear.userservice.place.entity.Place;

public record EventPlaceResponseDto(
        Long placeId,
        String placeName,
        String address
) {
    public static EventPlaceResponseDto from(Place place) {
        return new EventPlaceResponseDto(
                place.getPlaceId(),
                place.getPlaceName(),
                place.getAddress()
        );
    }
}