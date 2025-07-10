package com.unear.userservice.auth.service.impl;

import com.unear.userservice.auth.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;

    @Override
    public String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(random.nextInt(10)); // 0~9 숫자 4자리
        }
        return code.toString();
    }

    @Override
    public void sendEmail(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("이메일 인증 코드");
        message.setText("인증 코드는: " + code);
        mailSender.send(message);
    }

    @Override
    public void saveCode(String email, String code) {
        redisTemplate.opsForValue().set("emailCode:" + email, code, Duration.ofMinutes(5));
    }

    @Override
    public boolean verifyCode(String email, String code) {
        String savedCode = redisTemplate.opsForValue().get("emailCode:" + email);
        boolean match = code.equals(savedCode);
        if (match) {
            redisTemplate.opsForValue().set("emailVerified:" + email, "true", Duration.ofMinutes(10));
            redisTemplate.delete("emailCode:" + email);
        }
        return match;
    }

    @Override
    public boolean isVerified(String email) {
        return "true".equals(redisTemplate.opsForValue().get("emailVerified:" + email));
    }

    @Override
    public void removeVerified(String email) {
        redisTemplate.delete("emailVerified:" + email);
    }

}
