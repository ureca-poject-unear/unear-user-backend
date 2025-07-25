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
                                            "placeId": 132,
                                            "name": "GS THE FRESH 방화점",
                                            "address": "서울특별시 강서구 양천로 19, 1층 104호 (방화동, 1동)",
                                            "categoryCode": "SHOPPING",
                                            "distanceKm": 3.9,
                                            "latitude": 37.5800000,
                                            "longitude": 126.8200000,
                                            "startTime": 9,
                                            "endTime": 20,
                                            "favorite": false,
                                            "markerCode": "FRANCHISE",
                                            "eventTypeCode": "NONE",
                                            "tel": "080-855-5525",
                                            "discountPolicyDetailId": null,
                                            "franchiseId": 6,
                                            "benefitDesc": "3,000원 금액 할인 쿠폰 증정",
                                            "coupons": [
                                                {
                                                    "couponTemplateId": 291,
                                                    "couponName": "GS THE FRESH VIP 쿠폰",
                                                    "discountCode": "COUPON_FIXED",
                                                    "membershipCode": "VIP",
                                                    "discountInfo": null,
                                                    "couponStart": "2025-07-01T00:00:00",
                                                    "couponEnd": "2025-07-31T00:00:00",
                                                    "userCouponId": null,
                                                    "downloaded": false
                                                }
                                            ]
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
            summary = "주변 가까운 장소 리스트 조회",
            description = "사용자 위치 기반으로 가까운 장소를 거리순으로 5개 조회합니다."
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
                                    summary = "사용자 기준 가까운 장소 리스트 조회",
                                    value = """
                                            {
                                                "resultCode": 200,
                                                "codeName": "SUCCESS",
                                                "message": "주변 매장 조회 성공",
                                                "data": [
                                                    {
                                                        "placeId": 127,
                                                        "distanceKm": 0.9
                                                    },
                                                    {
                                                        "placeId": 133,
                                                        "distanceKm": 0.9
                                                    },
                                                    {
                                                        "placeId": 201,
                                                        "distanceKm": 1.0
                                                    },
                                                    {
                                                        "placeId": 128,
                                                        "distanceKm": 1.6
                                                    },
                                                    {
                                                        "placeId": 209,
                                                        "distanceKm": 1.8
                                                    }
                                                ]
                                            }
                """
                            )
                    }
            )
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface GetNearbyPlaces {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "즐겨찾기 토글",
            description = "해당 장소에 대해 즐겨찾기 등록/해제 토글입니다."
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
                                    summary = "장소에 대한 즐겨찾기 토글",
                                    value = """
                                    {
                                        "resultCode": 200,
                                        "codeName": "SUCCESS",
                                        "message": "즐겨찾기 상태 변경 성공",
                                        "data": true
                                    }
                """
                            )
                    }
            )
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
    @ApiResponse(
            responseCode = "200",
            description = "장소 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlaceRenderResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "DefaultFilter",
                                    summary = "사용자의 즐겨찾기 장소 목록을 반환",
                                    value = """
                                            {
                                                "resultCode": 200,
                                                "codeName": "SUCCESS",
                                                "message": "즐겨찾기 장소 목록 조회 성공",
                                                "data": [
                                                    {
                                                        "placeId": 155,
                                                        "placeName": "SEOUL SKY",
                                                        "placeDesc": "하늘 위 새로운 세상 (동반 3인 10%)",
                                                        "address": "서울특별시 송파구 올림픽로 300 117~123층",
                                                        "latitude": 37.51,
                                                        "longitude": 127.1,
                                                        "benefitCategory": "무료 서비스",
                                                        "startTime": 9,
                                                        "endTime": 20,
                                                        "categoryCode": "ACTIVITY",
                                                        "markerCode": "BASIC",
                                                        "eventCode": "1",
                                                        "franchiseName": null,
                                                        "favorite": true
                                                    },
                                                    {
                                                        "placeId": 122,
                                                        "placeName": "GS25 화곡점",
                                                        "placeDesc": "GS25 화곡점입니다",
                                                        "address": "서울특별시 강서구 수명로 68-35, 웨스트엔드중정 3층 304호 (내발산동)",
                                                        "latitude": 37.55,
                                                        "longitude": 126.83,
                                                        "benefitCategory": "적립",
                                                        "startTime": 0,
                                                        "endTime": 0,
                                                        "categoryCode": "FOOD",
                                                        "markerCode": "FRANCHISE",
                                                        "eventCode": "1",
                                                        "franchiseName": "GS 25",
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
    public @interface GetFavorites {
    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "가까운장소 및 쿠폰 템플릿 조회",
            description = "사용자의 위도 경도를 기준으로 가까운장소 및 쿠폰 템플릿 조회합니다. 현재 coupon_template이 없는 장소는 coupons가 비어져있는 상태로 반환됩니다. \n"
            +"downloaded == true 인 쿠폰은 현재 사용자가 다운받은 상태입니다. benefitDesc == null 인 곳은 할인헤택이 따로 존재하지않는 장소입니다"
    )
    @ApiResponse(
            responseCode = "200",
            description = "사용자 기준으로 가까운장소 및 쿠폰 템플릿 조회",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlaceRenderResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "DefaultFilter",
                                    summary = "사용자 기준으로 가까운장소 및 쿠폰 템플릿 조회",
                                    value = """
                                            {
                                                "resultCode": 200,
                                                "codeName": "SUCCESS",
                                                "message": "주변 매장 및 쿠폰 조회 성공",
                                                "data": [
                                                    {
                                                        "placeId": 158,
                                                        "name": "롯데월드 어드벤처 부산",
                                                        "address": "부산 기장군 기장읍 동부산관광로 42",
                                                        "categoryCode": "ACTIVITY",
                                                        "distanceKm": 0.0,
                                                        "latitude": 35.1900000,
                                                        "longitude": 129.2100000,
                                                        "startTime": 9,
                                                        "endTime": 20,
                                                        "favorite": false,
                                                        "markerCode": "BASIC",
                                                        "eventTypeCode": "NONE",
                                                        "tel": "1600-2000",
                                                        "discountPolicyDetailId": 12,
                                                        "franchiseId": null,
                                                        "benefitDesc": "25% 할인 쿠폰 증정 (최대 25,000원 할인)",
                                                        "coupons": [
                                                            {
                                                                "couponTemplateId": 54,
                                                                "couponName": "롯데월드 어드벤처 부산 전용 쿠폰",
                                                                "discountCode": "COUPON_PERCENT",
                                                                "membershipCode": "ALL",
                                                                "discountInfo": null,
                                                                "couponStart": "2025-07-01T00:00:00",
                                                                "couponEnd": "2025-07-31T00:00:00",
                                                                "userCouponId": null,
                                                                "downloaded": false
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        "placeId": 406,
                                                        "name": "원더빌리지",
                                                        "address": "경기 고양시 덕양구 고양대로 1955 3층 원더빌리지",
                                                        "categoryCode": "ACTIVITY",
                                                        "distanceKm": 4.9,
                                                        "latitude": 35.1600000,
                                                        "longitude": 129.1700000,
                                                        "startTime": 9,
                                                        "endTime": 20,
                                                        "favorite": false,
                                                        "markerCode": "BASIC",
                                                        "eventTypeCode": "NONE",
                                                        "tel": "031-5173-3455",
                                                        "discountPolicyDetailId": null,
                                                        "franchiseId": null,
                                                        "benefitDesc": null,
                                                        "coupons": []
                                                    },
                                                    {
                                                        "placeId": 147,
                                                        "name": "클럽디 오아시스",
                                                        "address": "부산 해운대구 달맞이길 30 엘시티",
                                                        "categoryCode": "ACTIVITY",
                                                        "distanceKm": 4.9,
                                                        "latitude": 35.1600000,
                                                        "longitude": 129.1700000,
                                                        "startTime": 9,
                                                        "endTime": 20,
                                                        "favorite": false,
                                                        "markerCode": "BASIC",
                                                        "eventTypeCode": "NONE",
                                                        "tel": "080-855-5523",
                                                        "discountPolicyDetailId": 1,
                                                        "franchiseId": null,
                                                        "benefitDesc": "10% 할인 쿠폰 증정 (최대 10,000원 할인)",
                                                        "coupons": [
                                                            {
                                                                "couponTemplateId": 52,
                                                                "couponName": "클럽디 오아시스 전용 쿠폰",
                                                                "discountCode": "COUPON_PERCENT",
                                                                "membershipCode": "ALL",
                                                                "discountInfo": null,
                                                                "couponStart": "2025-07-01T00:00:00",
                                                                "couponEnd": "2025-07-31T00:00:00",
                                                                "userCouponId": null,
                                                                "downloaded": false
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        "placeId": 359,
                                                        "name": "바다횟집",
                                                        "address": "부산시 해운대구 구남로41번길 15",
                                                        "categoryCode": "FOOD",
                                                        "distanceKm": 5.1,
                                                        "latitude": 35.1700000,
                                                        "longitude": 129.1600000,
                                                        "startTime": 9,
                                                        "endTime": 20,
                                                        "favorite": false,
                                                        "markerCode": "LOCAL",
                                                        "eventTypeCode": "NONE",
                                                        "tel": "0507-1346-7177\\n",
                                                        "discountPolicyDetailId": 70,
                                                        "franchiseId": null,
                                                        "benefitDesc": "멤버십 혜택: 1,000원당 100원 할인",
                                                        "coupons": []
                                                    },
                                                    {
                                                        "placeId": 356,
                                                        "name": "수산물직판장태화점",
                                                        "address": "울산 중구 오산4길 15, 1층",
                                                        "categoryCode": "FOOD",
                                                        "distanceKm": 5.6,
                                                        "latitude": 35.1600000,
                                                        "longitude": 129.1600000,
                                                        "startTime": 9,
                                                        "endTime": 20,
                                                        "favorite": false,
                                                        "markerCode": "LOCAL",
                                                        "eventTypeCode": "NONE",
                                                        "tel": "052-222-8192\\n",
                                                        "discountPolicyDetailId": 67,
                                                        "franchiseId": null,
                                                        "benefitDesc": "멤버십 혜택: 1,000원당 100원 할인",
                                                        "coupons": []
                                                    }
                                                ]
                                            }
                """
                            )
                    }
            )
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface GetNearbyPlacesWithCoupons {
    }

}
