package com.fivelogic_recreate.member.domain;

import java.util.Objects;

public final class MemberId {
    private final Long id;

    public MemberId(Long id) {
        this.id = id;
        validate();
    }

    private void validate() {
        if (id == null) {
            throw new IllegalArgumentException("잘못된 ID 입니다.");
        }
    }

    public Long value() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MemberId memberId = (MemberId) o;
        return Objects.equals(id, memberId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
