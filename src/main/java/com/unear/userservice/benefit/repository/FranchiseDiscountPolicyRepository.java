package com.unear.userservice.benefit.repository;

import com.unear.userservice.benefit.entity.FranchiseDiscountPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FranchiseDiscountPolicyRepository extends JpaRepository<FranchiseDiscountPolicy, Long> {

    @Query("SELECT f.franchiseDiscountPolicyId FROM FranchiseDiscountPolicy f WHERE f.franchise.franchiseId = :franchiseId")
    List<Long> findPolicyIdsByFranchiseId(@Param("franchiseId") Long franchiseId);

    List<FranchiseDiscountPolicy> findByFranchise_FranchiseId(Long franchiseId);
}
