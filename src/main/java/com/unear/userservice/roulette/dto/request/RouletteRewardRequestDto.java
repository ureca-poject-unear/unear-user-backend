package com.unear.userservice.roulette.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RouletteRewardRequestDto {

    @NotBlank
    private String reward;
}
