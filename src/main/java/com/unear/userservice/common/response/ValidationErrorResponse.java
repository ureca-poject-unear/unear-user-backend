package com.unear.userservice.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ValidationErrorResponse {
    private Map<String, String> fieldErrors;

    public static ValidationErrorResponse of(Map<String, String> fieldErrors) {
        return new ValidationErrorResponse(fieldErrors);
    }
}
