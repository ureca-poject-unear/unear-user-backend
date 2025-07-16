package com.unear.userservice.benefit.service;

import com.unear.userservice.benefit.dto.request.FranchiseDiscountPolicyRequestDto;
import com.unear.userservice.benefit.dto.response.GeneralDiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.dto.response.FranchiseDiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.dto.response.FranchiseDiscountPolicyResponseDto;
import org.springframework.data.domain.Page;

public interface DiscountPolicyService {

    GeneralDiscountPolicyDetailResponseDto getDiscountPolicyDetail(Long discountPolicyDetailId);
    Page<FranchiseDiscountPolicyResponseDto> getFranchiseDiscountPolicies(FranchiseDiscountPolicyRequestDto requestDto);
    FranchiseDiscountPolicyDetailResponseDto getFranchiseDiscountPolicyDetail(Long franchiseId);

}
