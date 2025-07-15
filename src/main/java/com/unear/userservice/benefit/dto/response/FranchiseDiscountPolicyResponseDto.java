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

    /**
     * 주어진 Franchise 엔티티로부터 할인 정책 정보를 추출하여 FranchiseDiscountPolicyResponseDto 인스턴스를 생성합니다.
     *
     * @param franchise 할인 정책 정보를 포함하는 Franchise 엔티티
     * @return 추출된 정보로 구성된 FranchiseDiscountPolicyResponseDto 객체
     */
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


