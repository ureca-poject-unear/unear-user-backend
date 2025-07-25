package com.unear.userservice.place.controller;

import com.unear.userservice.common.docs.place.PlaceApiDocs;
import com.unear.userservice.common.response.ApiResponse;
import com.unear.userservice.common.security.CustomUser;
import com.unear.userservice.common.exception.exception.UnauthorizedException;
import com.unear.userservice.place.dto.request.NearbyPlaceRequestDto;
import com.unear.userservice.place.dto.request.PlaceRequestDto;
import com.unear.userservice.place.dto.response.NearbyPlaceWithCouponsDto;
import com.unear.userservice.place.dto.response.NearestPlaceResponseDto;
import com.unear.userservice.place.dto.response.PlaceRenderResponseDto;
import com.unear.userservice.place.dto.response.PlaceResponseDto;
import com.unear.userservice.place.service.PlaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
@Tag(name = "Place", description = "장소 관련 API")
public class PlaceController {

    private final PlaceService placeService;

    @PlaceApiDocs.GetPlace
    @GetMapping("/{placeId}")
    public ResponseEntity<ApiResponse<NearbyPlaceWithCouponsDto>> getPlace(
            @PathVariable Long placeId,
            @Valid @ParameterObject @ModelAttribute NearbyPlaceRequestDto location,
            @AuthenticationPrincipal CustomUser user
    ) {
        Long userId = (user != null && user.getUser() != null) ? user.getUser().getUserId() : null;
        NearbyPlaceWithCouponsDto dto = placeService.getPlaceDetail(placeId, userId, location.getLatitude(), location.getLongitude());
        return ResponseEntity.ok(ApiResponse.success("장소 조회 성공",dto));
    }

    @PlaceApiDocs.GetFilteredPlaces
    @GetMapping
    public ResponseEntity<ApiResponse<List<PlaceRenderResponseDto>>> getFilteredPlaces(
            @Validated @ParameterObject @ModelAttribute PlaceRequestDto requestDto,
            @AuthenticationPrincipal CustomUser user
    ) {
        Long userId = (user != null && user.getUser() != null) ? user.getUser().getUserId() : null;
        List<PlaceRenderResponseDto> result = placeService.getFilteredPlaces(requestDto, userId);
        return ResponseEntity.ok(ApiResponse.success("장소 목록 조회 성공", result));
    }


    @PlaceApiDocs.GetNearbyPlaces
    @GetMapping("/nearby")
    public ResponseEntity<ApiResponse<List<NearestPlaceResponseDto>>> getNearbyPlaces(
            @Valid @ParameterObject @ModelAttribute NearbyPlaceRequestDto requestDto,
            @AuthenticationPrincipal CustomUser user
    ) {
        Long userId = (user != null && user.getUser() != null) ? user.getUser().getUserId() : null;
        List<NearestPlaceResponseDto> result = placeService.getNearbyPlaces(requestDto, userId);
        return ResponseEntity.ok(ApiResponse.success("주변 매장 조회 성공", result));
    }

    @PlaceApiDocs.GetNearbyPlacesWithCoupons
    @GetMapping("/nearby-with-coupons")
    public ResponseEntity<ApiResponse<List<NearbyPlaceWithCouponsDto>>> getNearbyPlacesWithCoupons(
            @Valid @ParameterObject @ModelAttribute NearbyPlaceRequestDto requestDto,
            @AuthenticationPrincipal CustomUser user
    ) {
        Long userId = (user != null && user.getUser() != null) ? user.getUser().getUserId() : null;
        List<NearbyPlaceWithCouponsDto> result = placeService.getNearbyPlacesWithCoupons(requestDto, userId);
        return ResponseEntity.ok(ApiResponse.success("주변 매장 및 쿠폰 조회 성공", result));
    }




    @PlaceApiDocs.ToggleFavorite
    @PostMapping("/{placeId}/favorite")
    public ResponseEntity<ApiResponse<Boolean>> toggleFavorite(
            @PathVariable Long placeId,
            @AuthenticationPrincipal CustomUser user
    ) {
        if (user == null || user.getUser() == null) {
            throw new UnauthorizedException("인증되지 않은 사용자입니다.");
        }
        Long userId = user.getUser().getUserId();
        boolean isNowFavorite = placeService.toggleFavorite(userId, placeId);
        return ResponseEntity.ok(ApiResponse.success("즐겨찾기 상태 변경 성공", isNowFavorite));
    }

    @PlaceApiDocs.GetFavorites
    @GetMapping("/favorite")
    public ResponseEntity<ApiResponse<List<PlaceResponseDto>>> getFavoritePlaces(
            @AuthenticationPrincipal CustomUser user
    ) {
        if (user == null || user.getUser() == null) {
            throw new UnauthorizedException("인증되지 않은 사용자입니다.");
        }
        Long userId = user.getUser().getUserId();
        List<PlaceResponseDto> favorites = placeService.getUserFavoritePlaces(userId);
        return ResponseEntity.ok(ApiResponse.success("즐겨찾기 장소 목록 조회 성공", favorites));
    }


}


