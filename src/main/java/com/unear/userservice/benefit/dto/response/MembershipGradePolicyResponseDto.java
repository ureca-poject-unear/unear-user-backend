package com.unear.userservice.benefit.dto.response;

import com.unear.userservice.benefit.entity.FranchiseDiscountPolicy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MembershipGradePolicyResponseDto {

    private String membershipCode;
    private String discountCode;
    private Integer unitBaseAmount;
    private Integer fixedDiscount;
    private Integer discountPercent;
    private Integer minPurchaseAmount;
    private Integer maxDiscountAmount;

    public static MembershipGradePolicyResponseDto from(FranchiseDiscountPolicy policy) {
        return new MembershipGradePolicyResponseDto(
                policy.getMembershipCode(),
                policy.getDiscountCode(),
                policy.getUnitBaseAmount(),
                policy.getFixedDiscount(),
                policy.getDiscountPercent(),
                policy.getMinPurchaseAmount(),
                policy.getMaxDiscountAmount()
        );
    }
}
