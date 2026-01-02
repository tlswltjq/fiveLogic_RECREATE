package com.fivelogic_recreate.member.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record Name(String firstName, String lastName) {
    public Name {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("잘못된 first name 입니다.");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("잘못된 last name 입니다.");
        }
    }

    public String value() {
        return firstName + " " + lastName;
    }
}