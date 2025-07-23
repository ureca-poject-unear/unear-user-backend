package com.unear.userservice.place.service;

import com.unear.userservice.place.dto.request.NearbyPlaceRequestDto;
import com.unear.userservice.place.dto.request.PlaceRequestDto;
import com.unear.userservice.place.dto.response.NearbyPlaceWithCouponsDto;
import com.unear.userservice.place.dto.response.NearestPlaceResponseDto;
import com.unear.userservice.place.dto.response.PlaceRenderResponseDto;
import com.unear.userservice.place.dto.response.PlaceResponseDto;

import java.util.List;

public interface PlaceService {

    List<PlaceRenderResponseDto> getFilteredPlaces(PlaceRequestDto requestDto, Long userId);
    PlaceResponseDto getPlaceDetail(Long placeId, Long userId, Double latitude, Double longitude);
    boolean toggleFavorite(Long userId, Long placeId);
    List<PlaceResponseDto> getUserFavoritePlaces(Long userId);
    List<NearestPlaceResponseDto> getNearbyPlaces(NearbyPlaceRequestDto requestDto, Long userId);
    List<NearbyPlaceWithCouponsDto> getNearbyPlacesWithCoupons(NearbyPlaceRequestDto requestDto, Long userId);

}
