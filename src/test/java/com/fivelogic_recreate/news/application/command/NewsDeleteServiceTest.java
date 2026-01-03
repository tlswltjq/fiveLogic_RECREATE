package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.fixture.News.NewsFixture;
import com.fivelogic_recreate.member.domain.service.MemberDomainService;
import com.fivelogic_recreate.news.application.command.dto.NewsDeleteCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsDeleteResult;
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
class NewsDeleteServiceTest {
    @Mock
    private NewsDomainService newsDomainService;
    @Mock
    private MemberDomainService memberDomainService;

    @InjectMocks
    private NewsDeleteService newsDeleteService;

    private final NewsFixture newsFixture = new NewsFixture();

    @Test
    @DisplayName("뉴스를 성공적으로 삭제 처리한다.")
    void shouldDeleteNewsSuccessfully() {
        Long newsId = 1L;
        String currentUserId = "user-1";
        NewsDeleteCommand command = new NewsDeleteCommand(newsId, currentUserId);

        News deletedNews = newsFixture.withId(newsId).withStatus(NewsStatus.DELETED).build();

        when(memberDomainService.getMember(any())).thenReturn(null);
        when(newsDomainService.delete(anyLong(), anyString())).thenReturn(deletedNews);

        NewsDeleteResult result = newsDeleteService.deleteNews(command);

        verify(newsDomainService).delete(newsId, currentUserId);
        assertThat(result.status()).isEqualTo(NewsStatus.DELETED);
    }
}
