package com.unear.userservice.roulette.service.impl;

import com.unear.userservice.common.exception.ErrorCode;
import com.unear.userservice.roulette.entity.RouletteResult;
import com.unear.userservice.event.entity.UnearEvent;
import com.unear.userservice.event.repository.EventRepository;
import com.unear.userservice.roulette.repository.RouletteResultRepository;
import com.unear.userservice.roulette.service.RouletteResultService;
import com.unear.userservice.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class RouletteResultServiceImpl implements RouletteResultService {

    private final RouletteResultRepository rouletteResultRepository;
    private final EventRepository eventRepository;

    @Override
    public boolean isAlreadyParticipated(Long eventId, User user) {
        UnearEvent event = getEventOrThrow(eventId);
        return rouletteResultRepository.findByUserAndEvent(user, event)
                .map(RouletteResult::getParticipated)
                .orElse(false);
    }

    @Override
    public String saveRouletteResult(Long eventId, User user, String reward) {
        UnearEvent event = getEventOrThrow(eventId);

        if (isAlreadyParticipated(eventId, user)) {
            throw new IllegalStateException(ErrorCode.ROULETTE_ALREADY_PARTICIPATED.getMessage());
        }

        RouletteResult result = RouletteResult.builder()
                .event(event)
                .user(user)
                .reward(reward) // 프론트에서 받은 보상
                .participated(true)
                .build();

        rouletteResultRepository.save(result);
        return reward;
    }

    @Override
    public String getRewardResult(Long eventId, User user) {
        UnearEvent event = getEventOrThrow(eventId);
        return rouletteResultRepository.findByUserAndEvent(user, event)
                .map(RouletteResult::getReward)
                .orElse(null);
    }

    private UnearEvent getEventOrThrow(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트가 존재하지 않습니다."));
    }
}
