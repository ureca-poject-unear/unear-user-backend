package com.unear.userservice.place.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceRequestDto {

    @Schema(description = "검색어", example = "GS")
    private String keyword;

    @Schema(description = "카테고리 코드", example = "FOOD")
    private String categoryCode;

    @Schema(description = "혜택 카테고리", example = "할인")
    private String benefitCategory;

    @Schema(description = "즐겨찾기 여부", example = "true")
    private Boolean isFavorite;

    @NotNull
    @Schema(description = "좌측하단 위도", example = "37.5458745")
    private Double southWestLatitude;

    @NotNull
    @Schema(description = "좌측하단 경도", example = "126.8105856")
    private Double southWestLongitude;

    @NotNull
    @Schema(description = "우측상단 위도", example = "37.5636053")
    private Double northEastLatitude;

    @NotNull
    @Schema(description = "우측상단 경도", example = "126.8357654")
    private Double northEastLongitude;

}
