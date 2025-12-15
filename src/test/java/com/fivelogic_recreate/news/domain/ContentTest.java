package com.fivelogic_recreate.news.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentTest {
    @Test
    @DisplayName("정상적으로 Content 가 생성된다.")
    void contentCreationTest() {
        String text = "This is the content.";
        String videoUrl = "http://example.com/video.mp4";
        Content content = new Content(text, videoUrl);
        assertThat(content).isNotNull();
        assertThat(content.text().value()).isEqualTo(text);
        assertThat(content.videoUrl().value()).isEqualTo(videoUrl);
    }

    @Test
    @DisplayName("null 이거나 빈 문자열인 text 로 Content 를 생성하려 하면 오류가 발생한다.")
    void contentCreationWithInvalidTextTest() {
        String videoUrl = "http://example.com/video.mp4";
        assertThatThrownBy(() -> new Content(null, videoUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("본문은 비울 수 없습니다.");

        assertThatThrownBy(() -> new Content("", videoUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("본문은 비울 수 없습니다.");
    }

    @Test
    @DisplayName("null 이거나 빈 문자열인 videoUrl 로 Content 를 생성하려 하면 오류가 발생한다.")
    void contentCreationWithInvalidVideoUrlTest() {
        String text = "This is the content.";
        assertThatThrownBy(() -> new Content(text, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("영상 링크는 비울 수 없습니다.");

        assertThatThrownBy(() -> new Content(text, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("영상 링크는 비울 수 없습니다.");
    }
}
