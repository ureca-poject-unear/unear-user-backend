package com.unear.userservice.benefit.repository;

import com.unear.userservice.place.entity.Franchise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface FranchiseRepository extends JpaRepository<Franchise, Long>, JpaSpecificationExecutor<Franchise> {

    @EntityGraph(attributePaths = "franchiseDiscountPolicies")
    Page<Franchise> findAll(Specification<Franchise> spec, Pageable pageable);

    @EntityGraph(attributePaths = "franchiseDiscountPolicies")
    Optional<Franchise> findWithPoliciesByFranchiseId(Long id);

}

