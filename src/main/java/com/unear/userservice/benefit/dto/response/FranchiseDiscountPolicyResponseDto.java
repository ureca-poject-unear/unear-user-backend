package com.unear.userservice.benefit.dto.response;

import com.unear.userservice.place.entity.Franchise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseDiscountPolicyResponseDto {

    private String franchiseName;
    private String franchiseImageUrl;
    private boolean hasVvipPolicy;
    private boolean hasVipPolicy;
    private boolean hasBasicPolicy;
    private String categoryCode;

    public static FranchiseDiscountPolicyResponseDto from(Franchise franchise) {
        return new FranchiseDiscountPolicyResponseDto(
                franchise.getFranchiseName(),
                franchise.getImageUrl(),
                franchise.getVvipPolicy() != null && !franchise.getVvipPolicy().isBlank(),
                franchise.getVipPolicy() != null && !franchise.getVipPolicy().isBlank(),
                franchise.getBasicPolicy() != null && !franchise.getBasicPolicy().isBlank(),
                franchise.getCategoryCode()
        );
    }
}


