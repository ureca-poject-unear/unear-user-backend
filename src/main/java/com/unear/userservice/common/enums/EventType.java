package com.unear.userservice.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {
    NONE("이벤트 아님"),
    GENERAL("이벤트(일반)"),
    REQUIRE("이벤트(필수)");

    private final String label;
}