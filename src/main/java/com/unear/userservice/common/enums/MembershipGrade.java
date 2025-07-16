package com.unear.userservice.common.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MembershipGrade {

    BASIC("BASIC", "우수"),
    VIP("VIP", "VIP"),
    VVIP("VVIP", "VVIP"),
    ALL("ALL", "모든등급");

    private final String code;
    private final String label;

    MembershipGrade(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static MembershipGrade fromCode(String code) {
        return Arrays.stream(values())
                .filter(g -> g.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid membership code: " + code));
    }
}
