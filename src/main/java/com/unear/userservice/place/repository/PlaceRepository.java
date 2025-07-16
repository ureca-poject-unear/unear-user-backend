package com.unear.userservice.place.repository;

import com.unear.userservice.place.entity.Place;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    @EntityGraph(attributePaths = {"franchise", "eventPlaces", "discountPolicies"})
    Optional<Place> findById(Long id);

    @Query("""
    SELECT p FROM Place p
    LEFT JOIN FavoritePlace f 
      ON f.place = p 
      AND f.user.userId = :userId
    WHERE (:categoryCode IS NULL OR p.categoryCode = :categoryCode)
    AND (:benefitCategory IS NULL OR p.benefitCategory = :benefitCategory)
    AND (
      :isFavorite IS NULL
      OR (:isFavorite = TRUE AND f.isFavorited = TRUE)
      OR (:isFavorite = FALSE AND (f IS NULL OR f.isFavorited = FALSE))
    )
    AND (:southWestLatitude IS NULL OR p.latitude >= :southWestLatitude)
    AND (:northEastLatitude IS NULL OR p.latitude <= :northEastLatitude)
    AND (:southWestLongitude IS NULL OR p.longitude >= :southWestLongitude)
    AND (:northEastLongitude IS NULL OR p.longitude <= :northEastLongitude)
""")
    List<Place> findFilteredPlaces(
            @Param("userId") Long userId,
            @Param("categoryCode") String categoryCode,
            @Param("benefitCategory") String benefitCategory,
            @Param("isFavorite") Boolean isFavorite,
            @Param("southWestLatitude") Double southWestLatitude,
            @Param("northEastLatitude") Double northEastLatitude,
            @Param("southWestLongitude") Double southWestLongitude,
            @Param("northEastLongitude") Double northEastLongitude
    );

    @Query("SELECT p.franchise.franchiseId FROM Place p WHERE p.placeId = :placeId")
    Optional<Long> findFranchiseIdByPlaceId(@Param("placeId") Long placeId);


}

