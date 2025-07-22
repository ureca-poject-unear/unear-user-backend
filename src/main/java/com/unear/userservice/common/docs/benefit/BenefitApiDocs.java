package com.unear.userservice.common.docs.benefit;


import com.unear.userservice.place.dto.response.PlaceRenderResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

public class BenefitApiDocs {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "일반 혜택 상세 조회",
            description = "일반 장소에 대한 할인 혜택 상세 정보를 조회합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "일반 혜택 상세 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlaceRenderResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "DefaultFilter",
                                    summary = "할인 정책 상세 조회",
                                    value = """
                                            {
                                                "resultCode": 200,
                                                "codeName": "SUCCESS",
                                                "message": "혜택 상세 조회 성공",
                                                "data": {
                                                    "placeId": 147,
                                                    "placeName": "클럽디 오아시스",
                                                    "address": "부산 해운대구 달맞이길 30 엘시티",
                                                    "startTime": 9,
                                                    "endTime": 20,
                                                    "unitBaseAmount": null,
                                                    "fixedDiscount": null,
                                                    "discountPercent": 10,
                                                    "minPurchaseAmount": null,
                                                    "maxDiscountAmount": 10000,
                                                    "discountCode": "COUPON_PERCENT",
                                                    "membershipCode": "ALL",
                                                    "markerCode": "BASIC"
                                                }
                                            }
                """
                            ) ,
                            @ExampleObject(
                                    name = "NotFoundFilter",
                                    summary = "존재하지 않는 id",
                                    value = """
                                            {
                                                "resultCode": 404,
                                                "codeName": "BENEFIT_NOT_FOUND",
                                                "message": "혜택 정보를 찾을 수 없습니다.",
                                                "data": null
                                            }
                """
                            )
                    }
            )
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface GetGeneralDiscountPolicyDetail {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "프랜차이즈 혜택 목록 조회",
            description = "프랜차이즈에 적용되는 혜택 정책들을 페이징하여 조회합니다. hasVvip/hasVip/hasBasic 필드는 대응되는 멤버십 정책이 있으면 true, 없으면 false를 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "프랜차이즈 혜택 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlaceRenderResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "DefaultFilter",
                                    summary = "상세 할인 혜택 정보 조회 성공",
                                    value = """
                                            {
                                                "resultCode": 200,
                                                "codeName": "SUCCESS",
                                                "message": "프랜차이즈 혜택 리스트 조회 성공",
                                                "data": {
                                                    "content": [
                                                        {
                                                            "franchiseName": "CGV",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "CLUTURE",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        },
                                                        {
                                                            "franchiseName": "파리바게뜨",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "BAKERY",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        },
                                                        {
                                                            "franchiseName": "맥도날드",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "FOOD",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        },
                                                        {
                                                            "franchiseName": "GS 25",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "LIFE",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        },
                                                        {
                                                            "franchiseName": "CU",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "LIFE",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        },
                                                        {
                                                            "franchiseName": "GS THE FRESH",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "LIFE",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        },
                                                        {
                                                            "franchiseName": "유앤아이피부과의원",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "BEAUTY",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        },
                                                        {
                                                            "franchiseName": "파리크라상",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "BAKERY",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        },
                                                        {
                                                            "franchiseName": "도미노피자",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "FOOD",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        },
                                                        {
                                                            "franchiseName": "미스터피자",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "FOOD",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        }
                                                    ],
                                                    "pageable": {
                                                        "pageNumber": 0,
                                                        "pageSize": 10,
                                                        "sort": {
                                                            "empty": false,
                                                            "sorted": true,
                                                            "unsorted": false
                                                        },
                                                        "offset": 0,
                                                        "paged": true,
                                                        "unpaged": false
                                                    },
                                                    "last": false,
                                                    "totalPages": 3,
                                                    "totalElements": 25,
                                                    "first": true,
                                                    "size": 10,
                                                    "number": 0,
                                                    "sort": {
                                                        "empty": false,
                                                        "sorted": true,
                                                        "unsorted": false
                                                    },
                                                    "numberOfElements": 10,
                                                    "empty": false
                                                }
                                            }
                """
                            ),
                            @ExampleObject(
                                    name = "categoryCodeFilter",
                                    summary = "장소 카테고리 코드로 조회 성공",
                                    value = """
                                            {
                                                "resultCode": 200,
                                                "codeName": "SUCCESS",
                                                "message": "프랜차이즈 혜택 리스트 조회 성공",
                                                "data": {
                                                    "content": [
                                                        {
                                                            "franchiseName": "파리바게뜨",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "BAKERY",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        },
                                                        {
                                                            "franchiseName": "파리크라상",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "BAKERY",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        },
                                                        {
                                                            "franchiseName": "브레댄코",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "BAKERY",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        },
                                                        {
                                                            "franchiseName": "뚜레쥬르",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "BAKERY",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        }
                                                    ],
                                                    "pageable": {
                                                        "pageNumber": 0,
                                                        "pageSize": 10,
                                                        "sort": {
                                                            "empty": false,
                                                            "sorted": true,
                                                            "unsorted": false
                                                        },
                                                        "offset": 0,
                                                        "paged": true,
                                                        "unpaged": false
                                                    },
                                                    "last": true,
                                                    "totalPages": 1,
                                                    "totalElements": 4,
                                                    "first": true,
                                                    "size": 10,
                                                    "number": 0,
                                                    "sort": {
                                                        "empty": false,
                                                        "sorted": true,
                                                        "unsorted": false
                                                    },
                                                    "numberOfElements": 4,
                                                    "empty": false
                                                }
                                            }
                """
                            ),
                            @ExampleObject(
                                    name = "nameFilter",
                                    summary = "프랜차이즈 이름으로 조회 성공",
                                    value = """
                                            {
                                                "resultCode": 200,
                                                "codeName": "SUCCESS",
                                                "message": "프랜차이즈 혜택 리스트 조회 성공",
                                                "data": {
                                                    "content": [
                                                        {
                                                            "franchiseName": "GS 25",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "LIFE",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        },
                                                        {
                                                            "franchiseName": "GS THE FRESH",
                                                            "franchiseImageUrl": "",
                                                            "categoryCode": "LIFE",
                                                            "hasVvip": true,
                                                            "hasVip": true,
                                                            "hasBasic": true
                                                        }
                                                    ],
                                                    "pageable": {
                                                        "pageNumber": 0,
                                                        "pageSize": 10,
                                                        "sort": {
                                                            "empty": false,
                                                            "sorted": true,
                                                            "unsorted": false
                                                        },
                                                        "offset": 0,
                                                        "paged": true,
                                                        "unpaged": false
                                                    },
                                                    "last": true,
                                                    "totalPages": 1,
                                                    "totalElements": 2,
                                                    "first": true,
                                                    "size": 10,
                                                    "number": 0,
                                                    "sort": {
                                                        "empty": false,
                                                        "sorted": true,
                                                        "unsorted": false
                                                    },
                                                    "numberOfElements": 2,
                                                    "empty": false
                                                }
                                            }
                """
                            )
                    }
            )
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface GetFranchiseDiscountPolicyList {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "프랜차이즈 혜택 상세 조회",
            description = "프랜차이즈의 상세 할인 혜택 정보를 조회합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "프랜차이즈 혜택 상세 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlaceRenderResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "DefaultFilter",
                                    summary = "프랜차이즈 할인 정책 상세 조회",
                                    value = """
                                            {
                                                "resultCode": 200,
                                                "codeName": "SUCCESS",
                                                "message": "프랜차이즈 혜택 상세 조회 성공",
                                                "data": {
                                                    "franchiseId": 1,
                                                    "franchiseName": "CGV",
                                                    "imageUrl": "",
                                                    "categoryCode": "CLUTURE",
                                                    "membershipPolicies": [
                                                        {
                                                            "membershipCode": "VIP",
                                                            "discountCode": "COUPON_FIXED",
                                                            "unitBaseAmount": null,
                                                            "fixedDiscount": 2000,
                                                            "discountPercent": null,
                                                            "minPurchaseAmount": null,
                                                            "maxDiscountAmount": null
                                                        },
                                                        {
                                                            "membershipCode": "VVIP",
                                                            "discountCode": "COUPON_FIXED",
                                                            "unitBaseAmount": null,
                                                            "fixedDiscount": 2000,
                                                            "discountPercent": null,
                                                            "minPurchaseAmount": null,
                                                            "maxDiscountAmount": null
                                                        },
                                                        {
                                                            "membershipCode": "BASIC",
                                                            "discountCode": "COUPON_FIXED",
                                                            "unitBaseAmount": null,
                                                            "fixedDiscount": 2000,
                                                            "discountPercent": null,
                                                            "minPurchaseAmount": 10000,
                                                            "maxDiscountAmount": null
                                                        }
                                                    ]
                                                }
                                            }

                """
                            ) ,
                            @ExampleObject(
                                    name = "NotFoundFilter",
                                    summary = "존재하지 않는 id",
                                    value = """
                                    {
                                        "resultCode": 404,
                                        "codeName": "BENEFIT_NOT_FOUND",
                                        "message": "혜택 정보를 찾을 수 없습니다.",
                                        "data": null
                                    }
                """
                            )
                    }
            )
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface GetFranchiseDiscountPolicyDetail {
    }
}
