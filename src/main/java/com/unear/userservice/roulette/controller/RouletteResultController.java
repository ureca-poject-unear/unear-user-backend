package com.unear.userservice.roulette.controller;

import com.unear.userservice.common.annotation.LoginUser;
import com.unear.userservice.common.response.ApiResponse;
import com.unear.userservice.roulette.dto.request.RouletteRewardRequestDto;
import com.unear.userservice.roulette.service.RouletteResultService;
import com.unear.userservice.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roulette")
@RequiredArgsConstructor
public class RouletteResultController {

    private final RouletteResultService rouletteResultService;

    @PostMapping("/spin/{eventId}")
    public ResponseEntity<ApiResponse<String>> spinRoulette(
            @PathVariable Long eventId,
            @RequestBody @Valid RouletteRewardRequestDto request,
            @LoginUser User user
    ) {
        String reward = rouletteResultService.saveRouletteResult(eventId, user, request.getReward());
        return ResponseEntity.ok(ApiResponse.success("룰렛 결과 저장 성공", reward));
    }


    @Operation(summary = "룰렛 결과 조회", description = "이벤트별로 유저가 받은 보상을 확인합니다.")
    @GetMapping("/result/{eventId}")
    public ResponseEntity<ApiResponse<String>> getRouletteResult(
            @PathVariable Long eventId,
            @LoginUser User user
    ) {
        String reward = rouletteResultService.getRewardResult(eventId, user);
        return ResponseEntity.ok(ApiResponse.success("룰렛 결과 조회 성공", reward));
    }
}
