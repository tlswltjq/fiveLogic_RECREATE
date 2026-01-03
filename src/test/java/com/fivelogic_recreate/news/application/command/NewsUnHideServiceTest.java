package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.fixture.News.NewsFixture;
import com.fivelogic_recreate.news.application.command.dto.NewsHideResult;
import com.fivelogic_recreate.news.application.command.dto.NewsUnHideCommand;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsStatus;
import com.fivelogic_recreate.news.domain.service.NewsDomainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsUnHideServiceTest {
    @Mock
    private NewsDomainService newsDomainService;

    @InjectMocks
    private NewsUnHideService newsUnHideService;

    private final NewsFixture newsFixture = new NewsFixture();

    @Test
    @DisplayName("뉴스를 성공적으로 숨김 해제 처리한다.")
    void shouldUnHideNewsSuccessfully() {
        Long newsId = 1L;
        String currentUserId = "user-1";
        NewsUnHideCommand command = new NewsUnHideCommand(newsId, currentUserId);

        News unHiddenNews = newsFixture.withId(newsId).withStatus(NewsStatus.PUBLISHED).build();

        when(newsDomainService.unhide(anyLong(), anyString())).thenReturn(unHiddenNews);

        NewsHideResult result = newsUnHideService.unHideNews(command);

        verify(newsDomainService).unhide(newsId, currentUserId);
        assertThat(result.status()).isEqualTo(NewsStatus.PUBLISHED);
    }
}
