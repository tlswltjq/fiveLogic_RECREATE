package com.fivelogic_recreate.member.domain;

import java.util.Objects;

public final class UserId {
    private final String userId;

    public UserId(String userId) {
        this.userId = userId;
        validate();
    }

    private void validate() {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("잘못된 사용자 ID 입니다.");
        }
        if (userId.length() < 5 || userId.length() > 12) {
            throw new IllegalArgumentException("사용자 ID는 5자 이상 12자 이하이어야 합니다.");
        }
    }

    public String value() {
        return userId;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId1 = (UserId) o;
        return Objects.equals(userId, userId1.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }
}
