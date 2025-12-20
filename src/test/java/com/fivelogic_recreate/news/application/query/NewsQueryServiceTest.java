package com.fivelogic_recreate.news.application.query;

import com.fivelogic_recreate.fixture.News.NewsFixture;
import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import com.fivelogic_recreate.news.domain.News;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("NewsQueryService 테스트")
class NewsQueryServiceTest {
    @Mock
    private NewsQueryRepositoryPort newsRepositoryPort;

    @InjectMocks
    private NewsQueryService newsQueryService;

    private final NewsFixture newsFixture = new NewsFixture();

    @Test
    @DisplayName("아이디로 News를 조회할 수 있다.")
    void shouldFindNewsById() {
        Long id = 1L;
        News news = newsFixture.withId(id).build();
        when(newsRepositoryPort.findById(id)).thenReturn(Optional.of(news));

        NewsQueryResponse response = newsQueryService.findById(id);

        assertThat(response).isNotNull();
        assertThat(response.title()).isEqualTo(news.getTitle().value());
        verify(newsRepositoryPort).findById(id);
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 조회하면 NewsNotFoundException이 발생한다.")
    void shouldThrowException_whenNewsNotFoundById() {
        Long id = 999L;
        when(newsRepositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> newsQueryService.findById(id))
                .isInstanceOf(NewsNotFoundException.class);
        verify(newsRepositoryPort).findById(id);
    }

    @Test
    @DisplayName("제목으로 News를 검색할 수 있다.")
    void shouldFindNewsByTitle() {
        String title = "title";
        List<News> newsList = List.of(
                newsFixture.withTitle("title1").build(),
                newsFixture.withTitle("title2").build()
        );
        when(newsRepositoryPort.findByTitle(title)).thenReturn(newsList);

        List<NewsQueryResponse> response = newsQueryService.findByTitle(title);

        assertThat(response).hasSize(2);
        verify(newsRepositoryPort).findByTitle(title);
    }

    @Test
    @DisplayName("제목에 해당하는 News가 없으면 빈 리스트를 반환한다.")
    void shouldReturnEmptyList_whenNoNewsFoundByTitle() {
        String title = "none";
        when(newsRepositoryPort.findByTitle(title)).thenReturn(List.of());

        List<NewsQueryResponse> result = newsQueryService.findByTitle(title);

        assertThat(result).isEmpty();
        verify(newsRepositoryPort).findByTitle(title);
    }

    @Test
    @DisplayName("내용으로 News를 검색할 수 있다.")
    void shouldFindNewsByContent() {
        String content = "content";
        List<News> newsList = List.of(
                newsFixture.withContent("content1").build(),
                newsFixture.withContent("content2").build()
        );
        when(newsRepositoryPort.findByContent(content)).thenReturn(newsList);

        List<NewsQueryResponse> result = newsQueryService.findByContent(content);

        assertThat(result).hasSize(2);
        verify(newsRepositoryPort).findByContent(content);
    }

    @Test
    @DisplayName("내용에 해당하는 News가 없으면 빈 리스트를 반환한다.")
    void shouldReturnEmptyList_whenNoNewsFoundByContent() {
        String content = "none";
        when(newsRepositoryPort.findByContent(content)).thenReturn(List.of());

        List<NewsQueryResponse> result = newsQueryService.findByContent(content);

        assertThat(result).isEmpty();
        verify(newsRepositoryPort).findByContent(content);
    }

    @Test
    @DisplayName("작성자로 News를 검색할 수 있다.")
    void shouldFindNewsByAuthorId() {
        String authorId = "author";
        List<News> newsList = List.of(newsFixture.withAuthorId(authorId).build());
        when(newsRepositoryPort.findByAuthorId(authorId)).thenReturn(newsList);

        List<NewsQueryResponse> result = newsQueryService.findByAuthorId(authorId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).authorId()).isEqualTo(authorId);
        verify(newsRepositoryPort).findByAuthorId(authorId);
    }

    @Test
    @DisplayName("작성자에 해당하는 News가 없으면 빈 리스트를 반환한다.")
    void shouldReturnEmptyList_whenNoNewsFoundByAuthorId() {
        String authorId = "none";
        when(newsRepositoryPort.findByAuthorId(authorId)).thenReturn(List.of());

        List<NewsQueryResponse> result = newsQueryService.findByAuthorId(authorId);

        assertThat(result).isEmpty();
        verify(newsRepositoryPort).findByAuthorId(authorId);
    }

    @Test
    @DisplayName("상태로 News를 검색할 수 있다.")
    void shouldFindNewsByStatus() {
        String status = "PUBLISHED";
        List<News> newsList = List.of(
                newsFixture.withStatus(NewsStatus.PUBLISHED).build(),
                newsFixture.withStatus(NewsStatus.PUBLISHED).build()
        );
        when(newsRepositoryPort.findByNewsStatus(status)).thenReturn(newsList);

        List<NewsQueryResponse> result = newsQueryService.findByStatus(status);

        // Then
        assertThat(result).hasSize(2);
        verify(newsRepositoryPort).findByNewsStatus(status);
    }

    @Test
    @DisplayName("상태에 해당하는 News가 없으면 빈 리스트를 반환한다.")
    void shouldReturnEmptyList_whenNoNewsFoundByStatus() {
        String status = "DRAFT";
        when(newsRepositoryPort.findByNewsStatus(status)).thenReturn(List.of());

        List<NewsQueryResponse> result = newsQueryService.findByStatus(status);

        assertThat(result).isEmpty();
        verify(newsRepositoryPort).findByNewsStatus(status);
    }

    @Test
    @DisplayName("지정한 날짜 이후에 발행된 News를 페이징 조회할 수 있다.")
    void shouldFindNewsPublishedAfterDate() {
        LocalDateTime date = LocalDateTime.now();
        Pageable pageable = PageRequest.of(0, 10);
        List<News> newsList = List.of(newsFixture.build());
        Page<News> page = new PageImpl<>(newsList, pageable, newsList.size());
        when(newsRepositoryPort.findByPublishedDateAfter(date, pageable)).thenReturn(page);

        Page<NewsQueryResponse> result = newsQueryService.findByPublishedDateAfter(date, pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).hasSize(1);
        verify(newsRepositoryPort).findByPublishedDateAfter(date, pageable);
    }

    @Test
    @DisplayName("지정한 날짜 이전에 발행된 News를 페이징 조회할 수 있다.")
    void shouldFindNewsPublishedBeforeDate() {
        LocalDateTime date = LocalDateTime.now();
        Pageable pageable = PageRequest.of(0, 10);
        Page<News> page = Page.empty(pageable);
        when(newsRepositoryPort.findByPublishedDateBefore(date, pageable)).thenReturn(page);

        Page<NewsQueryResponse> result = newsQueryService.findByPublishedDateBefore(date, pageable);

        assertThat(result).isEmpty();
        verify(newsRepositoryPort).findByPublishedDateBefore(date, pageable);
    }

    @Test
    @DisplayName("지정한 기간 내에 발행된 News를 페이징 조회할 수 있다.")
    void shouldFindNewsPublishedBetweenDates() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        Pageable pageable = PageRequest.of(0, 10);
        List<News> newsList = List.of(newsFixture.build());
        Page<News> page = new PageImpl<>(newsList, pageable, newsList.size());
        when(newsRepositoryPort.findByPublishedDateBetween(start, end, pageable)).thenReturn(page);

        Page<NewsQueryResponse> result = newsQueryService.findByPublishedDateBetween(start, end, pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        verify(newsRepositoryPort).findByPublishedDateBetween(start, end, pageable);
    }
}
