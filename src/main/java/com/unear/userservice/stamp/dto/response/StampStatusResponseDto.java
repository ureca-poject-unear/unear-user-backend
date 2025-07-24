package com.unear.userservice.stamp.dto.response;

import com.unear.userservice.stamp.dto.response.StampResponseDto;
import com.unear.userservice.stamp.entity.Stamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StampStatusResponseDto {
    private List<StampResponseDto> stamps;
    private boolean rouletteAvailable;

    public static StampStatusResponseDto of(List<Stamp> stampList, boolean rouletteAvailable) {
        List<StampResponseDto> dtoList = stampList.stream()
                .map(StampResponseDto::from)
                .toList();

        return new StampStatusResponseDto(dtoList, rouletteAvailable);
    }
}
