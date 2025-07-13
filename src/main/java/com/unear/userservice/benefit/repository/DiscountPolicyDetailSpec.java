package com.unear.userservice.benefit.repository;

import com.unear.userservice.benefit.entity.DiscountPolicyDetail;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DiscountPolicyDetailSpec {

    public static Specification<DiscountPolicyDetail> withFilters(Long placeId, String membershipCode, String markerCode, String categoryCode) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (placeId != null) {
                predicates.add(cb.equal(root.get("place").get("placesId"), placeId));
            }

            if (membershipCode != null) {
                predicates.add(cb.equal(root.get("membershipCode"), membershipCode));
            }

            if (markerCode != null) {
                predicates.add(cb.equal(root.get("markerCode"), markerCode));
            }

            if (categoryCode != null && !categoryCode.isBlank()) {
                predicates.add(cb.equal(root.get("place").get("categoryCode"), categoryCode));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

