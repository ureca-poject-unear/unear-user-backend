package com.unear.userservice.coupon.repository;

import com.unear.userservice.coupon.entity.CouponTemplate;
import com.unear.userservice.coupon.entity.UserCoupon;
import com.unear.userservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    @Query("""
        SELECT uc.couponTemplate.couponTemplateId
        FROM UserCoupon uc
        WHERE uc.user.userId = :userId
    """)
    Set<Long> findCouponTemplateIdsByUserId(@Param("userId") Long userId);

    boolean existsByBarcodeNumber(String barcodeNumber);

}
