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
            description = "장소 ID와 사용자의 위도,경도를 기준으로 장소 상세 정보를 조회합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "장소 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlaceRenderResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "DefaultFilter",
                                    summary = "사용자의 위도,경도를 기준으로 장소 상세정보 조회",
                                    value = """
                                            {
                                                "resultCode": 200,
                                                "codeName": "SUCCESS",
                                                "message": "장소 조회 성공",
                                                "data": {
                                                    "placeId": 127,
                                                    "placeName": "파리바게뜨 강서점",
                                                    "placeDesc": "파리바게뜨 강서점입니다",
                                                    "address": "서울특별시 강서구 남부순환로 190 (외발산동,외 2필지 (지하 2층))",
                                                    "latitude": 37.55,
                                                    "longitude": 126.82,
                                                    "benefitCategory": "할인",
                                                    "startTime": 9,
                                                    "endTime": 20,
                                                    "categoryCode": "BAKERY",
                                                    "markerCode": "FRANCHISE",
                                                    "eventCode": "1",
                                                    "franchiseName": "파리바게뜨",
                                                    "distanceKm": 0.9,
                                                    "favorite": false
                                                }
                                            }
                """
                            ),
                            @ExampleObject(
                                    name = "NotFoundFilter",
                                    summary = "존재하지 않는 place_id",
                                    value = """
                                    {
                                        "resultCode": 404,
                                        "codeName": "PLACE_NOT_FOUND",
                                        "message": "장소 정보를 찾을 수 없습니다.",
                                        "data": null
                                    }
                """
                            )
                    }
            )
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface GetPlace {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "필터링된 장소 목록 조회",
            description = "카테고리, 검색어, 좌표 범위 등을 통한 장소 목록을 조회합니다. ( distanceKm는 현재 사용자의 위치와 장소까지의 거리를 의미합니다. 0.9 -> 0.9km )"
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
                                                             "placeId": 209,
                                                             "latitude": 37.55,
                                                             "longitude": 126.83,
                                                             "categoryCode": "FOOD",
                                                             "markerCode": "FRANCHISE",
                                                             "eventCode": "NONE",
                                                             "benefitCategory": "할인",
                                                             "favorite": false
                                                         },
                                                         {
                                                             "placeId": 281,
                                                             "latitude": 37.55,
                                                             "longitude": 126.83,
                                                             "categoryCode": "BAKERY",
                                                             "markerCode": "FRANCHISE",
                                                             "eventCode": "NONE",
                                                             "benefitCategory": "적립",
                                                             "favorite": false
                                                         },
                                                         {
                                                             "placeId": 176,
                                                             "latitude": 37.56,
                                                             "longitude": 126.83,
                                                             "categoryCode": "BEAUTY",
                                                             "markerCode": "FRANCHISE",
                                                             "eventCode": "NONE",
                                                             "benefitCategory": "무료 서비스",
                                                             "favorite": false
                                                         },
                                                         {
                                                             "placeId": 127,
                                                             "latitude": 37.55,
                                                             "longitude": 126.82,
                                                             "categoryCode": "BAKERY",
                                                             "markerCode": "FRANCHISE",
                                                             "eventCode": "NONE",
                                                             "benefitCategory": "할인",
                                                             "favorite": false
                                                         },
                                                         {
                                                             "placeId": 133,
                                                             "latitude": 37.55,
                                                             "longitude": 126.82,
                                                             "categoryCode": "LIFE",
                                                             "markerCode": "FRANCHISE",
                                                             "eventCode": "NONE",
                                                             "benefitCategory": "할인",
                                                             "favorite": false
                                                         },
                                                         {
                                                             "placeId": 122,
                                                             "latitude": 37.55,
                                                             "longitude": 126.83,
                                                             "categoryCode": "FOOD",
                                                             "markerCode": "FRANCHISE",
                                                             "eventCode": "NONE",
                                                             "benefitCategory": "적립",
                                                             "favorite": true
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
                                                          "placeId": 133,
                                                          "latitude": 37.55,
                                                          "longitude": 126.82,
                                                          "categoryCode": "LIFE",
                                                          "markerCode": "FRANCHISE",
                                                          "eventCode": "NONE",
                                                          "benefitCategory": "할인",
                                                          "favorite": false
                                                      },
                                                      {
                                                          "placeId": 122,
                                                          "latitude": 37.55,
                                                          "longitude": 126.83,
                                                          "categoryCode": "FOOD",
                                                          "markerCode": "FRANCHISE",
                                                          "eventCode": "NONE",
                                                          "benefitCategory": "적립",
                                                          "favorite": true
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
                                                             "placeId": 209,
                                                             "latitude": 37.55,
                                                             "longitude": 126.83,
                                                             "categoryCode": "FOOD",
                                                             "markerCode": "FRANCHISE",
                                                             "eventCode": "NONE",
                                                             "benefitCategory": "할인",
                                                             "favorite": false
                                                         },
                                                         {
                                                             "placeId": 122,
                                                             "latitude": 37.55,
                                                             "longitude": 126.83,
                                                             "categoryCode": "FOOD",
                                                             "markerCode": "FRANCHISE",
                                                             "eventCode": "NONE",
                                                             "benefitCategory": "적립",
                                                             "favorite": true
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
                                                          "eventCode": "NONE",
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
