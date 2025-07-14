package com.unear.userservice.place.dto.response;

import com.unear.userservice.place.entity.Place;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PlaceResponseDto {
    private Long placeId;
    private String placeName;
    private String placeDesc;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String benefitCategory;
    private Integer startTime;
    private Integer endTime;
    private String categoryCode;
    private String markerCode;
    private String eventCode;
    private String franchiseName;
    private boolean isFavorite;

    public static PlaceResponseDto from(Place place, boolean isFavorite) {
        return PlaceResponseDto.builder()
                .placeId(place.getPlacesId())
                .placeName(place.getPlaceName())
                .placeDesc(place.getPlaceDesc())
                .address(place.getAddress())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .benefitCategory(place.getBenefitCategory())
                .startTime(place.getStartTime())
                .endTime(place.getEndTime())
                .categoryCode(place.getCategoryCode())
                .markerCode(place.getMarkerCode())
                .eventCode(place.getEventCode())
                .franchiseName(place.getFranchise().getFranchiseName())
                .isFavorite(isFavorite)
                .build();
    }

}


