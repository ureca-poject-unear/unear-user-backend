package com.unear.userservice.place.service;

import com.unear.userservice.place.dto.request.PlaceRequestDto;
import com.unear.userservice.place.dto.response.PlaceRenderResponseDto;
import com.unear.userservice.place.dto.response.PlaceResponseDto;

import java.util.List;

public interface PlaceService {

    List<PlaceRenderResponseDto> getFilteredPlaces(PlaceRequestDto requestDto, Long userId);
    PlaceResponseDto getPlaceDetailWithFavorite(Long placeId, Long userId);
    boolean toggleFavorite(Long userId, Long placeId);
    List<PlaceResponseDto> getUserFavoritePlaces(Long userId);
}
