package com.unear.userservice.benefit.service.impl;

import com.unear.userservice.benefit.dto.request.FranchiseDiscountPolicyRequestDto;
import com.unear.userservice.benefit.dto.response.GeneralDiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.dto.response.FranchiseDiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.dto.response.FranchiseDiscountPolicyResponseDto;
import com.unear.userservice.benefit.entity.GeneralDiscountPolicy;
import com.unear.userservice.benefit.repository.GeneralDiscountPolicyRepository;
import com.unear.userservice.benefit.repository.FranchiseRepository;
import com.unear.userservice.benefit.service.DiscountPolicyService;
import com.unear.userservice.common.exception.exception.BenefitNotFoundException;
import com.unear.userservice.place.entity.Franchise;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountPolicyServiceImpl implements DiscountPolicyService {

    private final GeneralDiscountPolicyRepository generalDiscountPolicyRepository;
    private final FranchiseRepository franchiseRepository;

    @Override
    public GeneralDiscountPolicyDetailResponseDto getDiscountPolicyDetail(Long discountPolicyDetailId) {
        GeneralDiscountPolicy detail = generalDiscountPolicyRepository.findWithPlaceAndFranchiseById(discountPolicyDetailId)
                .orElseThrow(() -> new BenefitNotFoundException("해당 혜택 정보를 찾을 수 없습니다."));

        return GeneralDiscountPolicyDetailResponseDto.from(detail);
    }

    @Override
    public Page<FranchiseDiscountPolicyResponseDto> getFranchiseDiscountPolicies(FranchiseDiscountPolicyRequestDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize(), Sort.by("franchiseId").ascending());

        Specification<Franchise> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (requestDto.getCategoryCode() != null && !requestDto.getCategoryCode().isBlank()) {
                predicates.add(cb.equal(root.get("categoryCode"), requestDto.getCategoryCode()));
            }

            if (requestDto.getFranchiseName() != null && !requestDto.getFranchiseName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + requestDto.getFranchiseName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return franchiseRepository.findAll(spec, pageable)
                .map(FranchiseDiscountPolicyResponseDto::from);
    }

    @Override
    public FranchiseDiscountPolicyDetailResponseDto getFranchiseDiscountPolicyDetail(Long franchiseId) {
        Franchise franchise = franchiseRepository.findWithPoliciesByFranchiseId(franchiseId)
                .orElseThrow(() -> new BenefitNotFoundException("프랜차이즈 혜택을 찾을 수 없습니다."));
        return FranchiseDiscountPolicyDetailResponseDto.from(franchise);
    }


}