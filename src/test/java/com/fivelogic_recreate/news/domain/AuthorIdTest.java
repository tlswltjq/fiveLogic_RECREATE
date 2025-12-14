package com.fivelogic_recreate.news.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthorIdTest {
    @Test
    @DisplayName("정상적으로 AuthorId 가 생성된다.")
    void authorCreationTest() {
        String authorValue = "authorId";
        AuthorId authorId = new AuthorId(authorValue);
        assertThat(authorId).isNotNull();
        assertThat(authorId.value()).isEqualTo(authorValue);
    }

    @Test
    @DisplayName("null 로 AuthorId 를 생성하려 하면 오류가 발생한다.")
    void authorCreationTestWithNull() {
        assertThatThrownBy(() -> new AuthorId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("작성자는 비워둘 수 없습니다.");
    }

    @Test
    @DisplayName("빈 문자열로 AuthorId 를 생성하려 하면 오류가 발생한다.")
    void authorCreationTestWithEmptyString() {
        assertThatThrownBy(() -> new AuthorId(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("작성자는 비워둘 수 없습니다.");
    }

    @Test
    @DisplayName("공백 문자열로 AuthorId 를 생성하려 하면 오류가 발생한다.")
    void authorCreationTestWithBlankString() {
        assertThatThrownBy(() -> new AuthorId(" "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("작성자는 비워둘 수 없습니다.");
    }
}
