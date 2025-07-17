package com.unear.userservice.coupon.repository;

import com.unear.userservice.coupon.entity.CouponTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponTemplateRepository extends JpaRepository<CouponTemplate, Long> {
    List<CouponTemplate> findByDiscountPolicyDetailIdInAndMarkerCode(List<Long> discountPolicyIds, String markerCode);
}
