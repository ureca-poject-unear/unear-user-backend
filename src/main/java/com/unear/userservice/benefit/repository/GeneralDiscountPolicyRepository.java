package com.unear.userservice.benefit.repository;

import com.unear.userservice.benefit.entity.GeneralDiscountPolicy;
import com.unear.userservice.place.entity.Place;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GeneralDiscountPolicyRepository extends JpaRepository<GeneralDiscountPolicy, Long>, JpaSpecificationExecutor<GeneralDiscountPolicy> {

    @Query("""
        SELECT d 
        FROM GeneralDiscountPolicy d
        LEFT JOIN FETCH d.place p
        LEFT JOIN FETCH p.franchise
        WHERE d.generalDiscountPolicyId = :id
    """)
    Optional<GeneralDiscountPolicy> findWithPlaceAndFranchiseById(@Param("id") Long id);

    @Query("SELECT p.generalDiscountPolicyId FROM GeneralDiscountPolicy p WHERE p.place.placeId = :placeId")
    List<Long> findPolicyIdsByPlaceId(@Param("placeId") Long placeId);

    List<GeneralDiscountPolicy> findByPlaceIn(List<Place> places);
}


