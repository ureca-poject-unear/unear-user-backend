package com.unear.userservice.place.service.impl;

import com.unear.userservice.benefit.entity.FranchiseDiscountPolicy;
import com.unear.userservice.benefit.entity.GeneralDiscountPolicy;
import com.unear.userservice.benefit.repository.FranchiseDiscountPolicyRepository;
import com.unear.userservice.benefit.repository.GeneralDiscountPolicyRepository;
import com.unear.userservice.common.enums.DiscountPolicy;
import com.unear.userservice.common.enums.PlaceType;
import com.unear.userservice.coupon.entity.CouponTemplate;
import com.unear.userservice.place.entity.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BenefitDescriptionResolver {

    private final FranchiseDiscountPolicyRepository franchiseDiscountPolicyRepository;
    private final GeneralDiscountPolicyRepository generalDiscountPolicyRepository;

    public String resolveBenefitDesc(PlaceServiceImpl.DiscountPolicyRef policyRef, String membershipCode) {
        NumberFormat numberFormat = NumberFormat.getInstance();

        if (policyRef != null && policyRef.franchiseId() != null) {
            List<FranchiseDiscountPolicy> policies = franchiseDiscountPolicyRepository
                    .findByFranchise_FranchiseId(policyRef.franchiseId());

            FranchiseDiscountPolicy matchedPolicy = policies.stream()
                    .filter(p1 -> p1.getMembershipCode().equalsIgnoreCase(membershipCode))
                    .findFirst()
                    .orElse(null);

            if (matchedPolicy != null) {
                Integer percent = matchedPolicy.getDiscountPercent();
                Integer max = matchedPolicy.getMaxDiscountAmount();
                Integer fixed = matchedPolicy.getFixedDiscount();
                Integer unit = matchedPolicy.getUnitBaseAmount();

                if (percent != null && max != null) {
                    return percent + "% 할인 쿠폰 증정 (최대 " + numberFormat.format(max) + "원 할인)";
                } else if (percent != null) {
                    return percent + "% 할인 쿠폰 증정";
                } else if (fixed != null) {
                    String discountCode = matchedPolicy.getDiscountCode();
                    DiscountPolicy discountPolicy = DiscountPolicy.fromCode(discountCode);
                    if (discountPolicy.isMembershipFixed()) {
                        return "멤버십 혜택: " + numberFormat.format(fixed) + "원 할인";
                    } else {
                        return numberFormat.format(fixed) + "원 금액 할인 쿠폰 증정";
                    }
                } else if (unit != null) {
                    return "멤버십 혜택: 1,000원당 " + numberFormat.format(unit) + "원 할인";
                } else {
                    return "기본 멤버십 혜택 제공";
                }
            }

        } else if (policyRef != null && policyRef.discountPolicyDetailId() != null) {
            GeneralDiscountPolicy policy = generalDiscountPolicyRepository.findById(policyRef.discountPolicyDetailId())
                    .orElse(null);

            if (policy != null) {
                Integer percent = policy.getDiscountPercent();
                Integer max = policy.getMaxDiscountAmount();
                Integer fixed = policy.getFixedDiscount();
                Integer unit = policy.getUnitBaseAmount();

                if (percent != null && max != null) {
                    return percent + "% 할인 쿠폰 증정 (최대 " + numberFormat.format(max) + "원 할인)";
                } else if (percent != null) {
                    return percent + "% 할인 쿠폰 증정";
                } else if (fixed != null) {
                    String discountCode = policy.getDiscountCode();
                    DiscountPolicy discountPolicy = DiscountPolicy.fromCode(discountCode);
                    if (discountPolicy.isMembershipFixed()) {
                        return "멤버십 혜택: " + numberFormat.format(fixed) + "원 할인";
                    } else {
                        return numberFormat.format(fixed) + "원 금액 할인 쿠폰 증정";
                    }
                } else if (unit != null) {
                    return "멤버십 혜택: 1,000원당 " + numberFormat.format(unit) + "원 할인";
                } else {
                    return "기본 멤버십 혜택 제공";
                }
            }
        }

        return null;
    }



    public List<Long> resolvePlaceIdFromTemplateList(CouponTemplate ct, List<Place> places) {
        PlaceType markerType = PlaceType.fromCode(ct.getMarkerCode());

        if (markerType.isFranchise()) {
            Long policyId = ct.getDiscountPolicyDetailId();
            if (policyId == null) return List.of();

            Long franchiseId = franchiseDiscountPolicyRepository.findById(policyId)
                    .map(f -> f.getFranchise().getFranchiseId())
                    .orElse(null);

            if (franchiseId == null) return List.of();

            return places.stream()
                    .filter(p -> p.getFranchise() != null && p.getFranchise().getFranchiseId().equals(franchiseId))
                    .map(Place::getPlaceId)
                    .toList();

        } else {
            return generalDiscountPolicyRepository.findById(ct.getDiscountPolicyDetailId())
                    .map(g -> g.getPlace().getPlaceId())
                    .map(List::of)
                    .orElse(List.of());
        }
    }
}

