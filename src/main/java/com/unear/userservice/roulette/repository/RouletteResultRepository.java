package com.unear.userservice.roulette.repository;

import com.unear.userservice.event.entity.UnearEvent;

import com.unear.userservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RouletteResultRepository extends JpaRepository<com.unear.userservice.roulette.entity.RouletteResult, Long> {
    Optional<com.unear.userservice.roulette.entity.RouletteResult> findByUserAndEvent(User user, UnearEvent event);
    List<com.unear.userservice.roulette.entity.RouletteResult> findAllByUser(User user);
}
