package com.fivelogic_recreate.member.interfaces.rest.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final int status;       // HTTP 상태 코드
    private final String errorCode;  // 도메인 특화 에러 코드 (예: M-404)
    private final String message;    // 사용자에게 보여줄 에러 메시지
    private final String path;       // 에러가 발생한 API 경로
    //TODO MemberControllerAdvice와 함께 구현
}