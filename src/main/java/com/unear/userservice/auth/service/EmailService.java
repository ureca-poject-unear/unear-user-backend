package com.unear.userservice.auth.service;

public interface EmailService {
    String generateCode();                      // 4자리 인증코드 생성
    void sendEmail(String toEmail, String code); // 이메일 전송
    void saveCode(String email, String code);    // 인증코드 저장 (ex: Redis)
    boolean verifyCode(String email, String code); // 인증코드 검증
    boolean isVerified(String email);
    void removeVerified(String email);
}
