package com.fivelogic_recreate.auth.domain;

public record RefreshToken(String value) {
    public RefreshToken {
        if(value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        if(value.startsWith("Bearer")) {
            throw new IllegalArgumentException("Invalid Bearer token");
        }
    }
}
