package com.unear.userservice.place.controller;

import com.unear.userservice.common.response.ApiResponse;
import com.unear.userservice.common.security.CustomUser;
import com.unear.userservice.exception.exception.UnauthorizedException;
import com.unear.userservice.place.dto.request.NearbyPlaceRequestDto;
import com.unear.userservice.place.dto.request.PlaceRequestDto;
import com.unear.userservice.place.dto.response.NearestPlaceResponseDto;
import com.unear.userservice.place.dto.response.PlaceRenderResponseDto;
import com.unear.userservice.place.dto.response.PlaceResponseDto;
import com.unear.userservice.place.entity.Place;
import com.unear.userservice.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/{placeId}")
    public ResponseEntity<ApiResponse<PlaceResponseDto>> getPlace(
            @PathVariable Long placeId,
            @ModelAttribute NearbyPlaceRequestDto location,
            @AuthenticationPrincipal CustomUser user
    ) {
        Long userId = (user != null && user.getUser() != null) ? user.getUser().getUserId() : null;
        PlaceResponseDto dto = placeService.getPlaceDetail(placeId, userId, location.getLatitude(), location.getLongitude());
        return ResponseEntity.ok(ApiResponse.success("장소 조회 성공",dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PlaceRenderResponseDto>>> getFilteredPlaces(
            @ModelAttribute PlaceRequestDto requestDto,
            @AuthenticationPrincipal CustomUser user
    ) {
        Long userId = (user != null && user.getUser() != null) ? user.getUser().getUserId() : null;
        List<PlaceRenderResponseDto> result = placeService.getFilteredPlaces(requestDto, userId);
        return ResponseEntity.ok(ApiResponse.success("장소 목록 조회 성공", result));
    }


    @GetMapping("/nearby")
    public ResponseEntity<ApiResponse<List<NearestPlaceResponseDto>>> getNearbyPlaces(
            @ModelAttribute NearbyPlaceRequestDto requestDto,
            @AuthenticationPrincipal CustomUser user
    ) {
        Long userId = (user != null && user.getUser() != null) ? user.getUser().getUserId() : null;
        List<NearestPlaceResponseDto> result = placeService.getNearbyPlaces(requestDto, userId);
        return ResponseEntity.ok(ApiResponse.success("주변 매장 조회 성공", result));
    }



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


