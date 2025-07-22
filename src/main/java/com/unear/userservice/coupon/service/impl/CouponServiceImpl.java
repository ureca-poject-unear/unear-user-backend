package com.unear.userservice.coupon.service.impl;

import com.unear.userservice.benefit.repository.FranchiseDiscountPolicyRepository;
import com.unear.userservice.benefit.repository.GeneralDiscountPolicyRepository;
import com.unear.userservice.common.enums.CouponStatus;
import com.unear.userservice.common.enums.DiscountPolicy;
import com.unear.userservice.common.enums.MembershipGrade;
import com.unear.userservice.common.enums.PlaceType;
import com.unear.userservice.common.exception.exception.*;
import com.unear.userservice.coupon.dto.response.CouponResponseDto;
import com.unear.userservice.coupon.dto.response.UserCouponDetailResponseDto;
import com.unear.userservice.coupon.dto.response.UserCouponListResponseDto;
import com.unear.userservice.coupon.dto.response.UserCouponResponseDto;
import com.unear.userservice.coupon.entity.CouponTemplate;
import com.unear.userservice.coupon.entity.UserCoupon;
import com.unear.userservice.coupon.repository.CouponTemplateRepository;
import com.unear.userservice.coupon.repository.UserCouponRepository;
import com.unear.userservice.coupon.service.CouponService;
import com.unear.userservice.place.repository.PlaceRepository;
import com.unear.userservice.user.entity.User;
import com.unear.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final GeneralDiscountPolicyRepository generalDiscountPolicyRepository;
    private final FranchiseDiscountPolicyRepository franchiseDiscountPolicyRepository;
    private final PlaceRepository placeRepository;
    private final CouponTemplateRepository couponTemplateRepository;
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
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

        final String userMembershipCode =
                (userId != null && placeType.isFranchise())
                        ? userRepository.findMembershipCodeByUserId(userId)
                        : null;

        return templates.stream()
                .filter(template -> {

                    if (!placeType.isFranchise()) return true;

                    String templateMembershipCode = template.getMembershipCode();
                    if (templateMembershipCode == null) return false;

                    if (MembershipGrade.isAll(templateMembershipCode)) return true;

                    if (userMembershipCode == null) return false;

                    return templateMembershipCode.equalsIgnoreCase(userMembershipCode);
                })
                .map(template -> {
                    String discountInfo = DiscountPolicy.fromCode(template.getDiscountCode()).getLabel();
                    boolean isDownloaded = downloadedIds.contains(template.getCouponTemplateId());
                    return CouponResponseDto.from(template, discountInfo, isDownloaded);
                })
                .toList();
    }


    @Override
    @Transactional
    public UserCouponResponseDto downloadCoupon(Long userId, Long couponTemplateId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        CouponTemplate template = couponTemplateRepository.findById(couponTemplateId)
                .orElseThrow(() -> new CouponTemplateNotFoundException("쿠폰 템플릿을 찾을 수 없습니다."));

        LocalDateTime now = LocalDateTime.now();
        if (template.getCouponStart().isAfter(now) || template.getCouponEnd().isBefore(now)) {
            throw new CouponExpiredException("유효 기간이 지난 쿠폰입니다.");
        }

        UserCoupon userCoupon = UserCoupon.builder()
                .user(user)
                .couponTemplate(template)
                .createdAt(LocalDateTime.now())
                .couponStatusCode(CouponStatus.UNUSED.getCode())
                .barcodeNumber(generateUniqueBarcode())
                .build();

        try {
            userCouponRepository.save(userCoupon);
        } catch (DataIntegrityViolationException e) {
            throw new CouponAlreadyDownloadedException("이미 다운로드한 쿠폰입니다.");
        }

        return UserCouponResponseDto.from(userCoupon);
    }

    @Override
    @Transactional
    public UserCouponResponseDto downloadFCFSCoupon(Long userId, Long couponTemplateId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        CouponTemplate template = couponTemplateRepository.findById(couponTemplateId)
                .orElseThrow(() -> new CouponTemplateNotFoundException("쿠폰 템플릿을 찾을 수 없습니다."));

        LocalDateTime now = LocalDateTime.now();
        if (template.getCouponStart().isAfter(now) || template.getCouponEnd().isBefore(now)) {
            throw new CouponExpiredException("유효 기간이 지난 쿠폰입니다.");
        }

        template.decreaseQuantity();

        UserCoupon userCoupon = UserCoupon.builder()
                .user(user)
                .couponTemplate(template)
                .createdAt(LocalDateTime.now())
                .couponStatusCode(CouponStatus.UNUSED.getCode())
                .barcodeNumber(generateUniqueBarcode())
                .build();

        try {
            userCouponRepository.save(userCoupon);
        } catch (DataIntegrityViolationException e) {
            throw new CouponAlreadyDownloadedException("이미 다운로드한 쿠폰입니다.");
        }
        return UserCouponResponseDto.from(userCoupon);
    }


    @Override
    public UserCouponListResponseDto getMyCoupons(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        List<UserCoupon> userCoupons = userCouponRepository.findByUser_UserId(userId);

        List<UserCouponResponseDto> dtoList = userCoupons.stream()
                .map(UserCouponResponseDto::from)
                .toList();

        return new UserCouponListResponseDto(dtoList);
    }



    @Override
    public UserCouponDetailResponseDto getMyCouponDetail(Long userId, Long userCouponId) {
        UserCoupon userCoupon = userCouponRepository.findByUserCouponIdAndUser_UserId(userCouponId, userId)
                .orElseThrow(() -> new UserCouponNotFoundException("다운받은 쿠폰을 찾을 수 없습니다."));
        CouponTemplate template = userCoupon.getCouponTemplate();
        String markerCode = template.getMarkerCode();

        UserCouponDetailResponseDto.UserCouponDetailResponseDtoBuilder builder = UserCouponDetailResponseDto.builder()
                .userCouponId(userCoupon.getUserCouponId())
                .couponName(template.getCouponName())
                .barcodeNumber(userCoupon.getBarcodeNumber())
                .couponStatusCode(userCoupon.getCouponStatusCode())
                .createdAt(userCoupon.getCreatedAt())
                .couponEnd(template.getCouponEnd())
                .markerCode(markerCode);

        if (template.getDiscountPolicyDetailId() == null) {
            return builder.build();
        }

        PlaceType placeType = PlaceType.fromCode(markerCode);
        if (placeType.isFranchise()) {
            franchiseDiscountPolicyRepository.findById(template.getDiscountPolicyDetailId()).ifPresent(policy ->
                    builder
                            .discountCode(policy.getDiscountCode())
                            .membershipCode(policy.getMembershipCode())
                            .fixedDiscount(policy.getFixedDiscount())
                            .discountPercent(policy.getDiscountPercent())
                            .minPurchaseAmount(policy.getMinPurchaseAmount())
                            .maxDiscountAmount(policy.getMaxDiscountAmount())
            );
        } else if (placeType.isBasic()) {
            generalDiscountPolicyRepository.findById(template.getDiscountPolicyDetailId()).ifPresent(policy ->
                    builder
                            .discountCode(policy.getDiscountCode())
                            .membershipCode(policy.getMembershipCode())
                            .fixedDiscount(policy.getFixedDiscount())
                            .discountPercent(policy.getDiscountPercent())
                            .minPurchaseAmount(policy.getMinPurchaseAmount())
                            .maxDiscountAmount(policy.getMaxDiscountAmount())
            );
        }

        return builder.build();
    }



    private String generateUniqueBarcode() {
        for (int i = 0; i < 3; i++) {
            String barcode = generateBarcode();
            if (!userCouponRepository.existsByBarcodeNumber(barcode)) {
                return barcode;
            }
        }
        throw new BarcodeDuplicatedException("중복된 바코드로 인해 바코드 생성 실패");
    }

    private String generateBarcode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }


}
