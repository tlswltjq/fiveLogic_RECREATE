package com.fivelogic_recreate.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @Test
    @DisplayName("정상적으로 Email 이 생성된다.")
    void emailCreationTest() {
        String emailValue = "test@example.com";
        Email email = new Email(emailValue);
        assertThat(email).isNotNull();
        assertThat(email.value()).isEqualTo(emailValue);
    }

    @Test
    @DisplayName("null 이나 유효하지 않은 형식의 이메일로 Email 을 생성하려 하면 오류가 발생한다.")
    void emailCreationTestWithInvalidEmail() {
        assertThatThrownBy(() -> new Email(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid email format");

        assertThatThrownBy(() -> new Email(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid email format");

        assertThatThrownBy(() -> new Email("test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid email format");

        assertThatThrownBy(() -> new Email("test@"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid email format");

        assertThatThrownBy(() -> new Email("@example.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid email format");
    }
}
