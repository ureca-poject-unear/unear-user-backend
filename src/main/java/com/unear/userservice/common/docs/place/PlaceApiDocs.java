package com.unear.userservice.common.docs.place;

import com.unear.userservice.place.dto.response.PlaceRenderResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


import java.lang.annotation.*;

public class PlaceApiDocs {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "장소 상세 조회",
            description = "장소 ID와 사용자 위치로 상세 정보를 조회합니다."
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface GetPlace {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "필터링된 장소 목록 조회",
            description = "카테고리, 검색어, 좌표 범위 등을 통한 장소 목록을 조회합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "장소 목록 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlaceRenderResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "DefaultFilter",
                                    summary = "기본 필터링 조회 예시 ( 화면상의 좌하단,우상단 위도경도만 사용 )",
                                    value = """
                                            {
                                                "resultCode": 200,
                                                "codeName": "SUCCESS",
                                                "message": "장소 목록 조회 성공",
                                                "data": [
                                                    {
                                                        "placeId": 119,
                                                        "latitude": 37.5458745,
                                                        "longitude": 126.8357654,
                                                        "categoryCode": "CAFE",
                                                        "markerCode": "BASIC",
                                                        "eventCode": "1",
                                                        "benefitCategory": "상품 증정",
                                                        "favorite": false
                                                    },
                                                    {
                                                        "placeId": 122,
                                                        "latitude": 37.552941,
                                                        "longitude": 126.826762,
                                                        "categoryCode": "FOOD",
                                                        "markerCode": "FRANCHISE",
                                                        "eventCode": "1",
                                                        "benefitCategory": "적립",
                                                        "favorite": false
                                                    },
                                                    {
                                                        "placeId": 127,
                                                        "latitude": 37.5485157,
                                                        "longitude": 126.8193085,
                                                        "categoryCode": "BAKERY",
                                                        "markerCode": "FRANCHISE",
                                                        "eventCode": "1",
                                                        "benefitCategory": "할인",
                                                        "favorite": false
                                                    },
                                                    {
                                                        "placeId": 133,
                                                        "latitude": 37.5469,
                                                        "longitude": 126.821378,
                                                        "categoryCode": "LIFE",
                                                        "markerCode": "FRANCHISE",
                                                        "eventCode": "1",
                                                        "benefitCategory": "할인",
                                                        "favorite": false
                                                    },
                                                    {
                                                        "placeId": 176,
                                                        "latitude": 37.5592015,
                                                        "longitude": 126.8261452,
                                                        "categoryCode": "BEAUTY",
                                                        "markerCode": "FRANCHISE",
                                                        "eventCode": "1",
                                                        "benefitCategory": "무료 서비스",
                                                        "favorite": false
                                                    },
                                                    {
                                                        "placeId": 209,
                                                        "latitude": 37.5478555,
                                                        "longitude": 126.833634,
                                                        "categoryCode": "FOOD",
                                                        "markerCode": "FRANCHISE",
                                                        "eventCode": "1",
                                                        "benefitCategory": "할인",
                                                        "favorite": false
                                                    },
                                                    {
                                                        "placeId": 281,
                                                        "latitude": 37.5490632,
                                                        "longitude": 126.8343112,
                                                        "categoryCode": "BAKERY",
                                                        "markerCode": "FRANCHISE",
                                                        "eventCode": "1",
                                                        "benefitCategory": "적립",
                                                        "favorite": false
                                                    },
                                                    {
                                                        "placeId": 299,
                                                        "latitude": 37.5481862,
                                                        "longitude": 126.8351412,
                                                        "categoryCode": "FOOD",
                                                        "markerCode": "FRANCHISE",
                                                        "eventCode": "1",
                                                        "benefitCategory": "무료 서비스",
                                                        "favorite": false
                                                    },
                                                    {
                                                        "placeId": 313,
                                                        "latitude": 37.548121,
                                                        "longitude": 126.8356556,
                                                        "categoryCode": "BAKERY",
                                                        "markerCode": "LOCAL",
                                                        "eventCode": "1",
                                                        "benefitCategory": "할인",
                                                        "favorite": false
                                                    }
                                                ]
                                            }
                """
                            ),
                            @ExampleObject(
                                    name = "NameFilter",
                                    summary = "이름(검색어) 기반 필터링 예시",
                                    value = """
                                            {
                                                 "resultCode": 200,
                                                 "codeName": "SUCCESS",
                                                 "message": "장소 목록 조회 성공",
                                                 "data": [
                                                     {
                                                         "placeId": 122,
                                                         "latitude": 37.55,
                                                         "longitude": 126.83,
                                                         "categoryCode": "FOOD",
                                                         "markerCode": "FRANCHISE",
                                                         "eventCode": "1",
                                                         "benefitCategory": "적립",
                                                         "favorite": false
                                                     },
                                                     {
                                                         "placeId": 133,
                                                         "latitude": 37.55,
                                                         "longitude": 126.82,
                                                         "categoryCode": "LIFE",
                                                         "markerCode": "FRANCHISE",
                                                         "eventCode": "1",
                                                         "benefitCategory": "할인",
                                                         "favorite": false
                                                     }
                                                 ]
                                             }
                """
                            ),
                            @ExampleObject(
                                    name = "CategoryFilter",
                                    summary = "장소 카테고리 코드기반 필터링 예시",
                                    value = """
                                            {
                                                "resultCode": 200,
                                                "codeName": "SUCCESS",
                                                "message": "장소 목록 조회 성공",
                                                "data": [
                                                    {
                                                        "placeId": 122,
                                                        "latitude": 37.55,
                                                        "longitude": 126.83,
                                                        "categoryCode": "FOOD",
                                                        "markerCode": "FRANCHISE",
                                                        "eventCode": "1",
                                                        "benefitCategory": "적립",
                                                        "favorite": false
                                                    },
                                                    {
                                                        "placeId": 209,
                                                        "latitude": 37.55,
                                                        "longitude": 126.83,
                                                        "categoryCode": "FOOD",
                                                        "markerCode": "FRANCHISE",
                                                        "eventCode": "1",
                                                        "benefitCategory": "할인",
                                                        "favorite": false
                                                    }
                                                ]
                                            }
                """
                            ),
                            @ExampleObject(
                                    name = "FavoriteOnly",
                                    summary = "즐겨찾기 장소만 필터링된 예시",
                                    value = """
                                            {
                                                 "resultCode": 200,
                                                 "codeName": "SUCCESS",
                                                 "message": "장소 목록 조회 성공",
                                                 "data": [
                                                     {
                                                         "placeId": 122,
                                                         "latitude": 37.55,
                                                         "longitude": 126.83,
                                                         "categoryCode": "FOOD",
                                                         "markerCode": "FRANCHISE",
                                                         "eventCode": "1",
                                                         "benefitCategory": "적립",
                                                         "favorite": true
                                                     }
                                                 ]
                                             }
                """
                            )
                    }
            )
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface GetFilteredPlaces {
    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "주변 장소 조회",
            description = "사용자 위치 기반으로 가까운 장소를 거리순으로 조회합니다."
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface GetNearbyPlaces {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "즐겨찾기 토글",
            description = "해당 장소에 대해 즐겨찾기 등록/해제 토글"
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface ToggleFavorite {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "즐겨찾기 장소 목록 조회",
            description = "사용자의 즐겨찾기 장소 목록을 반환합니다."
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface GetFavorites {
    }
}
