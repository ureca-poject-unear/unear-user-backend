package com.unear.userservice.place.service.impl;

import com.unear.userservice.exception.exception.PlaceNotFoundException;
import com.unear.userservice.exception.exception.UserNotFoundException;
import com.unear.userservice.place.dto.request.NearbyPlaceRequestDto;
import com.unear.userservice.place.dto.request.PlaceRequestDto;
import com.unear.userservice.place.dto.response.NearestPlaceProjection;
import com.unear.userservice.place.dto.response.NearestPlaceResponseDto;
import com.unear.userservice.place.dto.response.PlaceRenderResponseDto;
import com.unear.userservice.place.dto.response.PlaceResponseDto;
import com.unear.userservice.place.entity.FavoritePlace;
import com.unear.userservice.place.entity.Place;
import com.unear.userservice.place.repository.FavoritePlaceRepository;
import com.unear.userservice.place.repository.PlaceRepository;
import com.unear.userservice.place.service.PlaceService;
import com.unear.userservice.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final FavoritePlaceRepository favoritePlaceRepository;
    private final UserRepository userRepository;

    @Override
    public List<PlaceRenderResponseDto> getFilteredPlaces(PlaceRequestDto requestDto, Long userId) {
        List<Place> places = placeRepository.findFilteredPlaces(
                userId,
                requestDto.getCategoryCode(),
                requestDto.getBenefitCategory(),
                requestDto.getIsFavorite(),
                requestDto.getSouthWestLatitude(),
                requestDto.getNorthEastLatitude(),
                requestDto.getSouthWestLongitude(),
                requestDto.getNorthEastLongitude(),
                requestDto.getKeyword()
        );

        Set<Long> favorites = (userId != null)
                ? favoritePlaceRepository.findPlaceIdsByUserId(userId)
                : Collections.emptySet();

        return places.stream()
                .map(place -> PlaceRenderResponseDto.from(place, favorites.contains(place.getPlaceId())))
                .toList();
    }


    @Override
    public PlaceResponseDto getPlaceDetail(Long placeId, Long userId, Double latitude, Double longitude) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceNotFoundException("해당 장소가 존재하지 않습니다."));

        boolean isFavorite = false;
        if (userId != null) {
            isFavorite = favoritePlaceRepository.existsByUser_UserIdAndPlace_PlaceIdAndIsFavoritedTrue(userId, placeId);
        }

        Double distanceKm = null;
        if (latitude != null && longitude != null && place.getLocation() != null) {
            Double distanceMeters = placeRepository.calculateDistance(placeId, latitude, longitude);
            if (distanceMeters != null) {
                distanceKm = Math.round( distanceMeters / 100.0) / 10.0;
            }
        }

        return PlaceResponseDto.from(place, isFavorite, distanceKm);
    }



    @Override
    @Transactional
    public boolean toggleFavorite(Long userId, Long placeId) {
        Optional<FavoritePlace> optional = favoritePlaceRepository.findByUser_UserIdAndPlace_PlaceId(userId, placeId);

        if (optional.isPresent()) {
            FavoritePlace favorite = optional.get();
            boolean newStatus = !Boolean.TRUE.equals(favorite.getIsFavorited());
            favorite.setIsFavorited(newStatus);
            favorite.setDeletedAt(newStatus ? null : LocalDateTime.now());
            return newStatus;
        } else {
            FavoritePlace favorite = new FavoritePlace();
            favorite.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + userId)));
            favorite.setPlace(placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceNotFoundException("장소를 찾을 수 없습니다: " + placeId)));
            favorite.setIsFavorited(true);
            favorite.setCreatedAt(LocalDateTime.now());
            favoritePlaceRepository.save(favorite);
            return true;
        }
    }

    @Override
    public List<PlaceResponseDto> getUserFavoritePlaces(Long userId) {
        List<FavoritePlace> favoritePlaces = favoritePlaceRepository.findWithPlaceAndFranchiseByUserId(userId);

        return favoritePlaces.stream()
                .map(fav -> PlaceResponseDto.from(fav.getPlace(), true))
                .collect(Collectors.toList());
    }

    @Override
    public List<NearestPlaceResponseDto> getNearbyPlaces(NearbyPlaceRequestDto requestDto, Long userId) {
        double lat = requestDto.getLatitude();
        double lon = requestDto.getLongitude();

        List<NearestPlaceProjection> projections = placeRepository.findNearestPlaceIdsByDistance(lat, lon, 5);

        return projections.stream()
                .map(NearestPlaceResponseDto::from)
                .toList();
    }

}
