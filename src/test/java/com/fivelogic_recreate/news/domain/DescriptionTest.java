package com.fivelogic_recreate.news.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DescriptionTest {
    @Test
    @DisplayName("정상적으로 Description 이 생성된다.")
    void descriptionCreationTest() {
        String descriptionValue = "This is a valid description.";
        Description description = new Description(descriptionValue);
        assertThat(description).isNotNull();
        assertThat(description.value()).isEqualTo(descriptionValue);
    }

    @Test
    @DisplayName("null 로 Description 을 생성하려 하면 오류가 발생한다.")
    void descriptionCreationWithNullTest() {
        assertThatThrownBy(() -> new Description(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 뉴스 설명입니다.");
    }

    @Test
    @DisplayName("빈 문자열로 Description 을 생성하려 하면 오류가 발생한다.")
    void descriptionCreationWithEmptyStringTest() {
        assertThatThrownBy(() -> new Description(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 뉴스 설명입니다.");
    }

    @Test
    @DisplayName("공백 문자열로 Description 을 생성하려 하면 오류가 발생한다.")
    void descriptionCreationWithBlankStringTest() {
        assertThatThrownBy(() -> new Description(" "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 뉴스 설명입니다.");
    }
}
