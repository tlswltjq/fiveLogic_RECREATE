package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.fixture.News.NewsFixture;
import com.fivelogic_recreate.member.domain.service.MemberDomainService;
import com.fivelogic_recreate.news.application.command.dto.NewsPublishCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsPublishResult;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsPublishServiceTest {
    @Mock
    private NewsDomainService newsDomainService;
    @Mock
    private MemberDomainService memberDomainService;

    @InjectMocks
    private NewsPublishService newsPublishService;

    private final NewsFixture newsFixture = new NewsFixture();

    @Test
    @DisplayName("뉴스를 성공적으로 발행 처리한다.")
    void shouldPublishNewsSuccessfully() {
        Long newsId = 1L;
        String currentUserId = "user-1";
        NewsPublishCommand command = new NewsPublishCommand(newsId, currentUserId);

        News publishedNews = newsFixture.withId(newsId).withStatus(NewsStatus.PUBLISHED).build();

        when(memberDomainService.getMember(any())).thenReturn(null);
        when(newsDomainService.publish(anyLong(), anyString())).thenReturn(publishedNews);

        NewsPublishResult result = newsPublishService.publishNews(command);

        verify(newsDomainService).publish(newsId, currentUserId);
        assertThat(result.status()).isEqualTo(NewsStatus.PUBLISHED);
        assertThat(result.publishedDate()).isNotNull();
    }
}
