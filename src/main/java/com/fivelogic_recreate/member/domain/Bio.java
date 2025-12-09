package com.fivelogic_recreate.member.domain;

public record Bio(String value) {
    private static final int MAX_LENGTH = 500;

    public Bio {
        if (value != null && (value.isBlank() || value.length() > MAX_LENGTH)) {
            throw new IllegalArgumentException("소개는 " + MAX_LENGTH + "자를 넘거나 공백일 수 없습니다.");
        }
    }
}
