package com.unear.userservice.benefit.repository;

import com.unear.userservice.place.entity.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FranchiseRepository extends JpaRepository<Franchise, Long>, JpaSpecificationExecutor<Franchise> {
}

