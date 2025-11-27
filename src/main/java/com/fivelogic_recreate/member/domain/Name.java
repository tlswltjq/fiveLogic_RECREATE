package com.fivelogic_recreate.member.domain;

import java.util.Objects;

public final class Name {
    private final String firstName;
    private final String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        validate();
    }

    private void validate() {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalStateException("Invalid first name");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalStateException("Invalid last name");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(firstName, name.firstName)
                && Objects.equals(lastName, name.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}