package com.unear.userservice.coupon.repository;

import com.unear.userservice.common.enums.DiscountPolicy;
import com.unear.userservice.coupon.entity.CouponTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponTemplateRepository extends JpaRepository<CouponTemplate, Long> {
    List<CouponTemplate> findByDiscountPolicyDetailIdInAndMarkerCode(List<Long> discountPolicyIds, String markerCode);

    @Query("""
SELECT ct
FROM CouponTemplate ct
WHERE ct.event.unearEventId = :eventId
AND ct.discountCode = :discountCode
""")
    List<CouponTemplate> findByEventIdAndDiscountCode(
            @Param("eventId") Long eventId,
            @Param("discountCode") DiscountPolicy discountCode
    );
}
