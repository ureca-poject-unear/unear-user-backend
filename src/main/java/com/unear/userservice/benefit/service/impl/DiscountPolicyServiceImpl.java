package com.unear.userservice.benefit.service.impl;

import com.unear.userservice.benefit.dto.request.FranchiseDiscountPolicyRequestDto;
import com.unear.userservice.benefit.dto.response.DiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.dto.response.FranchiseDiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.dto.response.FranchiseDiscountPolicyResponseDto;
import com.unear.userservice.benefit.entity.DiscountPolicyDetail;
import com.unear.userservice.benefit.repository.DiscountPolicyDetailRepository;
import com.unear.userservice.benefit.repository.FranchiseRepository;
import com.unear.userservice.benefit.service.DiscountPolicyService;
import com.unear.userservice.exception.exception.BenefitNotFoundException;
import com.unear.userservice.place.entity.Franchise;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountPolicyServiceImpl implements DiscountPolicyService {

    private final DiscountPolicyDetailRepository discountPolicyDetailRepository;
    private final FranchiseRepository franchiseRepository;

    @Override
    public DiscountPolicyDetailResponseDto getDiscountPolicyDetail(Long discountPolicyDetailId) {
        DiscountPolicyDetail detail = discountPolicyDetailRepository.findWithPlaceAndFranchiseById(discountPolicyDetailId)
                .orElseThrow(() -> new BenefitNotFoundException("해당 혜택 정보를 찾을 수 없습니다."));

        return DiscountPolicyDetailResponseDto.from(detail);
    }

    @Override
    public Page<FranchiseDiscountPolicyResponseDto> getFranchiseDiscountPolicies(FranchiseDiscountPolicyRequestDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        Specification<Franchise> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (requestDto.getCategoryCode() != null && !requestDto.getCategoryCode().isBlank()) {
                predicates.add(cb.equal(root.get("categoryCode"), requestDto.getCategoryCode()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return franchiseRepository.findAll(spec, pageable)
                .map(FranchiseDiscountPolicyResponseDto::from);
    }

    @Override
    public FranchiseDiscountPolicyDetailResponseDto getFranchiseDiscountPolicyDetail(Long franchiseId) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new BenefitNotFoundException("프랜차이즈 혜택을 찾을 수 없습니다."));
        return FranchiseDiscountPolicyDetailResponseDto.from(franchise);
    }

}