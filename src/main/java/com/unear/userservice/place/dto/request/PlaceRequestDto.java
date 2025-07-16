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

    private Double southWestLatitude;
    private Double southWestLongitude;
    private Double northEastLatitude;
    private Double northEastLongitude;

}
