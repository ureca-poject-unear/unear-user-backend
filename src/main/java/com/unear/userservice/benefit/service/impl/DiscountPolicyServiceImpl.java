package com.unear.userservice.benefit.service.impl;

import com.unear.userservice.benefit.dto.request.DiscountPolicyDetailRequestDto;
import com.unear.userservice.benefit.dto.response.DiscountPolicyDetailResponseDto;
import com.unear.userservice.benefit.entity.DiscountPolicyDetail;
import com.unear.userservice.benefit.repository.DiscountPolicyDetailRepository;
import com.unear.userservice.benefit.repository.DiscountPolicyDetailSpec;
import com.unear.userservice.benefit.service.DiscountPolicyService;
import com.unear.userservice.exception.exception.BenefitNotFoundException;
import com.unear.userservice.place.entity.Franchise;
import com.unear.userservice.place.entity.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountPolicyServiceImpl implements DiscountPolicyService {

    private final DiscountPolicyDetailRepository discountPolicyDetailRepository;

    @Override
    public Page<DiscountPolicyDetailResponseDto> getAllBenefits(DiscountPolicyDetailRequestDto requestDto) {
        Pageable pageable = PageRequest.of(
                requestDto.getPage(), requestDto.getSize(), Sort.by("discountPolicyDetailId").descending()
        );

        Specification<DiscountPolicyDetail> spec = DiscountPolicyDetailSpec.withFilters(
                requestDto.getPlaceId(),
                requestDto.getMembershipCode(),
                requestDto.getMarkerCode(),
                requestDto.getCategoryCode()
        );

        return discountPolicyDetailRepository.findAll(spec, pageable)
                .map(detail -> {
                    Place place = detail.getPlace();
                    Franchise franchise = place.getFranchise();
                    return DiscountPolicyDetailResponseDto.from(detail);
                });
    }

    @Override
    public DiscountPolicyDetailResponseDto getBenefitDetail(Long discountPolicyDetailId) {
        DiscountPolicyDetail detail = discountPolicyDetailRepository.findWithPlaceAndFranchiseById(discountPolicyDetailId)
                .orElseThrow(() -> new BenefitNotFoundException("해당 혜택 정보를 찾을 수 없습니다."));
        return DiscountPolicyDetailResponseDto.from(detail);
    }


}