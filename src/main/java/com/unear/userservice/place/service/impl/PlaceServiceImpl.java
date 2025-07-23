package com.unear.userservice.place.service.impl;

import com.unear.userservice.benefit.entity.FranchiseDiscountPolicy;
import com.unear.userservice.benefit.entity.GeneralDiscountPolicy;
import com.unear.userservice.benefit.repository.FranchiseDiscountPolicyRepository;
import com.unear.userservice.benefit.repository.FranchiseRepository;
import com.unear.userservice.benefit.repository.GeneralDiscountPolicyRepository;
import com.unear.userservice.common.enums.PlaceType;
import com.unear.userservice.common.exception.exception.PlaceNotFoundException;
import com.unear.userservice.common.exception.exception.UserNotFoundException;
import com.unear.userservice.coupon.dto.response.CouponResponseDto;
import com.unear.userservice.coupon.entity.CouponTemplate;
import com.unear.userservice.coupon.entity.UserCoupon;
import com.unear.userservice.coupon.repository.CouponTemplateRepository;
import com.unear.userservice.coupon.repository.UserCouponRepository;
import com.unear.userservice.coupon.service.CouponService;
import com.unear.userservice.place.dto.request.NearbyPlaceRequestDto;
import com.unear.userservice.place.dto.request.PlaceRequestDto;
import com.unear.userservice.place.dto.response.*;
import com.unear.userservice.place.entity.FavoritePlace;
import com.unear.userservice.place.entity.Franchise;
import com.unear.userservice.place.entity.Place;
import com.unear.userservice.place.repository.FavoritePlaceRepository;
import com.unear.userservice.place.repository.PlaceRepository;
import com.unear.userservice.place.service.PlaceService;
import com.unear.userservice.user.entity.User;
import com.unear.userservice.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final FavoritePlaceRepository favoritePlaceRepository;
    private final UserRepository userRepository;
    private final UserCouponRepository userCouponRepository;
    private final CouponTemplateRepository couponTemplateRepository;
    private final FranchiseDiscountPolicyRepository franchiseDiscountPolicyRepository;
    private final GeneralDiscountPolicyRepository generalDiscountPolicyRepository;

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

    @Override
    @Transactional
    public List<NearbyPlaceWithCouponsDto> getNearbyPlacesWithCoupons(NearbyPlaceRequestDto requestDto, Long userId) {

        List<NearestPlaceProjection> projections = placeRepository.findNearestPlaceIdsByDistance(
                requestDto.getLatitude(), requestDto.getLongitude(), 5);
        List<Long> placeIds = projections.stream()
                .map(NearestPlaceProjection::getPlaceId)
                .toList();

        List<Place> places = placeRepository.findAllById(placeIds);
        Map<Long, Place> placeMap = places.stream()
                .collect(Collectors.toMap(Place::getPlaceId, Function.identity()));

        List<Franchise> franchises = places.stream()
                .map(Place::getFranchise)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        List<UserCoupon> userCoupons = userCouponRepository.findByUser_UserId(userId);
        Map<Long, Long> templateToUserCouponId = userCoupons.stream()
                .collect(Collectors.toMap(
                        uc -> uc.getCouponTemplate().getCouponTemplateId(),
                        UserCoupon::getUserCouponId
                ));

        String membershipCode = userRepository.findById(userId)
                .map(User::getMembershipCode)
                .orElse(null);

        List<CouponTemplate> templates = couponTemplateRepository.findByPlacesAndMembership(
                places, franchises, membershipCode);

        Map<Long, List<CouponResponseDto>> couponMap = templates.stream()
                .map(ct -> {
                    Long templateId = ct.getCouponTemplateId();
                    boolean isDownloaded = templateToUserCouponId.containsKey(templateId);
                    Long userCouponId = templateToUserCouponId.get(templateId);
                    Long resolvedPlaceId = resolvePlaceIdFromTemplate(ct, places);
                    if (resolvedPlaceId == null) return null;

                    return Map.entry(
                            resolvedPlaceId,
                            CouponResponseDto.from(ct, null, isDownloaded, userCouponId)
                    );
                })
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        return projections.stream()
                .map(p -> {
                    Place place = placeMap.get(p.getPlaceId());
                    if (place == null) return null;

                    return NearbyPlaceWithCouponsDto.builder()
                            .placeId(place.getPlaceId())
                            .name(place.getPlaceName())
                            .address(place.getAddress())
                            .categoryCode(place.getCategoryCode())
                            .distanceKm(Math.round(p.getDistance() / 100.0) / 10.0)
                            .coupons(couponMap.getOrDefault(place.getPlaceId(), List.of()))
                            .build();
                })
                .filter(Objects::nonNull)
                .toList();
    }



    private Long resolvePlaceIdFromTemplate(CouponTemplate ct, List<Place> places) {
        PlaceType markerType = PlaceType.fromCode(ct.getMarkerCode());
        if (markerType.isFranchise()) {
            Long franchiseId = franchiseDiscountPolicyRepository.findById(ct.getDiscountPolicyDetailId())
                    .map(FranchiseDiscountPolicy::getFranchise)
                    .map(Franchise::getFranchiseId)
                    .orElse(null);
            return places.stream()
                    .filter(p -> p.getFranchise() != null && p.getFranchise().getFranchiseId().equals(franchiseId))
                    .map(Place::getPlaceId)
                    .findFirst()
                    .orElse(null);
        } else {
            return generalDiscountPolicyRepository.findById(ct.getDiscountPolicyDetailId())
                    .map(GeneralDiscountPolicy::getPlace)
                    .map(Place::getPlaceId)
                    .orElse(null);
        }
    }


}
