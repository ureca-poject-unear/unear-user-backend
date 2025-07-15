package com.unear.userservice.benefit.controller;


import com.unear.userservice.benefit.dto.request.DiscountPolicyDetailRequestDto;
import com.unear.userservice.benefit.dto.request.FranchiseDiscountPolicyRequestDto;
import com.unear.userservice.benefit.dto.response.DiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.dto.response.FranchiseDiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.dto.response.FranchiseDiscountPolicyResponseDto;
import com.unear.userservice.benefit.service.DiscountPolicyService;
import com.unear.userservice.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/benefits")
@RequiredArgsConstructor
public class DiscountPolicyController {

    private final DiscountPolicyService discountPolicyService;

    /**
     * 지정된 할인 정책 ID에 대한 상세 정보를 조회하여 반환합니다.
     *
     * @param discountPolicyDetailId 조회할 할인 정책의 고유 ID
     * @return 할인 정책 상세 정보와 성공 메시지를 포함한 API 응답
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DiscountPolicyDetailResponseDto>> getDiscountPolicyDetail(
            @PathVariable("id") Long discountPolicyDetailId
    ) {
        DiscountPolicyDetailResponseDto response = discountPolicyService.getDiscountPolicyDetail(discountPolicyDetailId);
        return ResponseEntity.ok(ApiResponse.success("혜택 상세 조회 성공", response));
    }

    /**
     * 프랜차이즈 할인 정책 목록을 조회하여 페이징된 결과로 반환합니다.
     *
     * @param requestDto 프랜차이즈 할인 정책 목록 조회에 필요한 요청 파라미터
     * @return 페이징된 프랜차이즈 할인 정책 목록과 성공 메시지를 포함한 API 응답
     */
    @GetMapping("/franchise")
    public ResponseEntity<ApiResponse<Page<FranchiseDiscountPolicyResponseDto>>> getFranchiseDiscountPolicies(
            @ModelAttribute FranchiseDiscountPolicyRequestDto requestDto
    ) {
        Page<FranchiseDiscountPolicyResponseDto> response = discountPolicyService.getFranchiseDiscountPolicies(requestDto);
        return ResponseEntity.ok(ApiResponse.success("프랜차이즈 혜택 리스트 조회 성공", response));
    }

    /**
     * 프랜차이즈 할인 정책의 상세 정보를 조회하여 반환합니다.
     *
     * @param franchiseId 조회할 프랜차이즈 할인 정책의 ID
     * @return 프랜차이즈 할인 정책 상세 정보가 포함된 ApiResponse를 담은 ResponseEntity
     */
    @GetMapping("/franchise/{id}")
    public ResponseEntity<ApiResponse<FranchiseDiscountPolicyDetailResponseDto>> getFranchiseDiscountPolicyDetail(
            @PathVariable("id") Long franchiseId
    ) {
        FranchiseDiscountPolicyDetailResponseDto response = discountPolicyService.getFranchiseDiscountPolicyDetail(franchiseId);
        return ResponseEntity.ok(ApiResponse.success("프랜차이즈 혜택 상세 조회 성공", response));
    }


}

