package com.unear.userservice.place.repository;

import com.unear.userservice.place.dto.response.NearestPlaceProjection;
import com.unear.userservice.place.entity.Place;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    @EntityGraph(attributePaths = {"franchise", "eventPlaces", "discountPolicies"})
    Optional<Place> findById(Long id);

    @Query(value = """
        SELECT p.* FROM places p
        LEFT JOIN favorite_places f\s
          ON f.place_id = p.place_id\s
          AND f.user_id = :userId
        WHERE (:categoryCodeSize = 0 OR p.category_code IN (:categoryCode))
          AND (:benefitCategorySize = 0 OR p.benefit_category IN (:benefitCategory))
          AND (
            :isFavorite IS NULL
            OR (:isFavorite = TRUE AND f.is_favorited = TRUE)
            OR (:isFavorite = FALSE AND (f.favorite_place_id IS NULL OR f.is_favorited = FALSE))
          )
          AND (:southWestLatitude IS NULL OR p.latitude >= :southWestLatitude)
          AND (:northEastLatitude IS NULL OR p.latitude <= :northEastLatitude)
          AND (:southWestLongitude IS NULL OR p.longitude >= :southWestLongitude)
          AND (:northEastLongitude IS NULL OR p.longitude <= :northEastLongitude)
          AND (:keyword IS NULL OR p.place_name ILIKE ('%' || :keyword || '%'))
    """, nativeQuery = true)
    List<Place> findFilteredPlaces(
            @Param("userId") Long userId,
            @Param("categoryCode") List<String> categoryCode,
            @Param("categoryCodeSize") int categoryCodeSize,
            @Param("benefitCategory") List<String> benefitCategory,
            @Param("benefitCategorySize") int benefitCategorySize,
            @Param("isFavorite") Boolean isFavorite,
            @Param("southWestLatitude") Double southWestLatitude,
            @Param("northEastLatitude") Double northEastLatitude,
            @Param("southWestLongitude") Double southWestLongitude,
            @Param("northEastLongitude") Double northEastLongitude,
            @Param("keyword") String keyword
    );

    @Query("SELECT p.franchise.franchiseId FROM Place p WHERE p.placeId = :placeId")
    Optional<Long> findFranchiseIdByPlaceId(@Param("placeId") Long placeId);

    @Query(value = """
    SELECT 
        place_id AS placeId,
        ST_Distance(
            CAST(location AS geography), 
            CAST(ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326) AS geography)
        ) AS distance
    FROM places
    ORDER BY distance
    LIMIT :limit
    """, nativeQuery = true)
    List<NearestPlaceProjection> findNearestPlaceIdsByDistance(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("limit") int limit
    );

    @Query(value = """
        SELECT ST_Distance(
            CAST(location AS geography),
            CAST(ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326) AS geography)
        )
        FROM places
        WHERE place_id = :placeId
    """, nativeQuery = true)
    Double calculateDistance(@Param("placeId") Long placeId,
                             @Param("latitude") Double latitude,
                             @Param("longitude") Double longitude);



}

