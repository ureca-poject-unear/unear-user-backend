package com.unear.userservice.benefit.repository;

import com.unear.userservice.benefit.entity.DiscountPolicyDetail;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DiscountPolicyDetailRepository extends JpaRepository<DiscountPolicyDetail, Long>, JpaSpecificationExecutor<DiscountPolicyDetail> {

    /**
     * 지정된 ID에 해당하는 DiscountPolicyDetail 엔티티를 조회하며, 연관된 Place와 Place의 Franchise를 즉시 로딩합니다.
     *
     * @param id 조회할 DiscountPolicyDetail의 ID
     * @return Place와 Franchise가 즉시 로딩된 DiscountPolicyDetail의 Optional 객체. 해당 ID가 없으면 빈 Optional 반환
     */
    @Query("""
        SELECT d 
        FROM DiscountPolicyDetail d
        JOIN FETCH d.place p
        LEFT JOIN FETCH p.franchise
        WHERE d.discountPolicyDetailId = :id
    """)
    Optional<DiscountPolicyDetail> findWithPlaceAndFranchiseById(@Param("id") Long id);


}

