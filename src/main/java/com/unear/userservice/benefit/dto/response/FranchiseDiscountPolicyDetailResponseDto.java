package com.unear.userservice.benefit.dto.response;

import com.unear.userservice.benefit.entity.FranchiseDiscountPolicy;
import com.unear.userservice.place.entity.Franchise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseDiscountPolicyDetailResponseDto {

    private Long franchiseId;
    private String franchiseName;
    private String imageUrl;
    private String categoryCode;

    private List<MembershipGradePolicyResponseDto> membershipPolicies;

    public static FranchiseDiscountPolicyDetailResponseDto from(Franchise franchise) {
        List<MembershipGradePolicyResponseDto> policies = franchise.getFranchiseDiscountPolicies().stream()
                .map(MembershipGradePolicyResponseDto::from)
                .toList();

        return new FranchiseDiscountPolicyDetailResponseDto(
                franchise.getFranchiseId(),
                franchise.getName(),
                franchise.getImageUrl(),
                franchise.getCategoryCode(),
                policies
        );
    }


}



