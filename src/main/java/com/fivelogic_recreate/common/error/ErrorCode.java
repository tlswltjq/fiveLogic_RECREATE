package com.fivelogic_recreate.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE("C001", "Invalid Input Value", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOWED("C002", "Method Not Allowed", HttpStatus.METHOD_NOT_ALLOWED),
    INTERNAL_SERVER_ERROR("C003", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),

    // Member
    MEMBER_NOT_FOUND("M001", "사용자를 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    USER_ID_DUPLICATION("M002", "이미 사용중인 사용자 ID 입니다", HttpStatus.CONFLICT),
    EMAIL_DUPLICATION("M003", "이미 사용중인 Email 입니다", HttpStatus.CONFLICT),
    PASSWORD_MISMATCH("M004", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    SAME_PASSWORD("M005", "새 비밀번호는 기존 비밀번호와 다르게 설정해야 합니다.", HttpStatus.BAD_REQUEST),

    // News
    NEWS_NOT_FOUND("N001", "뉴스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NEWS_PUBLISH_NOT_ALLOWED("N002", "발행 가능한 상태가 아닙니다.", HttpStatus.CONFLICT),
    NEWS_HIDE_NOT_ALLOWED("N003", "숨김 처리할 수 없는 뉴스 상태입니다.", HttpStatus.CONFLICT),
    NEWS_UN_HIDE_NOT_ALLOWED("N004", "숨김 해제할 수 없는 뉴스 상태입니다.", HttpStatus.CONFLICT),
    NEWS_DELETE_NOT_ALLOWED("N005", "삭제할 수 없는 뉴스 상태입니다.", HttpStatus.CONFLICT),

    // Auth
    LOGIN_FAILED("A001", "아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("A002", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED("A003", "접근이 거부되었습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
