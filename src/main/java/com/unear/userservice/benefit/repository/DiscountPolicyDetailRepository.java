package com.unear.userservice.benefit.repository;

import com.unear.userservice.benefit.entity.DiscountPolicyDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DiscountPolicyDetailRepository extends JpaRepository<DiscountPolicyDetail, Long>, JpaSpecificationExecutor<DiscountPolicyDetail> {

    @EntityGraph(attributePaths = {"place", "place.franchise"})
    Page<DiscountPolicyDetail> findAll(Specification<DiscountPolicyDetail> spec, Pageable pageable);

    @Query("SELECT d FROM DiscountPolicyDetail d JOIN FETCH d.place p LEFT JOIN FETCH p.franchise WHERE d.discountPolicyDetailId = :id")
    Optional<DiscountPolicyDetail> findWithPlaceAndFranchiseById(@Param("id") Long id);

}

