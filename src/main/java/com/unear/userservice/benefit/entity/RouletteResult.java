package com.unear.userservice.benefit.entity;

import com.unear.userservice.event.entity.UnearEvent;
import com.unear.userservice.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roulette_results")
@Getter
@Setter
public class RouletteResult {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rouletteResultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unear_event_id")
    private UnearEvent event;

    private String reward;
    private Boolean participated;
}

