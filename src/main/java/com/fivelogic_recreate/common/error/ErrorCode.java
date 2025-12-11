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
    EMAIL_DUPLICATION("M003", "이미 사용중인 Email 입니다", HttpStatus.CONFLICT)

    ,;

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
