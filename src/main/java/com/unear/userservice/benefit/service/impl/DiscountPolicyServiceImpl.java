package com.unear.userservice.benefit.service.impl;

import com.unear.userservice.benefit.dto.request.DiscountPolicyDetailRequestDto;
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
import jakarta.persistence.EntityNotFoundException;
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

    private final DiscountPolicyDetailRepository discountPolicyDetailRepository;
    private final FranchiseRepository franchiseRepository;

    /**
     * 지정된 ID에 해당하는 할인 정책 상세 정보를 조회하여 반환합니다.
     *
     * @param discountPolicyDetailId 조회할 할인 정책 상세 정보의 ID
     * @return 할인 정책 상세 정보 응답 DTO
     * @throws BenefitNotFoundException 해당 ID의 할인 정책 상세 정보가 존재하지 않을 경우 발생
     */
    @Override
    public DiscountPolicyDetailResponseDto getDiscountPolicyDetail(Long discountPolicyDetailId) {
        DiscountPolicyDetail detail = discountPolicyDetailRepository.findWithPlaceAndFranchiseById(discountPolicyDetailId)
                .orElseThrow(() -> new BenefitNotFoundException("해당 혜택 정보를 찾을 수 없습니다."));

        return DiscountPolicyDetailResponseDto.from(detail);
    }

    /**
     * 가맹점 할인 정책 목록을 페이지 단위로 조회합니다.
     *
     * 요청 DTO의 카테고리 코드가 제공된 경우 해당 카테고리로 필터링하며, 결과는 페이지네이션되어 반환됩니다.
     *
     * @param requestDto 가맹점 할인 정책 목록 조회 요청 정보
     * @return 가맹점 할인 정책 응답 DTO의 페이지 객체
     */
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

    /**
     * 주어진 프랜차이즈 ID에 해당하는 프랜차이즈의 할인 정책 상세 정보를 조회합니다.
     *
     * @param franchiseId 조회할 프랜차이즈의 ID
     * @return 프랜차이즈 할인 정책 상세 정보 DTO
     * @throws BenefitNotFoundException 해당 ID의 프랜차이즈가 존재하지 않을 경우 발생
     */
    @Override
    public FranchiseDiscountPolicyDetailResponseDto getFranchiseDiscountPolicyDetail(Long franchiseId) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new BenefitNotFoundException("프랜차이즈 혜택을 찾을 수 없습니다."));
        return FranchiseDiscountPolicyDetailResponseDto.from(franchise);
    }

}