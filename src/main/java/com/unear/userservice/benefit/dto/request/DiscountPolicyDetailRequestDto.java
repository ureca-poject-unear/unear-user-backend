package com.unear.userservice.benefit.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountPolicyDetailRequestDto {

    private int page = 0;
    private int size = 10;

    private Long placeId;
    private String membershipCode;
    private String markerCode;
    private String categoryCode;

}
