package com.unear.userservice.benefit.dto.response;

import com.unear.userservice.benefit.entity.FranchiseDiscountPolicy;
import com.unear.userservice.common.enums.MembershipGrade;
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
    private String categoryCode;

    private boolean hasVvip;
    private boolean hasVip;
    private boolean hasBasic;

    public static FranchiseDiscountPolicyResponseDto from(Franchise franchise) {
        List<MembershipGrade> membershipGrades = franchise.getFranchiseDiscountPolicies().stream()
                .map(FranchiseDiscountPolicy::getMembershipCode)
                .map(MembershipGrade::fromCode)
                .toList();

        return new FranchiseDiscountPolicyResponseDto(
                franchise.getName(),
                franchise.getImageUrl(),
                franchise.getCategoryCode(),
                membershipGrades.contains(MembershipGrade.VVIP),
                membershipGrades.contains(MembershipGrade.VIP),
                membershipGrades.contains(MembershipGrade.BASIC)
        );
    }
}



