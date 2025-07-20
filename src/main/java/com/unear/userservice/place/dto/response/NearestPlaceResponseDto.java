package com.unear.userservice.place.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NearestPlaceResponseDto {
    private Long placeId;
    private Double distanceKm;

    public static NearestPlaceResponseDto from(
            NearestPlaceProjection p
    ) {
        return NearestPlaceResponseDto.builder()
                .placeId(p.getPlaceId())
                .distanceKm(Math.round(p.getDistance() / 100.0) / 10.0)
                .build();
    }
}

