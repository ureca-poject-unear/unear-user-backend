package com.unear.userservice.common.docs.place;

import io.swagger.v3.oas.annotations.Operation;


import java.lang.annotation.*;

public class PlaceApiDocs {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "장소 상세 조회",
            description = "장소 ID와 사용자 위치로 상세 정보를 조회합니다."
    )
    public @interface GetPlace {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "필터링된 장소 목록 조회",
            description = "카테고리, 검색어, 좌표 범위 등을 통한 장소 목록 조회"
    )
    public @interface GetFilteredPlaces {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "주변 장소 조회",
            description = "사용자 위치 기반으로 가까운 장소를 거리순으로 조회합니다."
    )
    public @interface GetNearbyPlaces {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "즐겨찾기 토글",
            description = "해당 장소에 대해 즐겨찾기 등록/해제 토글"
    )
    public @interface ToggleFavorite {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "즐겨찾기 장소 목록 조회",
            description = "사용자의 즐겨찾기 장소 목록을 반환합니다."
    )
    public @interface GetFavorites {
    }
}
