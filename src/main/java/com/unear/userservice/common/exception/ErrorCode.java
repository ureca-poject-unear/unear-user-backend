package com.unear.userservice.common.exception;


import com.unear.userservice.common.enums.EventType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C500", "서버 내부 오류가 발생했습니다"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U404", "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "INVALID_PASSWORD", "비밀번호가 올바르지 않습니다"),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN", "유효하지 않은 토큰입니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "EXPIRED_TOKEN", "만료된 토큰입니다"),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "LOGIN_REQUIRED", "로그인이 필요합니다"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "INVALID_INPUT_VALUE", "올바르지 않은 입력값입니다."),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "DUPLICATED_EMAIL", "이미 가입된 이메일입니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.BAD_REQUEST, "EMAIL_NOT_VERIFIED", "이메일 인증이 필요합니다."),
    BENEFIT_NOT_FOUND(HttpStatus.NOT_FOUND, "BENEFIT_NOT_FOUND" , "혜택 정보를 찾을 수 없습니다."),
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "PLACE_NOT_FOUND", "장소 정보를 찾을 수 없습니다."),
    INVALID_CODE(HttpStatus.NOT_FOUND, "INVALID_CODE", "유효하지 않은 공통코드입니다."),
    COUPON_TEMPLATE_NOT_FOUND(HttpStatus.NOT_FOUND , "COUPON_TEMPLATE_NOT_FOUND" , "쿠폰 템플릿을 찾을 수 없습니다." ),
    COUPON_ALREADY_DOWNLOADED(HttpStatus.BAD_REQUEST, "COUPON_ALREADY_DOWNLOADED", "이미 다운로드한 쿠폰입니다."),
    DUPLICATED_BARCODE(HttpStatus.BAD_REQUEST, "DUPLICATED_BARCODE" , "중복된 바코드 번호입니다."),
    COUPON_SOLD_OUT(HttpStatus.BAD_REQUEST, "COUPON_SOLD_OUT" , "쿠폰 재고가 없습니다."),
    COUPON_EXPIRED(HttpStatus.BAD_REQUEST, "COUPON_EXPIRED", "유효 기간이 지난 쿠폰입니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED_USER", "인증되지 않은 사용자입니다."),
    USER_COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_COUPON_NOT_FOUND", "다운받은 쿠폰을 찾을 수 없습니다."),
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND,"EVENT_NOT_FOUND","이벤트를 찾지 못했습니다."),
    ROULETTE_ALREADY_PARTICIPATED(HttpStatus.BAD_REQUEST, "ROULETTE_ALREADY_PARTICIPATED", "이미 룰렛에 참여하였습니다.")
    ;


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
