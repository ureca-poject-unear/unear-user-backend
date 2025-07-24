package com.unear.userservice.common.docs.event;

import com.unear.userservice.event.dto.response.EventPlaceResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

public class EventApiDocs {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "이벤트 상세 조회",
            description = "이벤트 ID를 통해 이벤트 지역, 매장, 쿠폰 정보를 조회합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "이벤트 정보 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EventPlaceResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "Event Read",
                                    summary = "이벤트 이름, 설명, 팝업스토어 정보, 이벤트 지역내 제휴처 정보 조회",
                                    value = """
                                            {
                                                            "resultCode": 200,
                                                            "codeName": "SUCCESS",
                                                            "message": "요청이 성공적으로 처리되었습니다.",
                                                            "data": {
                                                                "eventId": 1,
                                                                "eventName": "강서 팝업 이벤트",
                                                                "description": "강서 팝업 매장 + 제휴처 할인 행사",
                                                                "latitude": 37.5511119,
                                                                "longitude": 126.8493014,
                                                                "radius": 1000,
                                                                "startDate": "2025-08-01",
                                                                "endDate": "2025-08-31",
                                                                "storeList": [
                                                                    {
                                                                        "placeId": 465,
                                                                        "placeName": "강서구청 팝업스토어",
                                                                        "placeDesc": "이벤트 한정 상품 제공",
                                                                        "address": "서울 강서구 화곡로 302",
                                                                        "latitude": 37.5500000,
                                                                        "longitude": 126.8400000,
                                                                        "benefitCategory": "UNEAR",
                                                                        "startTime": 10,
                                                                        "endTime": 20,
                                                                        "categoryCode": "CAFE",
                                                                        "markerCode": "POPUP",
                                                                        "eventCode": "REQUIRE",
                                                                        "franchiseName": null,
                                                                        "distanceKm": null,
                                                                        "favorite": true
                                                                    },
                                                                    {
                                                                        "placeId": 138,
                                                                        "placeName": "GS25 화곡61길점",
                                                                        "placeDesc": "대한민국 국가대표 편의점 GS25",
                                                                        "address": "서울특별시 강서구 화곡로61길 31, 1층 (등촌동, 3동)",
                                                                        "latitude": 37.5600000,
                                                                        "longitude": 126.8500000,
                                                                        "benefitCategory": "할인",
                                                                        "startTime": 0,
                                                                        "endTime": 0,
                                                                        "categoryCode": "LIFE",
                                                                        "markerCode": "FRANCHISE",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": "GS 25",
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    },
                                                                    {
                                                                        "placeId": 298,
                                                                        "placeName": "라그릴리아 등촌점",
                                                                        "placeDesc": "정통 이탈리안 그릴 및 파스타 요리를 선보이는 이탈리안 캐주얼 레스토랑입니다.",
                                                                        "address": "서울특별시 강서구 공항대로41길 34, 1층 101호 (등촌동, 3동 플러스존)",
                                                                        "latitude": 37.5600000,
                                                                        "longitude": 126.8500000,
                                                                        "benefitCategory": "할인",
                                                                        "startTime": 9,
                                                                        "endTime": 20,
                                                                        "categoryCode": "FOOD",
                                                                        "markerCode": "FRANCHISE",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": "라그릴리아",
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    },
                                                                    {
                                                                        "placeId": 299,
                                                                        "placeName": "서울드래곤시티 강서점",
                                                                        "placeDesc": "그랜드머큐어, 노보텔, 노보텔스위트, 이비스스타일 4개의 브랜드로 이루어진 국내 최초 호텔 플렉스",
                                                                        "address": "서울특별시 강서구 강서로45길 49-4, 상가동 1층 7호 (내발산동, 태승훼미리아파트)",
                                                                        "latitude": 37.5500000,
                                                                        "longitude": 126.8400000,
                                                                        "benefitCategory": "무료 서비스",
                                                                        "startTime": 9,
                                                                        "endTime": 20,
                                                                        "categoryCode": "FOOD",
                                                                        "markerCode": "FRANCHISE",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": "서울드래곤시티",
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    },
                                                                    {
                                                                        "placeId": 119,
                                                                        "placeName": "허니네커피",
                                                                        "placeDesc": "허니네커피 프렌차이즈 아닙니다.",
                                                                        "address": "서울특별시 강서구 강서로45가길 47, 2층 202호 (화곡동, 3동)",
                                                                        "latitude": 37.5500000,
                                                                        "longitude": 126.8400000,
                                                                        "benefitCategory": "상품 증정",
                                                                        "startTime": 9,
                                                                        "endTime": 20,
                                                                        "categoryCode": "CAFE",
                                                                        "markerCode": "BASIC",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": null,
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    },
                                                                    {
                                                                        "placeId": 313,
                                                                        "placeName": "공병득쉐프",
                                                                        "placeDesc": "다양한 빵들을 시식 후 구매 가능한 인심 좋은 맛집",
                                                                        "address": "서울특별시 강서구 강서로 45길 10",
                                                                        "latitude": 37.5500000,
                                                                        "longitude": 126.8400000,
                                                                        "benefitCategory": "할인",
                                                                        "startTime": 9,
                                                                        "endTime": 20,
                                                                        "categoryCode": "BAKERY",
                                                                        "markerCode": "LOCAL",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": null,
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    },
                                                                    {
                                                                        "placeId": 140,
                                                                        "placeName": "GS25 등촌217점",
                                                                        "placeDesc": "대한민국 국가대표 편의점 GS25",
                                                                        "address": "서울특별시 강서구 등촌로 217, 1층 (등촌동, 2동)",
                                                                        "latitude": 37.5500000,
                                                                        "longitude": 126.8600000,
                                                                        "benefitCategory": "할인",
                                                                        "startTime": 0,
                                                                        "endTime": 0,
                                                                        "categoryCode": "LIFE",
                                                                        "markerCode": "FRANCHISE",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": "GS 25",
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    },
                                                                    {
                                                                        "placeId": 210,
                                                                        "placeName": "피자헛 방화점",
                                                                        "placeDesc": "피자 브랜드를 넘어 일상의 가치와 즐거움을 공유하다.",
                                                                        "address": "서울특별시 강서구 등촌로51길 11, 1층 (등촌동, 2동)",
                                                                        "latitude": 37.5500000,
                                                                        "longitude": 126.8600000,
                                                                        "benefitCategory": "할인",
                                                                        "startTime": 9,
                                                                        "endTime": 20,
                                                                        "categoryCode": "FOOD",
                                                                        "markerCode": "FRANCHISE",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": "피자헛",
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    },
                                                                    {
                                                                        "placeId": 126,
                                                                        "placeName": "맥도날드 강북점",
                                                                        "placeDesc": "맥도날드 강북점입니다",
                                                                        "address": "서울특별시 강서구 등촌로 181, 1층 (등촌동, 2동 실로암안과병원)",
                                                                        "latitude": 37.5500000,
                                                                        "longitude": 126.8600000,
                                                                        "benefitCategory": "할인",
                                                                        "startTime": 9,
                                                                        "endTime": 20,
                                                                        "categoryCode": "BAKERY",
                                                                        "markerCode": "FRANCHISE",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": "맥도날드",
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    },
                                                                    {
                                                                        "placeId": 125,
                                                                        "placeName": "커피에이엠",
                                                                        "placeDesc": "커피에이엠 프렌차이즈 아닙니다",
                                                                        "address": "서울특별시 강서구 화곡로 296, 1층 110호 (화곡동, 6동)",
                                                                        "latitude": 37.5500000,
                                                                        "longitude": 126.8500000,
                                                                        "benefitCategory": "상품 증정",
                                                                        "startTime": 9,
                                                                        "endTime": 20,
                                                                        "categoryCode": "CAFE",
                                                                        "markerCode": "BASIC",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": null,
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    },
                                                                    {
                                                                        "placeId": 283,
                                                                        "placeName": "파리바게뜨 등촌점",
                                                                        "placeDesc": "신선한 빵과 만나는 정통 유럽풍 베이커리",
                                                                        "address": "서울특별시 강서구 공항대로41길 51, 1층 129호 (등촌동, 3동 세신그린코아)",
                                                                        "latitude": 37.5600000,
                                                                        "longitude": 126.8500000,
                                                                        "benefitCategory": "적립",
                                                                        "startTime": 9,
                                                                        "endTime": 20,
                                                                        "categoryCode": "BAKERY",
                                                                        "markerCode": "FRANCHISE",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": "파리바게뜨",
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    },
                                                                    {
                                                                        "placeId": 284,
                                                                        "placeName": "뚜레쥬르 화곡서광점",
                                                                        "placeDesc": "재료부터 다른 건강한 베이커리 뚜레쥬르",
                                                                        "address": "서울특별시 강서구 화곡로 292, 1층 104호 (화곡동, 6동 서광프리메라)",
                                                                        "latitude": 37.5500000,
                                                                        "longitude": 126.8500000,
                                                                        "benefitCategory": "할인",
                                                                        "startTime": 9,
                                                                        "endTime": 20,
                                                                        "categoryCode": "BAKERY",
                                                                        "markerCode": "FRANCHISE",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": "뚜레쥬르",
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    },
                                                                    {
                                                                        "placeId": 134,
                                                                        "placeName": "GS25 등촌공항대로점",
                                                                        "placeDesc": "대한민국 국가대표 편의점 GS25",
                                                                        "address": "서울특별시 강서구 공항대로55길 10, 1층 (등촌동, 1동)",
                                                                        "latitude": 37.5500000,
                                                                        "longitude": 126.8600000,
                                                                        "benefitCategory": "할인",
                                                                        "startTime": 0,
                                                                        "endTime": 0,
                                                                        "categoryCode": "LIFE",
                                                                        "markerCode": "FRANCHISE",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": "GS 25",
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    },
                                                                    {
                                                                        "placeId": 296,
                                                                        "placeName": "라그릴리아 강서점",
                                                                        "placeDesc": "정통 이탈리안 그릴 및 파스타 요리를 선보이는 이탈리안 캐주얼 레스토랑입니다.",
                                                                        "address": "서울특별시 강서구 강서로 259 (내발산동,우장산역엠버리움빌딩 (지상 1~2층) 101호 201~202호)",
                                                                        "latitude": 37.5500000,
                                                                        "longitude": 126.8400000,
                                                                        "benefitCategory": "할인",
                                                                        "startTime": 9,
                                                                        "endTime": 20,
                                                                        "categoryCode": "FOOD",
                                                                        "markerCode": "FRANCHISE",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": "라그릴리아",
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    },
                                                                    {
                                                                        "placeId": 324,
                                                                        "placeName": "보나세라 오가닉",
                                                                        "placeDesc": "치즈식빵과 벚꽃빵 맛집\\n",
                                                                        "address": "서울특별시 강서구 등촌로 55길 39\\n",
                                                                        "latitude": 37.5500000,
                                                                        "longitude": 126.8600000,
                                                                        "benefitCategory": "할인",
                                                                        "startTime": 9,
                                                                        "endTime": 20,
                                                                        "categoryCode": "BAKERY",
                                                                        "markerCode": "LOCAL",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": null,
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    },
                                                                    {
                                                                        "placeId": 131,
                                                                        "placeName": "GS THE FRESH 등촌역점",
                                                                        "placeDesc": "GS THE FRESH",
                                                                        "address": "서울특별시 강서구 양천로 476, 5호 (등촌동,(지상 7층))",
                                                                        "latitude": 37.5500000,
                                                                        "longitude": 126.8600000,
                                                                        "benefitCategory": "할인",
                                                                        "startTime": 9,
                                                                        "endTime": 20,
                                                                        "categoryCode": "SHOPPING",
                                                                        "markerCode": "FRANCHISE",
                                                                        "eventCode": "GENERAL",
                                                                        "franchiseName": "GS THE FRESH",
                                                                        "distanceKm": null,
                                                                        "favorite": false
                                                                    }
                                                                ],
                                                                "coupons": [
                                                                    {
                                                                        "couponTemplateId": 45,
                                                                        "couponName": "팝업 선착순 할인 쿠폰",
                                                                        "discountCode": "COUPON_FCFS",
                                                                        "membershipCode": "ALL",
                                                                        "discountInfo": null,
                                                                        "couponStart": "2025-07-01T00:00:00",
                                                                        "couponEnd": "2025-08-31T00:00:00",
                                                                        "userCouponId": null,
                                                                        "downloaded": false
                                                                    }
                                                                ]
                                                            }
                                                        }                      
                                            """
                            )
                    }
            )
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface GetEventDetails {
    }
}


