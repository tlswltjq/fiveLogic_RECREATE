package com.fivelogic_recreate.auth.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;

@Embeddable
public record Expiration(
                LocalDateTime value) {
}
