package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.fixture.News.NewsFixture;
import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.UserId;
import com.fivelogic_recreate.member.domain.port.MemberQueryRepositoryPort;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsStatus;
import com.fivelogic_recreate.news.domain.service.NewsDomainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsCreateServiceTest {
    @Mock
    private NewsDomainService newsDomainService;

    @Mock
    private MemberQueryRepositoryPort memberQueryRepositoryPort;

    @InjectMocks
    private NewsCreateService newsCreateService;

    private final NewsFixture newsFixture = new NewsFixture();
    private final MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("뉴스를 성공적으로 생성하고, 상태가 PROCESSING으로 변경된다.")
    void shouldCreateNewsAndSetStatusToProcessing() {
        Member author = memberFixture.build();
        NewsCreateCommand command = new NewsCreateCommand(
                "Default News Title",
                "Default News Description",
                "Default News Content",
                "default-news-video-url.com",
                author.getUserId().value());
        News draftNews = newsFixture.withStatus(NewsStatus.PROCESSING).withId(1L).withAuthor(author).build();

        when(memberQueryRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.of(author));
        when(newsDomainService.create(any(News.class))).thenReturn(draftNews);

        NewsCreateResult result = newsCreateService.createNews(command);

        verify(newsDomainService).create(any(News.class));
        assertThat(result).isNotNull();
        assertThat(result.newsId()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo(command.title());
        assertThat(result.authorId()).isEqualTo(command.authorId());
        assertThat(result.status()).isEqualTo(NewsStatus.PROCESSING);
    }
}
