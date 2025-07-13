package com.unear.userservice.benefit.service;

import com.unear.userservice.benefit.dto.request.DiscountPolicyDetailRequestDto;
import com.unear.userservice.benefit.dto.response.DiscountPolicyDetailResponseDto;
import org.springframework.data.domain.Page;

public interface DiscountPolicyService {
    Page<DiscountPolicyDetailResponseDto> getAllBenefits( DiscountPolicyDetailRequestDto request);
    DiscountPolicyDetailResponseDto getBenefitDetail(Long discountPolicyDetailId);
}
