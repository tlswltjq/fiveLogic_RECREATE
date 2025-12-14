package com.fivelogic_recreate.news.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TitleTest {
    @Test
    @DisplayName("정상적으로 Title 이 생성된다.")
    void titleCreationTest() {
        String titleValue = "This is a valid title.";
        Title title = new Title(titleValue);
        assertThat(title).isNotNull();
        assertThat(title.value()).isEqualTo(titleValue);
    }

    @Test
    @DisplayName("null 로 Title 을 생성하려 하면 오류가 발생한다.")
    void titleCreationWithNullTest() {
        assertThatThrownBy(() -> new Title(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제목은 비워둘 수 없습니다");
    }

    @Test
    @DisplayName("빈 문자열로 Title 을 생성하려 하면 오류가 발생한다.")
    void titleCreationWithEmptyStringTest() {
        assertThatThrownBy(() -> new Title(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제목은 비워둘 수 없습니다");
    }

    @Test
    @DisplayName("최대 길이를 초과하는 문자열로 Title 을 생성하려 하면 오류가 발생한다.")
    void titleCreationWithLongStringTest() {
        String longTitle = "a".repeat(256);
        assertThatThrownBy(() -> new Title(longTitle))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("너무 긴 제목");
    }
}
