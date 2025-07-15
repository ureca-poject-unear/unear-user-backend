package com.unear.userservice.place.controller;

import com.unear.userservice.common.response.ApiResponse;
import com.unear.userservice.common.security.CustomUser;
import com.unear.userservice.place.dto.request.PlaceRequestDto;
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
            @AuthenticationPrincipal CustomUser user
    ) {
        Long userId = (user != null && user.getUser() != null) ? user.getUser().getUserId() : null;
        PlaceResponseDto dto = placeService.getPlaceDetailWithFavorite(placeId, userId);
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

    @PostMapping("/{placeId}/favorite")
    public ResponseEntity<ApiResponse<Boolean>> toggleFavorite(
            @PathVariable Long placeId,
            @AuthenticationPrincipal CustomUser user
    ) {
        Long userId = (user != null && user.getUser() != null) ? user.getUser().getUserId() : null;
        boolean isNowFavorite = placeService.toggleFavorite(userId, placeId);
        return ResponseEntity.ok(ApiResponse.success("즐겨찾기 상태 변경 성공", isNowFavorite));
    }


}


