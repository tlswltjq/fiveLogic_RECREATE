package com.fivelogic_recreate.news.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("NewsStatus 테스트")
class NewsStatusTest {
    @Nested
    @DisplayName("DRAFT 상태에서")
    class DraftStatus {
        @Test
        @DisplayName("PROCESSING 또는 DELETED 로 변경할 수 있다.")
        void shouldTransitToProcessingOrDeleted() {
            assertThat(NewsStatus.DRAFT.transitTo(NewsStatus.PROCESSING)).isEqualTo(NewsStatus.PROCESSING);
            assertThat(NewsStatus.DRAFT.transitTo(NewsStatus.DELETED)).isEqualTo(NewsStatus.DELETED);
        }

        @ParameterizedTest
        @EnumSource(value = NewsStatus.class, names = {"DRAFT", "READY", "PUBLISHED", "HIDDEN"})
        @DisplayName("PROCESSING 또는 DELETED 가 아닌 상태로 변경할 수 없다.")
        void shouldNotTransitToOtherStatus(NewsStatus target) {
            assertThatThrownBy(() -> NewsStatus.DRAFT.transitTo(target))
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    @DisplayName("PROCESSING 상태에서")
    class ProcessingStatus {
        @Test
        @DisplayName("READY, DRAFT, DELETED 로 변경할 수 있다.")
        void shouldTransitToReadyOrDraftOrDeleted() {
            assertThat(NewsStatus.PROCESSING.transitTo(NewsStatus.READY)).isEqualTo(NewsStatus.READY);
            assertThat(NewsStatus.PROCESSING.transitTo(NewsStatus.DRAFT)).isEqualTo(NewsStatus.DRAFT);
            assertThat(NewsStatus.PROCESSING.transitTo(NewsStatus.DELETED)).isEqualTo(NewsStatus.DELETED);
        }

        @ParameterizedTest
        @EnumSource(value = NewsStatus.class, names = {"PROCESSING", "PUBLISHED", "HIDDEN"})
        @DisplayName("READY, DRAFT, DELETED 가 아닌 상태로 변경할 수 없다.")
        void shouldNotTransitToOtherStatus(NewsStatus target) {
            assertThatThrownBy(() -> NewsStatus.PROCESSING.transitTo(target))
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    @DisplayName("READY 상태에서")
    class ReadyStatus {
        @Test
        @DisplayName("PUBLISHED 또는 DELETED 로 변경할 수 있다.")
        void shouldTransitToPublishedOrDeleted() {
            assertThat(NewsStatus.READY.transitTo(NewsStatus.PUBLISHED)).isEqualTo(NewsStatus.PUBLISHED);
            assertThat(NewsStatus.READY.transitTo(NewsStatus.DELETED)).isEqualTo(NewsStatus.DELETED);
        }

        @ParameterizedTest
        @EnumSource(value = NewsStatus.class, names = {"DRAFT", "PROCESSING", "READY", "HIDDEN"})
        @DisplayName("PUBLISHED 또는 DELETED 가 아닌 상태로 변경할 수 없다.")
        void shouldNotTransitToOtherStatus(NewsStatus target) {
            assertThatThrownBy(() -> NewsStatus.READY.transitTo(target))
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    @DisplayName("PUBLISHED 상태에서")
    class PublishedStatus {
        @Test
        @DisplayName("HIDDEN 또는 DELETED 로 변경할 수 있다.")
        void shouldTransitToHiddenOrDeleted() {
            assertThat(NewsStatus.PUBLISHED.transitTo(NewsStatus.HIDDEN)).isEqualTo(NewsStatus.HIDDEN);
            assertThat(NewsStatus.PUBLISHED.transitTo(NewsStatus.DELETED)).isEqualTo(NewsStatus.DELETED);
        }

        @ParameterizedTest
        @EnumSource(value = NewsStatus.class, names = {"DRAFT", "PROCESSING", "READY", "PUBLISHED"})
        @DisplayName("HIDDEN 또는 DELETED 가 아닌 상태로 변경할 수 없다.")
        void shouldNotTransitToOtherStatus(NewsStatus target) {
            assertThatThrownBy(() -> NewsStatus.PUBLISHED.transitTo(target))
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    @DisplayName("HIDDEN 상태에서")
    class HiddenStatus {
        @Test
        @DisplayName("PUBLISHED 또는 DELETED 로 변경할 수 있다.")
        void shouldTransitToPublishedOrDeleted() {
            assertThat(NewsStatus.HIDDEN.transitTo(NewsStatus.PUBLISHED)).isEqualTo(NewsStatus.PUBLISHED);
            assertThat(NewsStatus.HIDDEN.transitTo(NewsStatus.DELETED)).isEqualTo(NewsStatus.DELETED);
        }

        @ParameterizedTest
        @EnumSource(value = NewsStatus.class, names = {"DRAFT", "PROCESSING", "READY", "HIDDEN"})
        @DisplayName("PUBLISHED 또는 DELETED 가 아닌 상태로 변경할 수 없다.")
        void shouldNotTransitToOtherStatus(NewsStatus target) {
            assertThatThrownBy(() -> NewsStatus.HIDDEN.transitTo(target))
                    .isInstanceOf(IllegalStateException.class);
        }
    }
    
    @Nested
    @DisplayName("DELETED 상태에서")
    class DeletedStatus {
        @Test
        @DisplayName("DRAFT로 변경할 수 있다.")
        void shouldTransitToDraftProcessingReady() {
            assertThat(NewsStatus.DELETED.transitTo(NewsStatus.DRAFT)).isEqualTo(NewsStatus.DRAFT);
        }

        @ParameterizedTest
        @EnumSource(value = NewsStatus.class, names = {"PUBLISHED", "HIDDEN", "DELETED"})
        @DisplayName("DRAFT, PROCESSING, READY 가 아닌 상태로 변경할 수 없다.")
        void shouldNotTransitToOtherStatus(NewsStatus target) {
            assertThatThrownBy(() -> NewsStatus.DELETED.transitTo(target))
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    @DisplayName("from() 메서드")
    class FromMethod {
        @ParameterizedTest
        @EnumSource(NewsStatus.class)
        @DisplayName("유효한 문자열로부터 NewsStatus를 생성할 수 있다.")
        void shouldCreateNewsStatusFromValidString(NewsStatus status) {
            assertThat(NewsStatus.from(status.name())).isEqualTo(status);
        }

        @Test
        @DisplayName("유효하지 않은 문자열로부터 NewsStatus를 생성하려고 하면 예외가 발생한다.")
        void shouldThrowExceptionForInvalidString() {
            String invalidStatusString = "INVALID_STATUS";
            assertThatThrownBy(() -> NewsStatus.from(invalidStatusString))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("유효하지 않은 상태입니다: " + invalidStatusString);
        }
    }
}
