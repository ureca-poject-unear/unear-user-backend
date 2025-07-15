package com.unear.userservice.benefit.repository;

import com.unear.userservice.benefit.entity.DiscountPolicyDetail;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DiscountPolicyDetailRepository extends JpaRepository<DiscountPolicyDetail, Long>, JpaSpecificationExecutor<DiscountPolicyDetail> {

    @Query("""
        SELECT d 
        FROM DiscountPolicyDetail d
        JOIN FETCH d.place p
        LEFT JOIN FETCH p.franchise
        WHERE d.discountPolicyDetailId = :id
    """)
    Optional<DiscountPolicyDetail> findWithPlaceAndFranchiseById(@Param("id") Long id);


}

