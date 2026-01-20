package com.fivelogic_recreate.auth.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record DeviceId(
                String value) {
}
