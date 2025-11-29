package com.fivelogic_recreate.member.domain;

public record Bio(String value) {
    private static final int MAX_LENGTH = 500;

    public Bio {
        if (value != null && (value.isBlank() || value.length() > MAX_LENGTH)) {
            throw new IllegalArgumentException("Bio must be less than " + MAX_LENGTH + " characters and not blank");
        }
    }
}
