package com.unear.userservice.roulette.entity;

import com.unear.userservice.event.entity.UnearEvent;
import com.unear.userservice.user.entity.User;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "roulette_results")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RouletteResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roulette_result_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unear_event_id", nullable = false)
    private UnearEvent event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "reward")
    private String reward;

    @Column(name = "participated")
    private Boolean participated;

    // 정적 생성 메서드
    public static RouletteResult of(UnearEvent event, User user, String reward, boolean participated) {
        RouletteResult result = new RouletteResult();
        result.event = event;
        result.user = user;
        result.reward = reward;
        result.participated = participated;
        return result;
    }
}
