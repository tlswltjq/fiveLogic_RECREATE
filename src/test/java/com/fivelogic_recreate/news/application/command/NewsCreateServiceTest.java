package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.fixture.News.NewsFixture;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsInfo;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsStatus;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsCreateServiceTest {
    @Mock
    private NewsRepositoryPort newsRepositoryPort;

    @InjectMocks
    private NewsCreateService newsCreateService;

    private final NewsFixture newsFixture = new NewsFixture();

    @Test
    @DisplayName("뉴스를 성공적으로 생성하고, 상태가 PROCESSING으로 변경된다.")
    void shouldCreateNewsAndSetStatusToProcessing() {
        NewsCreateCommand command = new NewsCreateCommand(
                "Default News Title",
                "Default News Description",
                "Default News Content",
        "default-news-video-url.com",
        "authorId"
        );
        News draftNews = newsFixture.withStatus(NewsStatus.PROCESSING).build();

        when(newsRepositoryPort.save(any(News.class))).thenReturn(draftNews);

        NewsInfo result = newsCreateService.createNews(command);

        verify(newsRepositoryPort).save(any(News.class));
        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo(command.title());
        assertThat(result.description()).isEqualTo(command.description());
        assertThat(result.textContent()).isEqualTo(command.textContent());
        assertThat(result.status()).isEqualTo(NewsStatus.PROCESSING.name());
    }
}
