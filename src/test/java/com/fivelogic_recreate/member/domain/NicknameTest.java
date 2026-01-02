package com.fivelogic_recreate.member.domain;

import com.fivelogic_recreate.member.domain.model.Nickname;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NicknameTest {

    @Test
    @DisplayName("정상적으로 Nickname 이 생성된다.")
    void nicknameCreationTest() {
        String nicknameValue = "nickname";
        Nickname nickname = new Nickname(nicknameValue);
        assertThat(nickname).isNotNull();
        assertThat(nickname.value()).isEqualTo(nicknameValue);
    }

    @Test
    @DisplayName("null 로 Nickname 을 생성하려 하면 오류가 발생한다.")
    void nicknameCreationTestWithNull() {
        assertThatThrownBy(() -> new Nickname(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("빈 문자열로 Nickname 을 생성하려 하면 오류가 발생한다.")
    void nicknameCreationTestWithEmptyString() {
        assertThatThrownBy(() -> new Nickname(""))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
