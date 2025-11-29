package com.fivelogic_recreate.member.domain;

public record MemberId(Long id) {
    public MemberId {
        if (id == null) {
            throw new IllegalArgumentException("잘못된 ID 입니다.");
        }
    }
}
