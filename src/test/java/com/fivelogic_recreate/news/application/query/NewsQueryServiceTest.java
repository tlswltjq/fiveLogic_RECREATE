package com.fivelogic_recreate.news.application.query;

import com.fivelogic_recreate.fixture.News.TestNewsQueryResponse;
import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import com.fivelogic_recreate.news.domain.NewsStatus;
import com.fivelogic_recreate.news.domain.port.NewsQueryRepositoryPort;
import com.fivelogic_recreate.news.exception.NewsNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("NewsQueryService 테스트")
class NewsQueryServiceTest {
    @Mock
    private NewsQueryRepositoryPort newsRepositoryPort;

    @InjectMocks
    private NewsQueryService newsQueryService;

    @DisplayName("ID로 뉴스를 조회한다.")
    @Test
    void findById() {
        Long newsId = 1L;
        LocalDateTime now = LocalDateTime.now();
        NewsQueryResponse mockResponse = new TestNewsQueryResponse(
                "Test Title",
                "Test Description",
                "Test Content",
                "http://test.video.url",
                "author123",
                now,
                NewsStatus.PUBLISHED);

        when(newsRepositoryPort.findQueryById(newsId)).thenReturn(Optional.of(mockResponse));

        NewsQueryResponse result = newsQueryService.findById(newsId);

        assertThat(result.getTitle()).isEqualTo("Test Title");
        assertThat(result.getDescription()).isEqualTo("Test Description");
        assertThat(result.getContent()).isEqualTo("Test Content");
        assertThat(result.getVideoUrl()).isEqualTo("http://test.video.url");
        assertThat(result.getAuthorId()).isEqualTo("author123");
        assertThat(result.getPublishedDate()).isEqualTo(now);
        assertThat(result.getStatus()).isEqualTo(NewsStatus.PUBLISHED);

        verify(newsRepositoryPort).findQueryById(newsId);
    }

