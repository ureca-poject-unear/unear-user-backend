package com.unear.userservice.place.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NearbyPlaceRequestDto {

    @NotNull(message = "위도는 필수입니다.")
    @DecimalMin(value = "-90.0", inclusive = true, message = "위도는 -90 이상이어야 합니다.")
    @DecimalMax(value = "90.0", inclusive = true, message = "위도는 90 이하이어야 합니다.")
    @Schema(description = "사용자 현재 위치의 위도", example = "37.5665")
    private Double latitude;

    @NotNull(message = "경도는 필수입니다.")
    @DecimalMin(value = "-180.0", inclusive = true, message = "경도는 -180 이상이어야 합니다.")
    @DecimalMax(value = "180.0", inclusive = true, message = "경도는 180 이하이어야 합니다.")
    @Schema(description = "사용자 현재 위치의 경도", example = "126.9780")
    private Double longitude;
}

