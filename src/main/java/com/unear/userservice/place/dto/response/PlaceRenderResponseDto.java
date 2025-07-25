package com.unear.userservice.place.dto.response;

import com.unear.userservice.place.entity.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Builder
public class PlaceRenderResponseDto {

    private Long placeId;
    private String placeName;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String categoryCode;
    private String markerCode;
    private String eventCode;
    private String benefitCategory;
    private boolean favorite;

    public static PlaceRenderResponseDto from(Place place, boolean isFavorite) {
        return PlaceRenderResponseDto.builder()
                .placeId(place.getPlaceId())
                .placeName(place.getPlaceName())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .categoryCode(place.getCategoryCode())
                .markerCode(place.getMarkerCode())
                .eventCode(place.getEventTypeCode())
                .benefitCategory(place.getBenefitCategory())
                .favorite(isFavorite)
                .build();
    }

}
