package com.unear.userservice.stamp.controller;

import com.unear.userservice.common.response.ApiResponse;
import com.unear.userservice.common.security.CustomUser;
import com.unear.userservice.stamp.dto.response.StampStatusResponseDto;
import com.unear.userservice.stamp.service.StampService;
import com.unear.userservice.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stamps")
public class StampController {

    private final StampService stampService;

    @GetMapping("/events/{eventId}")
    public ResponseEntity<ApiResponse<StampStatusResponseDto>> getStampsByEvent(
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable Long eventId
    ) {
        StampStatusResponseDto result = stampService.getStampStatus(customUser.getId(), eventId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
