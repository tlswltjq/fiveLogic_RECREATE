package com.fivelogic_recreate.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberIdTest {
    @Test
    @DisplayName("정상적으로 MemberId 가 생성된다.")
    void memberIdCreationTest() {
        Long id = 1L;
        MemberId memberId = new MemberId(id);
        assertThat(memberId).isNotNull();
        assertThat(memberId.id()).isEqualTo(id);
    }

    @Test
    @DisplayName("Null로 MemberId 를 생성하려 하면 오류가 발생한다.")
    void memberIdCreationTestWithNulll() {
        assertThatThrownBy(() -> new MemberId(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}