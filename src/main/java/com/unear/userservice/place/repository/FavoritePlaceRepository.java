package com.unear.userservice.place.repository;

import com.unear.userservice.place.entity.FavoritePlace;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FavoritePlaceRepository extends JpaRepository<FavoritePlace, Long> {

    @Query("SELECT f.place.placeId FROM FavoritePlace f " +
            "WHERE f.user.userId = :userId AND f.isFavorited = true")
    Set<Long> findPlaceIdsByUserId(@Param("userId") Long userId);

    boolean existsByUser_UserIdAndPlace_PlaceIdAndIsFavoritedTrue(Long userId, Long placeId);

    Optional<FavoritePlace> findByUser_UserIdAndPlace_PlaceId(Long userId, Long placeId);

    @Query("""
        SELECT f
        FROM FavoritePlace f
        JOIN FETCH f.place p
        LEFT JOIN FETCH p.franchise
        WHERE f.user.userId = :userId AND f.isFavorited = true
    """)
    List<FavoritePlace> findWithPlaceAndFranchiseByUserId(@Param("userId") Long userId);


}
