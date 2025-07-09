package com.unear.userservice.common.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final StringRedisTemplate stringRedisTemplate;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";

    public void saveRefreshToken(Long userId, String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        Duration expiration = Duration.ofMillis(refreshTokenExpiration);

        stringRedisTemplate.opsForValue().set(key, refreshToken, expiration);
        log.info("Refresh token saved for user: {}", userId);
    }

    public String getRefreshToken(Long userId) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        String token = stringRedisTemplate.opsForValue().get(key);

        if (token != null) {
            log.info("Refresh token found for user: {}", userId);
        } else {
            log.info("No refresh token found for user: {}", userId);
        }

        return token;
    }

    public void deleteRefreshToken(Long userId) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        Boolean deleted = stringRedisTemplate.delete(key);

        if (Boolean.TRUE.equals(deleted)) {
            log.info("Refresh token deleted for user: {}", userId);
        } else {
            log.warn("No refresh token to delete for user: {}", userId);
        }
    }

    public boolean hasRefreshToken(Long userId) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        Boolean exists = stringRedisTemplate.hasKey(key);
        return Boolean.TRUE.equals(exists);
    }

    public boolean validateRefreshToken(Long userId, String refreshToken) {
        String storedToken = getRefreshToken(userId);
        boolean isValid = storedToken != null && storedToken.equals(refreshToken);

        if (isValid) {
            log.info("Refresh token validation successful for user: {}", userId);
        } else {
            log.warn("Refresh token validation failed for user: {}", userId);
        }

        return isValid;
    }

    public void deleteAllRefreshTokens() {
        String pattern = REFRESH_TOKEN_PREFIX + "*";
        var keys = stringRedisTemplate.keys(pattern);

        if (keys != null && !keys.isEmpty()) {
            stringRedisTemplate.delete(keys);
            log.info("All refresh tokens deleted. Count: {}", keys.size());
        }
    }
}

