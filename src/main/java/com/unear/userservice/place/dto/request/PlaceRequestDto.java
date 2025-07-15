package com.unear.userservice.place.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceRequestDto {
    private String categoryCode;
    private String benefitCategory;
    private Boolean isFavorite;

    private Double minLatitude;  // 좌하단 위도
    private Double minLongitude; // 좌하단 경도
    private Double maxLatitude;  // 우상단 위도
    private Double maxLongitude; // 우상단 경도
}
