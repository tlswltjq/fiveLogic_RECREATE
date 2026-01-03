package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.fixture.News.NewsFixture;
import com.fivelogic_recreate.member.domain.service.MemberDomainService;
import com.fivelogic_recreate.news.application.command.dto.NewsHideCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsHideResult;
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
class NewsHideServiceTest {
    @Mock
    private NewsDomainService newsDomainService;
    @Mock
    private MemberDomainService memberDomainService;

    @InjectMocks
    private NewsHideService newsHideService;

    private final NewsFixture newsFixture = new NewsFixture();

    @Test
    @DisplayName("뉴스를 성공적으로 숨김 처리한다.")
    void shouldHideNewsSuccessfully() {
        Long newsId = 1L;
        String currentUserId = "user-1";
        NewsHideCommand command = new NewsHideCommand(newsId, currentUserId);

        News hiddenNews = newsFixture.withId(newsId).withStatus(NewsStatus.HIDDEN).build();

        when(memberDomainService.getMember(any())).thenReturn(null);
        when(newsDomainService.hide(anyLong(), anyString())).thenReturn(hiddenNews);

        NewsHideResult result = newsHideService.hideNews(command);

        verify(newsDomainService).hide(newsId, currentUserId);
        assertThat(result.status()).isEqualTo(NewsStatus.HIDDEN);
    }
}
