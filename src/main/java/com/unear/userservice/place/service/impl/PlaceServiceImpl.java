package com.unear.userservice.place.service.impl;

import com.unear.userservice.benefit.entity.FranchiseDiscountPolicy;
import com.unear.userservice.benefit.entity.GeneralDiscountPolicy;
import com.unear.userservice.benefit.repository.FranchiseDiscountPolicyRepository;
import com.unear.userservice.benefit.repository.GeneralDiscountPolicyRepository;
import com.unear.userservice.common.enums.DiscountPolicy;
import com.unear.userservice.common.enums.PlaceType;
import com.unear.userservice.common.exception.exception.PlaceNotFoundException;
import com.unear.userservice.common.exception.exception.UserNotFoundException;
import com.unear.userservice.coupon.dto.response.CouponResponseDto;
import com.unear.userservice.coupon.entity.CouponTemplate;
import com.unear.userservice.coupon.entity.UserCoupon;
import com.unear.userservice.coupon.repository.CouponTemplateRepository;
import com.unear.userservice.coupon.repository.UserCouponRepository;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.unear.userservice.common.enums.DiscountPolicy.*;

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
    private final BenefitDescriptionResolver benefitDescriptionResolver;

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
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public NearbyPlaceWithCouponsDto getPlaceDetail(Long placeId, Long userId, Double latitude, Double longitude) {
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
                distanceKm = Math.round(distanceMeters / 100.0) / 10.0;
            }
        }

        List<Place> singlePlaceList = List.of(place);
        List<Franchise> franchises = place.getFranchise() != null ? List.of(place.getFranchise()) : List.of();

        List<UserCoupon> userCoupons = userCouponRepository.findByUser_UserId(userId);
        Map<Long, Long> templateToUserCouponId = userCoupons.stream()
                .filter(uc -> uc.getCouponTemplate() != null)
                .collect(Collectors.toMap(
                        uc -> uc.getCouponTemplate().getCouponTemplateId(),
                        UserCoupon::getUserCouponId
                ));

        String membershipCode = userRepository.findById(userId)
                .map(User::getMembershipCode)
                .orElse(null);

        List<CouponTemplate> templates = couponTemplateRepository.findByPlacesAndMembership(
                singlePlaceList, franchises, membershipCode);

        List<CouponResponseDto> coupons = templates.stream()
                .filter(ct -> benefitDescriptionResolver.resolvePlaceIdFromTemplateList(ct, singlePlaceList).contains(placeId))
                .map(ct -> {
                    Long templateId = ct.getCouponTemplateId();
                    boolean isDownloaded = templateToUserCouponId.containsKey(templateId);
                    Long userCouponId = templateToUserCouponId.get(templateId);
                    return CouponResponseDto.from(ct, null, isDownloaded, userCouponId);
                })
                .toList();


        DiscountPolicyRef policyRef = templates.stream()
                .filter(ct -> benefitDescriptionResolver.resolvePlaceIdFromTemplateList(ct, singlePlaceList).contains(placeId))
                .map(ct -> {
                    PlaceType markerType = PlaceType.fromCode(ct.getMarkerCode());
                    if (markerType.isFranchise()) {
                        Long franchiseId = (ct.getDiscountPolicyDetailId() != null)
                                ? franchiseDiscountPolicyRepository.findById(ct.getDiscountPolicyDetailId())
                                .map(f -> f.getFranchise().getFranchiseId())
                                .orElse(null)
                                : place.getFranchise() != null ? place.getFranchise().getFranchiseId() : null;
                        return new DiscountPolicyRef(placeId, null, franchiseId);
                    } else {
                        return new DiscountPolicyRef(placeId, ct.getDiscountPolicyDetailId(), null);
                    }
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        if ((policyRef == null || policyRef.franchiseId() == null) && place.getFranchise() != null) {
            policyRef = new DiscountPolicyRef(place.getPlaceId(), null, place.getFranchise().getFranchiseId());
        }

        List<GeneralDiscountPolicy> generalPolicies = generalDiscountPolicyRepository.findByPlaceIn(singlePlaceList);
        for (GeneralDiscountPolicy policy : generalPolicies) {
            Long pid = policy.getPlace().getPlaceId();
            if (policyRef == null || policyRef.discountPolicyDetailId() == null) {
                policyRef = new DiscountPolicyRef(pid, policy.getGeneralDiscountPolicyId(), null);
            }
        }

        Long franchiseId = (policyRef != null && policyRef.franchiseId() != null)
                ? policyRef.franchiseId()
                : (place.getFranchise() != null ? place.getFranchise().getFranchiseId() : null);

        String benefitDesc = benefitDescriptionResolver.resolveBenefitDesc(policyRef, membershipCode);

        return NearbyPlaceWithCouponsDto.builder()
                .placeId(place.getPlaceId())
                .name(place.getPlaceName())
                .address(place.getAddress())
                .categoryCode(place.getCategoryCode())
                .distanceKm(distanceKm)
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .startTime(place.getStartTime())
                .endTime(place.getEndTime())
                .tel(place.getTel())
                .markerCode(place.getMarkerCode())
                .eventTypeCode(place.getEventTypeCode())
                .favorite(isFavorite)
                .discountPolicyDetailId(policyRef != null ? policyRef.discountPolicyDetailId() : null)
                .franchiseId(franchiseId)
                .coupons(coupons)
                .benefitDesc(benefitDesc)
                .build();
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
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
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
                .filter(uc -> uc.getCouponTemplate() != null)
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
                .flatMap(ct -> {
                    Long templateId = ct.getCouponTemplateId();
                    boolean isDownloaded = templateToUserCouponId.containsKey(templateId);
                    Long userCouponId = templateToUserCouponId.get(templateId);
                    List<Long> resolvedPlaceIds = benefitDescriptionResolver.resolvePlaceIdFromTemplateList(ct, places);
                    if (resolvedPlaceIds == null || resolvedPlaceIds.isEmpty()) return Stream.empty();

                    return resolvedPlaceIds.stream()
                            .map(placeId -> Map.entry(
                                    placeId,
                                    CouponResponseDto.from(ct, null, isDownloaded, userCouponId)
                            ));
                })
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));




        Map<Long, DiscountPolicyRef> policyRefMap = templates.stream()
                .flatMap(ct -> {
                    List<Long> resolvedPlaceIds = benefitDescriptionResolver.resolvePlaceIdFromTemplateList(ct, places);
                    if (resolvedPlaceIds == null || resolvedPlaceIds.isEmpty()) return Stream.empty();

                    PlaceType markerType = PlaceType.fromCode(ct.getMarkerCode());
                    return resolvedPlaceIds.stream().map(placeId -> {
                        if (markerType.isFranchise()) {
                            Long franchiseId = null;
                            Long policyId = ct.getDiscountPolicyDetailId();

                            if (policyId != null) {
                                franchiseId = franchiseDiscountPolicyRepository.findById(policyId)
                                        .map(f -> f.getFranchise().getFranchiseId())
                                        .orElse(null);
                            } else {
                                Place place = placeMap.get(placeId);
                                franchiseId = (place != null && place.getFranchise() != null)
                                        ? place.getFranchise().getFranchiseId()
                                        : null;
                            }

                            return Map.entry(placeId, new DiscountPolicyRef(placeId, null, franchiseId));
                        } else {
                            return Map.entry(placeId, new DiscountPolicyRef(placeId, ct.getDiscountPolicyDetailId(), null));
                        }
                    });
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a));



        List<GeneralDiscountPolicy> generalPolicies = generalDiscountPolicyRepository.findByPlaceIn(places);
        for (GeneralDiscountPolicy policy : generalPolicies) {
            Long placeId = policy.getPlace().getPlaceId();
            if (!policyRefMap.containsKey(placeId)) {
                policyRefMap.put(placeId, new DiscountPolicyRef(placeId, policy.getGeneralDiscountPolicyId(), null));
            }
        }

        for (Place place : places) {
            if (!policyRefMap.containsKey(place.getPlaceId()) && place.getFranchise() != null) {
                policyRefMap.put(place.getPlaceId(), new DiscountPolicyRef(
                        place.getPlaceId(), null, place.getFranchise().getFranchiseId()));
            }
        }

        Set<Long> favoriteIds = userId != null
                ? favoritePlaceRepository.findPlaceIdsByUserId(userId)
                : Set.of();

        return projections.stream()
                .map(p -> {

                    Place place = placeMap.get(p.getPlaceId());
                    if (place == null) return null;

                    boolean isFavorite = favoriteIds.contains(place.getPlaceId());

                    DiscountPolicyRef policyRef = policyRefMap.get(place.getPlaceId());

                    String benefitDesc = benefitDescriptionResolver.resolveBenefitDesc(policyRef, membershipCode);

                    Long franchiseId = (policyRef != null && policyRef.franchiseId() != null)
                            ? policyRef.franchiseId()
                            : (place.getFranchise() != null ? place.getFranchise().getFranchiseId() : null);

                    return NearbyPlaceWithCouponsDto.builder()
                            .placeId(place.getPlaceId())
                            .name(place.getPlaceName())
                            .address(place.getAddress())
                            .categoryCode(place.getCategoryCode())
                            .distanceKm(Math.round(p.getDistance() / 100.0) / 10.0)
                            .latitude(place.getLatitude())
                            .longitude(place.getLongitude())
                            .startTime(place.getStartTime())
                            .endTime(place.getEndTime())
                            .tel(place.getTel())
                            .markerCode(place.getMarkerCode())
                            .eventTypeCode(place.getEventTypeCode())
                            .favorite(isFavorite)
                            .discountPolicyDetailId(policyRef != null ? policyRef.discountPolicyDetailId() : null)
                            .franchiseId(franchiseId)
                            .coupons(couponMap.getOrDefault(place.getPlaceId(), List.of()))
                            .benefitDesc(benefitDesc)
                            .build();

                })
                .filter(Objects::nonNull)
                .toList();
    }

    public record DiscountPolicyRef(
            Long placeId,
            Long discountPolicyDetailId,
            Long franchiseId
    ) {}


}
