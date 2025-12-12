package com.fivelogic_recreate.news.domain;

public record NewsId(Long value) {
    public NewsId {
        if (value == null) {
            throw new IllegalArgumentException("잘못된 ID 입니다.");
        }
    }
}
