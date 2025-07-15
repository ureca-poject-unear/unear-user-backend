package com.unear.userservice.benefit.service;

import com.unear.userservice.benefit.dto.request.DiscountPolicyDetailRequestDto;
import com.unear.userservice.benefit.dto.request.FranchiseDiscountPolicyRequestDto;
import com.unear.userservice.benefit.dto.response.DiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.dto.response.FranchiseDiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.dto.response.FranchiseDiscountPolicyResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DiscountPolicyService {

    DiscountPolicyDetailResponseDto getDiscountPolicyDetail(Long discountPolicyDetailId);
    Page<FranchiseDiscountPolicyResponseDto> getFranchiseDiscountPolicies(FranchiseDiscountPolicyRequestDto requestDto);
    FranchiseDiscountPolicyDetailResponseDto getFranchiseDiscountPolicyDetail(Long franchiseId);

}
