package com.unear.userservice.coupon.service.impl;

import com.unear.userservice.benefit.repository.FranchiseDiscountPolicyRepository;
import com.unear.userservice.benefit.repository.GeneralDiscountPolicyRepository;
import com.unear.userservice.common.enums.DiscountPolicy;
import com.unear.userservice.common.enums.PlaceType;
import com.unear.userservice.coupon.dto.response.CouponResponseDto;
import com.unear.userservice.coupon.entity.CouponTemplate;
import com.unear.userservice.coupon.repository.CouponTemplateRepository;
import com.unear.userservice.coupon.repository.UserCouponRepository;
import com.unear.userservice.coupon.service.CouponService;
import com.unear.userservice.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final GeneralDiscountPolicyRepository generalDiscountPolicyRepository;
    private final FranchiseDiscountPolicyRepository franchiseDiscountPolicyRepository;
    private final PlaceRepository placeRepository;
    private final CouponTemplateRepository couponTemplateRepository;
    private final UserCouponRepository userCouponRepository;

    @Override
    public List<CouponResponseDto> getCouponsByPlaceAndMarker(Long userId, Long placeId, String markerCode) {
        PlaceType placeType = PlaceType.fromCode(markerCode);

        List<Long> discountPolicyIds = List.of();
        if (placeType.isBasic()) {
            discountPolicyIds = generalDiscountPolicyRepository.findPolicyIdsByPlaceId(placeId);
        } else if (placeType.isFranchise()) {
            Optional<Long> franchiseIdOpt = placeRepository.findFranchiseIdByPlaceId(placeId);
            if (franchiseIdOpt.isEmpty()) return List.of();
            Long franchiseId = franchiseIdOpt.get();
            discountPolicyIds = franchiseDiscountPolicyRepository.findPolicyIdsByFranchiseId(franchiseId);
        }

        if (discountPolicyIds.isEmpty()) return List.of();

        List<CouponTemplate> templates = couponTemplateRepository
                .findByDiscountPolicyDetailIdInAndMarkerCode(discountPolicyIds, placeType.getCode());


        Set<Long> downloadedIds = (userId != null)
                ? userCouponRepository.findCouponTemplateIdsByUserId(userId)
                : Set.of();

        return templates.stream()
                .map(template -> {
                    String discountInfo = DiscountPolicy.fromCode(template.getDiscountCode()).getLabel();
                    boolean isDownloaded = downloadedIds.contains(template.getCouponTemplateId());

                    return CouponResponseDto.from(template, discountInfo, isDownloaded);
                })
                .toList();
    }

}
