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

    /**
     * 주어진 Franchise 엔티티로부터 할인 정책 상세 응답 DTO를 생성합니다.
     *
     * @param franchise 할인 정책 정보를 포함한 프랜차이즈 엔티티
     * @return 프랜차이즈의 할인 정책 상세 정보를 담은 DTO 인스턴스
     */
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