    @DisplayName("ID로 뉴스를 찾을 수 없으면 예외를 던진다.")
    @Test
    void findById_notFound() {
        Long newsId = 1L;
        when(newsRepositoryPort.findQueryById(newsId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> newsQueryService.findById(newsId))
                .isInstanceOf(NewsNotFoundException.class);
        verify(newsRepositoryPort).findQueryById(newsId);
    }

    @DisplayName("제목으로 뉴스를 조회한다.")
    @Test
    void findByTitle() {
        String title = "Test Title";
        Pageable pageable = Pageable.ofSize(10);
        NewsQueryResponse mockResponse = new TestNewsQueryResponse(
                title, "desc", "content", "url", "author", LocalDateTime.now(), NewsStatus.PUBLISHED);

        when(newsRepositoryPort.findByTitle(title, pageable))
                .thenReturn(new PageImpl<>(List.of(mockResponse), pageable, 1));

        Page<NewsQueryResponse> result = newsQueryService.findByTitle(title, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo(title);
        verify(newsRepositoryPort).findByTitle(title, pageable);
    }

    @DisplayName("내용으로 뉴스를 조회한다.")
    @Test
    void findByContent() {
        String content = "Test Content";
        Pageable pageable = Pageable.ofSize(10);
        NewsQueryResponse mockResponse = new TestNewsQueryResponse(
                "title", "desc", content, "url", "author", LocalDateTime.now(), NewsStatus.PUBLISHED);

        when(newsRepositoryPort.findByContent(content, pageable))
                .thenReturn(new PageImpl<>(List.of(mockResponse), pageable, 1));

        Page<NewsQueryResponse> result = newsQueryService.findByContent(content, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getContent()).isEqualTo(content);
        verify(newsRepositoryPort).findByContent(content, pageable);
    }

    @DisplayName("작성자 ID로 뉴스를 조회한다.")
    @Test
    void findByAuthorId() {
        String authorId = "author123";
        Pageable pageable = Pageable.ofSize(10);
        NewsQueryResponse mockResponse = new TestNewsQueryResponse(
                "title", "desc", "content", "url", authorId, LocalDateTime.now(), NewsStatus.PUBLISHED);

        when(newsRepositoryPort.findByAuthorId(authorId, pageable))
                .thenReturn(new PageImpl<>(List.of(mockResponse), pageable, 1));

        Page<NewsQueryResponse> result = newsQueryService.findByAuthorId(authorId, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getAuthorId()).isEqualTo(authorId);
        verify(newsRepositoryPort).findByAuthorId(authorId, pageable);
    }

    @DisplayName("뉴스 상태로 뉴스를 조회한다.")
    @Test
    void findByStatus() {
        String status = NewsStatus.PUBLISHED.name();
        Pageable pageable = Pageable.ofSize(10);
        NewsQueryResponse mockResponse = new TestNewsQueryResponse(
                "title", "desc", "content", "url", "author", LocalDateTime.now(), NewsStatus.PUBLISHED);

        when(newsRepositoryPort.findByNewsStatus(status, pageable))
                .thenReturn(new PageImpl<>(List.of(mockResponse), pageable, 1));

        Page<NewsQueryResponse> result = newsQueryService.findByStatus(status, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getStatus()).isEqualTo(NewsStatus.PUBLISHED);
        verify(newsRepositoryPort).findByNewsStatus(status, pageable);
    }

    @DisplayName("특정 발행일 이후의 뉴스를 페이지네이션하여 조회한다.")
    @Test
    void findByPublishedDateAfter() {
        LocalDateTime publishedDate = LocalDateTime.now().minusDays(1);
        Pageable pageable = Pageable.ofSize(10);
        NewsQueryResponse mockResponse = new TestNewsQueryResponse(
                "Title", "desc", "content", "url", "author", LocalDateTime.now(), NewsStatus.PUBLISHED);

        when(newsRepositoryPort.findByPublishedDateAfter(publishedDate, pageable))
                .thenReturn(new PageImpl<>(List.of(mockResponse), pageable, 1));

        var result = newsQueryService.findByPublishedDateAfter(publishedDate, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Title");
        verify(newsRepositoryPort).findByPublishedDateAfter(publishedDate, pageable);
    }

    @DisplayName("특정 발행일 이전의 뉴스를 페이지네이션하여 조회한다.")
    @Test
    void findByPublishedDateBefore() {
        LocalDateTime publishedDate = LocalDateTime.now().plusDays(1);
        Pageable pageable = Pageable.ofSize(10);
        NewsQueryResponse mockResponse = new TestNewsQueryResponse(
                "Title", "desc", "content", "url", "author", LocalDateTime.now(), NewsStatus.PUBLISHED);
        when(newsRepositoryPort.findByPublishedDateBefore(publishedDate, pageable))
                .thenReturn(new PageImpl<>(List.of(mockResponse), pageable, 1));

        var result = newsQueryService.findByPublishedDateBefore(publishedDate, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Title");
        verify(newsRepositoryPort).findByPublishedDateBefore(publishedDate, pageable);
    }

    @DisplayName("특정 발행일 범위 내의 뉴스를 페이지네이션하여 조회한다.")
    @Test
    void findByPublishedDateBetween() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(5);
        LocalDateTime endDate = LocalDateTime.now().minusDays(1);
        Pageable pageable = Pageable.ofSize(10);
        NewsQueryResponse mockResponse = new TestNewsQueryResponse(
                "Title", "desc", "content", "url", "author", LocalDateTime.now(), NewsStatus.PUBLISHED);
        when(newsRepositoryPort.findByPublishedDateBetween(startDate, endDate, pageable))
                .thenReturn(new PageImpl<>(List.of(mockResponse), pageable, 1));

        var result = newsQueryService.findByPublishedDateBetween(startDate, endDate, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Title");
        verify(newsRepositoryPort).findByPublishedDateBetween(startDate, endDate, pageable);
    }
}