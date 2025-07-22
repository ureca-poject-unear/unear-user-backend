package com.unear.userservice.benefit.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseDiscountPolicyRequestDto {
    private int page = 0;
    private int size = 10;

    @Schema(description = "카테고리 코드", example = "CAFE")
    private String categoryCode;

    @Schema(description = "프랜차이즈 이름", example = "GS")
    private String franchiseName;
}
