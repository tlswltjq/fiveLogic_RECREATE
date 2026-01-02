package com.fivelogic_recreate.member.domain;

import com.fivelogic_recreate.member.domain.model.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {
    @Test
    @DisplayName("정상적으로 Name 이 생성된다.")
    void nameCreationTest() {
        String firstName = "first";
        String lastName = "last";
        Name name = new Name(firstName, lastName);
        assertThat(name).isNotNull();
        assertThat(name.firstName()).isEqualTo(firstName);
        assertThat(name.lastName()).isEqualTo(lastName);
    }

    @Test
    @DisplayName("null 이나 공백으로 된 firstName 으로 Name 을 생성하려 하면 오류가 발생한다.")
    void nameCreationTestWithInvalidFirstName() {
        String lastName = "last";
        assertThatThrownBy(() -> new Name(null, lastName))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Name("", lastName))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Name(" ", lastName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("null 이나 공백으로 된 lastName 으로 Name 을 생성하려 하면 오류가 발생한다.")
    void nameCreationTestWithInvalidLastName() {
        String firstName = "first";
        assertThatThrownBy(() -> new Name(firstName, null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Name(firstName, ""))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Name(firstName, " "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름과 성을 합친 문자열을 반환한다.")
    void shouldReturnFirstNameAndLastName() {
        String firstName = "first";
        String lastName = "last";
        Name name = new Name(firstName, lastName);
        assertThat(name).isNotNull();
        assertThat(name.value()).isEqualTo(firstName + " " + lastName);
    }
}
