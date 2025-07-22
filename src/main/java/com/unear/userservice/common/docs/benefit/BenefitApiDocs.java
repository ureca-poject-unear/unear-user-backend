package com.unear.userservice.common.docs.benefit;


import com.unear.userservice.place.dto.response.PlaceRenderResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

public class BenefitApiDocs {

    @Tag(name = "혜택 API", description = "일반 혜택 및 프랜차이즈 혜택 관련 API")
    public @interface BenefitApiTag {
    }

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
    public @interface GetGeneralDiscountPolicyDetail {
    }

    @Operation(
            summary = "프랜차이즈 혜택 목록 조회",
            description = "프랜차이즈에 적용되는 혜택 정책들을 페이징하여 조회합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlaceRenderResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "DefaultFilter",
                                    summary = "  ",
                                    value = """
                
                """
                            )
                    }
            )
    )
    public @interface GetFranchiseDiscountPolicyList {
    }

    @Operation(
            summary = "프랜차이즈 혜택 상세 조회",
            description = "프랜차이즈의 상세 할인 혜택 정보를 조회합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlaceRenderResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "DefaultFilter",
                                    summary = "  ",
                                    value = """
                
                """
                            )
                    }
            )
    )
    public @interface GetFranchiseDiscountPolicyDetail {
    }
}
