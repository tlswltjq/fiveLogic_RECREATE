package com.fivelogic_recreate.member.domain;

public enum MemberType {
    MENTOR, MENTEE, ADMIN;

    public static MemberType from(String value) {
        try {
            return MemberType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid member type: " + value);
        }
    }
}
