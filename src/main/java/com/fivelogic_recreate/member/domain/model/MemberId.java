package com.fivelogic_recreate.member.domain.model;

public record MemberId(Long value) {
    public MemberId {
        if (value == null) {
            throw new IllegalArgumentException("잘못된 ID 입니다.");
        }
    }
}
