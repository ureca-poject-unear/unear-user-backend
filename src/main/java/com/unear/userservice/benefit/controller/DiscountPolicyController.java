package com.unear.userservice.benefit.controller;


import com.unear.userservice.benefit.dto.request.FranchiseDiscountPolicyRequestDto;
import com.unear.userservice.benefit.dto.response.GeneralDiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.dto.response.FranchiseDiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.dto.response.FranchiseDiscountPolicyResponseDto;
import com.unear.userservice.benefit.service.DiscountPolicyService;
import com.unear.userservice.common.docs.benefit.BenefitApiDocs;
import com.unear.userservice.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benefits")
@RequiredArgsConstructor
public class DiscountPolicyController {

    private final DiscountPolicyService discountPolicyService;

    @BenefitApiDocs.GetGeneralDiscountPolicyDetail
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GeneralDiscountPolicyDetailResponseDto>> getDiscountPolicyDetail(
            @PathVariable("id") Long discountPolicyDetailId
    ) {
        GeneralDiscountPolicyDetailResponseDto response = discountPolicyService.getDiscountPolicyDetail(discountPolicyDetailId);
        return ResponseEntity.ok(ApiResponse.success("혜택 상세 조회 성공", response));
    }

    @BenefitApiDocs.GetFranchiseDiscountPolicyList
    @GetMapping("/franchise")
    public ResponseEntity<ApiResponse<Page<FranchiseDiscountPolicyResponseDto>>> getFranchiseDiscountPolicies(
            @ModelAttribute FranchiseDiscountPolicyRequestDto requestDto
    ) {
        Page<FranchiseDiscountPolicyResponseDto> response = discountPolicyService.getFranchiseDiscountPolicies(requestDto);
        return ResponseEntity.ok(ApiResponse.success("프랜차이즈 혜택 리스트 조회 성공", response));
    }

    @BenefitApiDocs.GetFranchiseDiscountPolicyDetail
    @GetMapping("/franchise/{id}")
    public ResponseEntity<ApiResponse<FranchiseDiscountPolicyDetailResponseDto>> getFranchiseDiscountPolicyDetail(
            @PathVariable("id") Long franchiseId
    ) {
        FranchiseDiscountPolicyDetailResponseDto response = discountPolicyService.getFranchiseDiscountPolicyDetail(franchiseId);
        return ResponseEntity.ok(ApiResponse.success("프랜차이즈 혜택 상세 조회 성공", response));
    }


}

