package com.unear.userservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PlaceType {

    LOCAL("LOCAL", "우리동네멤버십"),
    BASIC("BASIC", "기본혜택"),
    FRANCHISE("FRANCHISE", "프랜차이즈"),
    POPUP("POPUP", "팝업스토어");

    private final String code;
    private final String label;

    PlaceType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static PlaceType fromCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid place type code: " + code));
    }
}

