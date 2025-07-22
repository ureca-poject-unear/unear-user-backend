package com.unear.userservice.common.docs.coupon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;


public class CouponApiDocs {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "장소 + 마커 코드로 쿠폰 템플릿 조회",
            description = "특정 장소 ID와 마커 코드(markerCode)에 해당하는 사용자의 쿠폰 템플릿 리스트를 조회합니다. markerCode 종류는 BASIC 과 FRANCHISE 입니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "장소 Id & 장소 유형 코드로 쿠폰 템플릿 조회",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "BasicCoupon",
                                    summary = "기본혜택 장소 쿠폰템플릿 조회 (markerCode == BASIC ) ",
                                    value = """
                                    {
                                        "resultCode": 200,
                                        "codeName": "SUCCESS",
                                        "message": "쿠폰 템플릿 조회 성공",
                                        "data": [
                                            {
                                                "couponTemplateId": 2,
                                                "couponName": "서울랜드",
                                                "discountCode": "COUPON_PERCENT",
                                                "membershipCode": "ALL",
                                                "discountInfo": "(쿠폰) 퍼센트 할인",
                                                "couponStart": "2025-07-01T00:00:00",
                                                "couponEnd": "2025-07-30T00:00:00",
                                                "downloaded": true
                                            }
                                        ]
                                    }
                """
                            ),
                            @ExampleObject(
                                    name = "FranchiseCoupon",
                                    summary = "프랜차이즈 장소 쿠폰템플릿 조회 (markerCode == FRANCHISE )",
                                    value = """
                                    {
                                        "resultCode": 200,
                                        "codeName": "SUCCESS",
                                        "message": "쿠폰 템플릿 조회 성공",
                                        "data": [
                                            {
                                                "couponTemplateId": 4,
                                                "couponName": "GS THE FRESH",
                                                "discountCode": "COUPON_FIXED",
                                                "membershipCode": "VVIP",
                                                "discountInfo": "(쿠폰) 금액 할인",
                                                "couponStart": "2025-07-01",
                                                "couponEnd": "2025-07-30",
                                                "downloaded": false
                                            },
                                            {
                                                "couponTemplateId": 5,
                                                "couponName": "GS THE FRESH",
                                                "discountCode": "COUPON_FIXED",
                                                "membershipCode": "VIP",
                                                "discountInfo": "(쿠폰) 금액 할인",
                                                "couponStart": "2025-07-01",
                                                "couponEnd": "2025-07-30",
                                                "downloaded": false
                                            },
                                            {
                                                "couponTemplateId": 6,
                                                "couponName": "GS THE FRESH",
                                                "discountCode": "COUPON_FIXED",
                                                "membershipCode": "BASIC",
                                                "discountInfo": "(쿠폰) 금액 할인",
                                                "couponStart": "2025-07-01",
                                                "couponEnd": "2025-07-30",
                                                "downloaded": false
                                            }
                                        ]
                                    }
                """
                            ),
                            @ExampleObject(
                                    name = "InvalidMarkerCode",
                                    summary = "유효하지 않은 markerCode",
                                    value = """
                                {
                                    "resultCode": 404,
                                    "codeName": "INVALID_CODE",
                                    "message": "유효하지 않은 공통코드입니다.",
                                    "data": null
                                }
                """
                            )
                    }
            )
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface GetCouponsByPlace {}

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "쿠폰 다운로드",
            description = "지정된 쿠폰 템플릿 ID를 기반으로 쿠폰을 다운로드합니다. 다운받은 쿠폰은 UNUSED인 상태입니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "쿠폰 다운로드 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "DownloadCoupon",
                                    summary = "쿠폰 다운로드 성공",
                                    value = """
                                    {
                                        "resultCode": 200,
                                        "codeName": "SUCCESS",
                                        "message": "쿠폰 다운로드 성공",
                                        "data": {
                                            "userCouponId": 1,
                                            "couponName": "서울랜드",
                                            "barcodeNumber": "eca2bdf31ecb4164",
                                            "couponStatusCode": "UNUSED",
                                            "createdAt": "2025-07-17"
                                        }
                                    }
                """
                            ),
                            @ExampleObject(
                                    name = "AlreadyDownloadCoupon",
                                    summary = "이미 다운로드한 쿠폰",
                                    value = """
                                    {
                                        "resultCode": 400,
                                        "codeName": "COUPON_ALREADY_DOWNLOADED",
                                        "message": "이미 다운로드한 쿠폰입니다.",
                                        "data": null
                                    }
                """
                            ),
                            @ExampleObject(
                                    name = "ExpiredCoupon",
                                    summary = "유효기간 외에 쿠폰 다운로드",
                                    value = """
                                    {
                                        "resultCode": 400,
                                        "codeName": "COUPON_EXPIRED",
                                        "message": "유효 기간이 지난 쿠폰입니다.",
                                        "data": null
                                    }
                """
                            ),
                            @ExampleObject(
                                    name = "NotFoundCoupon",
                                    summary = "존재하지 않는 쿠폰템플릿 id",
                                    value = """
                                    {
                                        "resultCode": 404,
                                        "codeName": "COUPON_TEMPLATE_NOT_FOUND",
                                        "message": "쿠폰 템플릿을 찾을 수 없습니다.",
                                        "data": null
                                    }
                """
                            )
                    }
            )
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface DownloadCoupon {}

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "선착순 쿠폰 다운로드",
            description = "재고가 제한된 쿠폰을 선착순으로 다운로드합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "선착순 쿠폰 다운로드 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "DownloadCoupon",
                                    summary = "선착순 쿠폰 다운로드 성공",
                                    value = """
                                    {
                                        "resultCode": 200,
                                        "codeName": "SUCCESS",
                                        "message": "쿠폰 다운로드 성공",
                                        "data": {
                                            "userCouponId": 2,
                                            "couponName": "진격의거인 피규어",
                                            "barcodeNumber": "f8bf61ebf1244dba",
                                            "couponStatusCode": "UNUSED",
                                            "createdAt": "2025-07-17"
                                        }
                                    }
                """
                            ),
                            @ExampleObject(
                                    name = "AlreadyDownloadCoupon",
                                    summary = "이미 다운로드한 선착순 쿠폰",
                                    value = """
                                    {
                                        "resultCode": 400,
                                        "codeName": "COUPON_ALREADY_DOWNLOADED",
                                        "message": "이미 다운로드한 쿠폰입니다.",
                                        "data": null
                                    }
                """
                            ),
                            @ExampleObject(
                                    name = "NotFoundCoupon",
                                    summary = "존재하지 않는 쿠폰템플릿 id",
                                    value = """
                                    {
                                        "resultCode": 404,
                                        "codeName": "COUPON_TEMPLATE_NOT_FOUND",
                                        "message": "쿠폰 템플릿을 찾을 수 없습니다.",
                                        "data": null
                                    }
                """
                            )
                    }
            )
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface DownloadFCFSCoupon {}

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "사용자 보유 쿠폰 목록 조회",
            description = "현재 로그인한 사용자의 다운받은 쿠폰 목록을 조회합니다. count는 쿠폰 수를 의미하고, 곧 만료예정인 쿠폰은 couponEnd와 현재 날짜를 비교하여 결정하면됩니다. (2~3일)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "쿠폰 템플릿 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Success",
                                    summary = "다운받은 쿠폰 리스트 조회 성공",
                                    value = """
                                    {
                                        "resultCode": 200,
                                        "codeName": "SUCCESS",
                                        "message": "사용자 쿠폰 목록 조회 성공",
                                        "data": {
                                            "count": 3,
                                            "coupons": [
                                                {
                                                    "userCouponId": 1,
                                                    "couponName": "서울랜드",
                                                    "barcodeNumber": "eca2bdf31ecb4164",
                                                    "couponStatusCode": "UNUSED",
                                                    "createdAt": "2025-07-17T00:00:00",
                                                    "couponEnd": "2025-07-30T00:00:00"
                                                },
                                                {
                                                    "userCouponId": 2,
                                                    "couponName": "진격의거인 피규어",
                                                    "barcodeNumber": "f8bf61ebf1244dba",
                                                    "couponStatusCode": "UNUSED",
                                                    "createdAt": "2025-07-17T00:00:00",
                                                    "couponEnd": "2025-07-30T00:00:00"
                                                },
                                                {
                                                    "userCouponId": 7,
                                                    "couponName": "GS THE FRESH",
                                                    "barcodeNumber": "63d6d88f11b54a44",
                                                    "couponStatusCode": "UNUSED",
                                                    "createdAt": "2025-07-17T00:00:00",
                                                    "couponEnd": "2025-07-30T00:00:00"
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
    public @interface GetMyCoupons {}

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Operation(
            summary = "사용자 쿠폰 상세 조회",
            description = "보유 중인 특정 쿠폰의 상세 정보를 조회합니다. discountCode에 따라 퍼센트 할인인지 , 금액 할인인지 종류가 나뉩니다.\n" +
                    "각 쿠폰 종류에 따라 사용하는 필드만 null이 아닌채로 반환됩니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "다운받은 쿠폰 상세 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "CouponPercent",
                                    summary = "퍼센트 할인 쿠폰 조회 성공 ( 40 % 할인 쿠폰, 최대 할인 금액 : 50000 )",
                                    value = """
                                    {
                                        "resultCode": 200,
                                        "codeName": "SUCCESS",
                                        "message": "사용자 쿠폰 상세 조회 성공",
                                        "data": {
                                            "userCouponId": 1,
                                            "couponName": "서울랜드",
                                            "barcodeNumber": "eca2bdf31ecb4164",
                                            "couponStatusCode": "UNUSED",
                                            "createdAt": "2025-07-17T00:00:00",
                                            "couponEnd": "2025-07-30T00:00:00",
                                            "discountCode": "COUPON_PERCENT",
                                            "membershipCode": "ALL",
                                            "unitBaseAmount": null,
                                            "fixedDiscount": null,
                                            "discountPercent": 40,
                                            "minPurchaseAmount": null,
                                            "maxDiscountAmount": 50000,
                                            "markerCode": "BASIC"
                                        }
                                    }
                """
                            ),
                            @ExampleObject(
                                    name = "CouponFixed",
                                    summary = "금액 할인 쿠폰 조회 성공 ( 3000원 할인 쿠폰, 최소 사용 금액 20000원 )",
                                    value = """
                                    {
                                        "resultCode": 200,
                                        "codeName": "SUCCESS",
                                        "message": "사용자 쿠폰 상세 조회 성공",
                                        "data": {
                                            "userCouponId": 7,
                                            "couponName": "GS THE FRESH",
                                            "barcodeNumber": "63d6d88f11b54a44",
                                            "couponStatusCode": "UNUSED",
                                            "createdAt": "2025-07-17T00:00:00",
                                            "couponEnd": "2025-07-30T00:00:00",
                                            "discountCode": "COUPON_FIXED",
                                            "membershipCode": "VIP",
                                            "unitBaseAmount": null,
                                            "fixedDiscount": 3000,
                                            "discountPercent": null,
                                            "minPurchaseAmount": 20000,
                                            "maxDiscountAmount": null,
                                            "markerCode": "FRANCHISE"
                                        }
                                    }
                """
                            ),
                            @ExampleObject(
                                    name = "CouponFcfs",
                                    summary = "선착순 쿠폰 조회 성공 ( 선착순 쿠폰 discountCode는 없음 null )",
                                    value = """
                                    {
                                        "resultCode": 200,
                                        "codeName": "SUCCESS",
                                        "message": "사용자 쿠폰 상세 조회 성공",
                                        "data": {
                                            "userCouponId": 2,
                                            "couponName": "진격의거인 피규어",
                                            "barcodeNumber": "f8bf61ebf1244dba",
                                            "couponStatusCode": "UNUSED",
                                            "createdAt": "2025-07-17T00:00:00",
                                            "couponEnd": "2025-07-30T00:00:00",
                                            "discountCode": null,
                                            "membershipCode": null,
                                            "unitBaseAmount": null,
                                            "fixedDiscount": null,
                                            "discountPercent": null,
                                            "minPurchaseAmount": null,
                                            "maxDiscountAmount": null,
                                            "markerCode": ""
                                        }
                                    }
                """
                            ),
                            @ExampleObject(
                                    name = "NotFoundCoupon",
                                    summary = "존재하지 않는 user_coupon_id",
                                    value = """
                                    {
                                        "resultCode": 404,
                                        "codeName": "COUPON_TEMPLATE_NOT_FOUND",
                                        "message": "쿠폰 템플릿을 찾을 수 없습니다.",
                                        "data": null
                                    }
                """
                            )
                    }
            )
    )
    @SecurityRequirement(name = "BearerAuth")
    public @interface GetMyCouponDetail {}


}

