package com.fivelogic_recreate.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserIdTest {
    @Test
    @DisplayName("정상적으로 UserId 가 생성된다.")
    void userIdCreationTest() {
        String userid = "newUserId";
        UserId userId = new UserId(userid);
        assertThat(userId).isNotNull();
        assertThat(userId.value()).isEqualTo(userid);
    }

    @Test
    @DisplayName("빈 문자열로 UserId를 생성하려 하면 오류가 발생한다.")
    void userIdCreationTestWithBlankUserId() {
        assertThatThrownBy(() -> new UserId(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("빈 문자열로 UserId를 생성하려 하면 오류가 발생한다.")
    void userIdCreationTestWithNullUserId() {
        assertThatThrownBy(() -> new UserId(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("너무 짧은 길이의 문자열로 UserId 를 생성하려 하면 오류가 발생한다.")
    void userIdCreationTestWithShortUserId() {
        assertThatThrownBy(() -> new UserId("id"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("너무 긴 길이의 문자열로 UserId 를 생성하려 하면 오류가 발생한다.")
    void userIdCreationTestWithLongUserId() {
        assertThatThrownBy(() -> new UserId("longlonglonglonglonglonglonglongUsdrId"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}