package com.unear.userservice.benefit.service;

import com.unear.userservice.benefit.dto.request.DiscountPolicyDetailRequestDto;
import com.unear.userservice.benefit.dto.request.FranchiseDiscountPolicyRequestDto;
import com.unear.userservice.benefit.dto.response.DiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.dto.response.FranchiseDiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.dto.response.FranchiseDiscountPolicyResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DiscountPolicyService {

    /**
 * 지정된 할인 정책 상세 ID에 해당하는 할인 정책 상세 정보를 조회합니다.
 *
 * @param discountPolicyDetailId 조회할 할인 정책 상세의 ID
 * @return 할인 정책 상세 정보 DTO
 */
DiscountPolicyDetailResponseDto getDiscountPolicyDetail(Long discountPolicyDetailId);
    /**
 * 프랜차이즈 할인 정책 목록을 페이지 단위로 조회합니다.
 *
 * @param requestDto 프랜차이즈 할인 정책 조회 조건이 포함된 요청 객체
 * @return 조회된 프랜차이즈 할인 정책의 페이지 결과
 */
Page<FranchiseDiscountPolicyResponseDto> getFranchiseDiscountPolicies(FranchiseDiscountPolicyRequestDto requestDto);
    /**
 * 지정된 프랜차이즈 ID에 대한 할인 정책 상세 정보를 반환합니다.
 *
 * @param franchiseId 할인 정책 상세 정보를 조회할 프랜차이즈의 ID
 * @return 프랜차이즈의 할인 정책 상세 정보
 */
FranchiseDiscountPolicyDetailResponseDto getFranchiseDiscountPolicyDetail(Long franchiseId);

}
