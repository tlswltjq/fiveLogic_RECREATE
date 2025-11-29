package com.fivelogic_recreate.member.domain;

public record Name(String firstName, String lastName) {
    public Name {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("Invalid first name");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Invalid last name");
        }
    }
}