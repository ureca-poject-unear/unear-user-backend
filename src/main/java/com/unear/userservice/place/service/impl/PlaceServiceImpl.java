package com.unear.userservice.place.service.impl;

import com.unear.userservice.exception.exception.PlaceNotFoundException;
import com.unear.userservice.place.dto.request.PlaceRequestDto;
import com.unear.userservice.place.dto.response.PlaceRenderResponseDto;
import com.unear.userservice.place.dto.response.PlaceResponseDto;
import com.unear.userservice.place.entity.FavoritePlace;
import com.unear.userservice.place.entity.Place;
import com.unear.userservice.place.repository.FavoritePlaceRepository;
import com.unear.userservice.place.repository.PlaceRepository;
import com.unear.userservice.place.service.PlaceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final FavoritePlaceRepository favoritePlaceRepository;

    public List<PlaceRenderResponseDto> getFilteredPlaces(PlaceRequestDto requestDto, Long userId) {
        List<Place> places = placeRepository.findFilteredPlaces(
                userId,
                requestDto.getCategoryCode(),
                requestDto.getBenefitCategory(),
                requestDto.getIsFavorite(),
                requestDto.getMinLatitude(),
                requestDto.getMaxLatitude(),
                requestDto.getMinLongitude(),
                requestDto.getMaxLongitude()
        );

        Set<Long> favorites = (userId != null)
                ? favoritePlaceRepository.findPlaceIdsByUserId(userId)
                : Collections.emptySet();

        return places.stream()
                .map(place -> PlaceRenderResponseDto.from(place, favorites.contains(place.getPlacesId())))
                .toList();
    }



    @Override
    public PlaceResponseDto getPlaceDetailWithFavorite(Long placeId, Long userId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceNotFoundException("해당 장소가 존재하지 않습니다."));

        boolean isFavorite = false;
        if (userId != null) {
            isFavorite = favoritePlaceRepository.existsByUser_UserIdAndPlace_PlacesIdAndIsFavoritedTrue(userId, placeId);
        }
        return PlaceResponseDto.from(place, isFavorite);
    }


}
