package com.fivelogic_recreate.auth.domain.model;

public record AccessToken(String value) {
    public AccessToken {
        if(value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        if(value.startsWith("Bearer")) {
            throw new IllegalArgumentException("Invalid Bearer token");
        }
    }
}
