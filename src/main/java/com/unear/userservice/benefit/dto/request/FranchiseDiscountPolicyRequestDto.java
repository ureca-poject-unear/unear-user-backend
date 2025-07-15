package com.unear.userservice.benefit.dto.request;


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
    private String categoryCode;
}
