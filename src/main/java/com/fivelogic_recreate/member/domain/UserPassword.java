package com.fivelogic_recreate.member.domain;

import java.util.Objects;

public final class UserPassword {
    private final String password;

    public UserPassword(String password) {
        this.password = password;
        validate();
    }

    private void validate() {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("잘못된 사용자 PW 입니다.");
        }
        if (password.length() < 5 || password.length() > 12) {
            throw new IllegalArgumentException("사용자 PW는 5자 이상 12자 이하이어야 합니다.");
        }
    }

    public String value() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserPassword that = (UserPassword) o;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(password);
    }
}
