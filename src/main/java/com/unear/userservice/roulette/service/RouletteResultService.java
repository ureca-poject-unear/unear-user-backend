package com.unear.userservice.roulette.service;

import com.unear.userservice.user.entity.User;

public interface RouletteResultService {

    boolean isAlreadyParticipated(Long eventId, User user);

    public String saveRouletteResult(Long eventId, User user, String reward);

    String getRewardResult(Long eventId, User user);
}
