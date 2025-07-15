package com.unear.userservice.benefit.dto.response;

import com.unear.userservice.place.entity.Franchise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseDiscountPolicyDetailResponseDto {

    private Long franchiseId;
    private String franchiseName;
    private String imageUrl;

    private String vvipPolicy;
    private String vipPolicy;
    private String basicPolicy;

    private String categoryCode;

    public static FranchiseDiscountPolicyDetailResponseDto from(Franchise franchise) {
        return new FranchiseDiscountPolicyDetailResponseDto(
                franchise.getFranchiseId(),
                franchise.getFranchiseName(),
                franchise.getImageUrl(),
                franchise.getVvipPolicy(),
                franchise.getVipPolicy(),
                franchise.getBasicPolicy(),
                franchise.getCategoryCode()
        );
    }
}

