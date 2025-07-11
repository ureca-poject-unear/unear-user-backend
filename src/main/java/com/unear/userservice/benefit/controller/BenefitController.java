package com.unear.userservice.benefit.controller;


import com.unear.userservice.benefit.dto.request.DiscountPolicyDetailRequestDto;
import com.unear.userservice.benefit.dto.response.DiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.service.DiscountPolicyService;
import com.unear.userservice.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/benefits")
@RequiredArgsConstructor
public class BenefitController {

    private final DiscountPolicyService discountPolicyService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<DiscountPolicyDetailResponseDto>>> getAllBenefits(
            @ModelAttribute DiscountPolicyDetailRequestDto requestDto
    ) {
        Page<DiscountPolicyDetailResponseDto> response = discountPolicyService.getAllBenefits(requestDto);
        return ResponseEntity.ok(ApiResponse.success("혜택 목록 조회 성공", response));
    }

}

