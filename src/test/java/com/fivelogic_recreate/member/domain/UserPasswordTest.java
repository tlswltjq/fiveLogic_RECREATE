package com.fivelogic_recreate.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserPasswordTest {
    @Test
    @DisplayName("정상적으로 UserPassword 가 생성된다.")
    void userPasswordCreationTest() {
        String password = "password";
        UserPassword userPassword = new UserPassword(password);
        assertThat(userPassword).isNotNull();
        assertThat(userPassword.value()).isEqualTo(password);
    }

    @Test
    @DisplayName("빈 문자열로 UserPassword 를 생성하려 하면 오류가 발생한다.")
    void userPasswordCreationTestWithBlankPassword() {
        assertThatThrownBy(() -> new UserPassword(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Null로 UserPassword 를 생성하려 하면 오류가 발생한다.")
    void userPasswordCreationTestWithNullPassword() {
        assertThatThrownBy(() -> new UserPassword(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("너무 짧은 길이의 문자열로 UserPassword 를 생성하려 하면 오류가 발생한다.")
    void userPasswordCreationTestWithShortPassword() {
        assertThatThrownBy(() -> new UserPassword("1234"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("너무 긴 길이의 문자열로 UserPassword 를 생성하려 하면 오류가 발생한다.")
    void userPasswordCreationTestWithLongPassword() {
        assertThatThrownBy(() -> new UserPassword("1234567891011121314151617181920"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}