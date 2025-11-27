package com.fivelogic_recreate.member.domain;

import java.util.Objects;

public final class Nickname {
    private final String nickname;

    public Nickname(String nickname) {
        this.nickname = nickname;
        validate();
    }

    public String value() {
        return nickname;
    }

    private void validate() {
        if (nickname == null || nickname.isEmpty()) {
            throw new IllegalArgumentException("잘못된 ID 입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Nickname nickname1 = (Nickname) o;
        return Objects.equals(nickname, nickname1.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }
}
