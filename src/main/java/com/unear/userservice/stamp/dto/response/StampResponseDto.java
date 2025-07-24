package com.unear.userservice.stamp.dto.response;

import com.unear.userservice.common.enums.EventType;
import com.unear.userservice.stamp.entity.Stamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StampResponseDto {
    private Long stampId;
    private String placeName;
    private EventType eventCode;
    private LocalDateTime stampedAt;

    public static StampResponseDto from(Stamp stamp) {
        return new StampResponseDto(
                stamp.getStampId(),
                stamp.getPlaceName(),
                stamp.getEventPlace().getEventCode(),
                stamp.getStampedAt()
        );
    }
}
