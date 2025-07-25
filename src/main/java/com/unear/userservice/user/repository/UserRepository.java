package com.unear.userservice.user.repository;

import com.unear.userservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByBarcodeNumber(String barcodeNumber);

    @Query("SELECT u.membershipCode FROM User u WHERE u.userId = :userId")
    String findMembershipCodeByUserId(@Param("userId") Long userId);

}
