package com.fivelogic_recreate.member.domain;

public record UserId(String userId) {
    public UserId {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("잘못된 사용자 ID 입니다.");
        }
        if (userId.length() < 5 || userId.length() > 12) {
            throw new IllegalArgumentException("사용자 ID는 5자 이상 12자 이하이어야 합니다.");
        }
    }
}
