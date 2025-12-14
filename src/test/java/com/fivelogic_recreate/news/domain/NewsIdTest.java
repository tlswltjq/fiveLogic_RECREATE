package com.fivelogic_recreate.news.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NewsIdTest {
    @Test
    @DisplayName("정상적으로 NewsId 가 생성된다.")
    void newsIdCreationTest() {
        Long id = 1L;
        NewsId newsId = new NewsId(id);
        assertThat(newsId).isNotNull();
        assertThat(newsId.value()).isEqualTo(id);
    }

    @Test
    @DisplayName("Null로 NewsId 를 생성하려 하면 오류가 발생한다.")
    void newsIdCreationTestWithNull() {
        assertThatThrownBy(() -> new NewsId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 ID 입니다.");
    }
}
