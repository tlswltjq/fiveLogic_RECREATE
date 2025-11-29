package com.fivelogic_recreate.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BioTest {

    @Test
    @DisplayName("정상적으로 Bio 가 생성된다.")
    void bioCreationTest() {
        String bioValue = "This is a valid bio.";
        Bio bio = new Bio(bioValue);
        assertThat(bio).isNotNull();
        assertThat(bio.value()).isEqualTo(bioValue);
    }

    @Test
    @DisplayName("null 로 Bio 를 생성할 수 있다.")
    void bioCreationWithNullTest() {
        Bio bio = new Bio(null);
        assertThat(bio).isNotNull();
        assertThat(bio.value()).isNull();
    }

    @Test
    @DisplayName("공백 문자열로 Bio 를 생성하려 하면 오류가 발생한다.")
    void bioCreationWithBlankStringTest() {
        assertThatThrownBy(() -> new Bio(" "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bio must be less than 500 characters and not blank");
    }

    @Test
    @DisplayName("최대 길이를 초과하는 문자열로 Bio 를 생성하려 하면 오류가 발생한다.")
    void bioCreationWithLongStringTest() {
        String longBio = "a".repeat(501);
        assertThatThrownBy(() -> new Bio(longBio))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bio must be less than 500 characters and not blank");
    }
}
