package com.fivelogic_recreate.news.interfaces.rest;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;
import com.fivelogic_recreate.common.rest.ApiResponse;
import com.fivelogic_recreate.news.application.command.*;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateResult;
import com.fivelogic_recreate.news.application.command.dto.NewsDeleteCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsUpdateResult;
import com.fivelogic_recreate.news.application.query.GetNewsDetail;
import com.fivelogic_recreate.news.application.query.GetNewsList;
import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import com.fivelogic_recreate.news.domain.NewsStatus;
import com.fivelogic_recreate.news.interfaces.rest.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NewsControllerTest {
    @InjectMocks
    private NewsController newsController;

    @Mock
    private RegisterNews registerNews;
    @Mock
    private EditNews editNews;
    @Mock
    private PublishNews publishNews;
    @Mock
    private HideNews hideNews;
    @Mock
    private UnhideNews unhideNews;
    @Mock
    private RemoveNews removeNews;
    @Mock
    private GetNewsDetail getNewsDetail;
    @Mock
    private GetNewsList getNewsList;

    @Test
    @DisplayName("뉴스 생성 요청(POST /api/news)이 성공해야 한다")
    void createNews_success() {
        // given
        CreateNewsRequest request = new CreateNewsRequest("title", "desc", "content", "videoUrl");
        NewsCreateResult result = new NewsCreateResult(1L, "title", "testuser", NewsStatus.DRAFT);
        UserDetails user = mock(UserDetails.class);
        given(user.getUsername()).willReturn("testuser");

        given(registerNews.createNews(any())).willReturn(result);

        // when
        ApiResponse<CreateNewsResponse> response = newsController.createNews(user, request);

        // then
        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(response.getData().id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("뉴스 수정 요청(PUT /api/news/{newsId})이 성공해야 한다")
    void updateNews_success() {
        // given
        UpdateNewsRequest request = new UpdateNewsRequest("newTitle", "newDesc", "newContent", "newVideoUrl");
        NewsUpdateResult result = new NewsUpdateResult(1L, "newTitle", "newDesc", "newContent", "newVideoUrl",
                NewsStatus.DRAFT);
        UserDetails user = mock(UserDetails.class);
        given(user.getUsername()).willReturn("testuser");

        given(editNews.updateNews(any())).willReturn(result);

        // when
        ApiResponse<UpdateNewsResponse> response = newsController.updateNews(1L, user, request);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().title()).isEqualTo("newTitle");
    }

    @Test
    @DisplayName("뉴스 발행 요청(POST /api/news/{newsId}/publish)이 성공해야 한다")
    void publishNews_success() {
        // given
        PublishNewsRequest request = new PublishNewsRequest();
        UserDetails user = mock(UserDetails.class);
        given(user.getUsername()).willReturn("testuser");

        // when
        ApiResponse<Void> response = newsController.publishNews(1L, user, request);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        verify(publishNews).publishNews(any());
    }

    @Test
    @DisplayName("뉴스 숨김 요청(POST /api/news/{newsId}/hide)이 성공해야 한다")
    void hideNews_success() {
        // given
        HideNewsRequest request = new HideNewsRequest();
        UserDetails user = mock(UserDetails.class);
        given(user.getUsername()).willReturn("testuser");

        // when
        ApiResponse<Void> response = newsController.hideNews(1L, user, request);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        verify(hideNews).hideNews(any());
    }

    @Test
    @DisplayName("뉴스 숨김 해제 요청(POST /api/news/{newsId}/unhide)이 성공해야 한다")
    void unhideNews_success() {
        // given
        UnhideNewsRequest request = new UnhideNewsRequest();
        UserDetails user = mock(UserDetails.class);
        given(user.getUsername()).willReturn("testuser");

        // when
        ApiResponse<Void> response = newsController.unhideNews(1L, user, request);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        verify(unhideNews).unHideNews(any());
    }

    @Test
    @DisplayName("뉴스 삭제 요청(DELETE /api/news/{newsId})이 성공해야 한다")
    void deleteNews_success() {
        // given
        UserDetails user = mock(UserDetails.class);
        given(user.getUsername()).willReturn("testuser");

        // when
        ApiResponse<Void> response = newsController.deleteNews(1L, user);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        verify(removeNews).deleteNews(any(NewsDeleteCommand.class));
    }

    @Test
    @DisplayName("뉴스 상세 조회(GET /api/news/{newsId})가 성공해야 한다")
    void getNews_success() {
        // given
        NewsQueryResponse result = mock(NewsQueryResponse.class);
        given(result.getTitle()).willReturn("title");
        given(getNewsDetail.findById(1L)).willReturn(result);

        // when
        ApiResponse<GetNewsResponse> response = newsController.getNews(1L);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().news().getTitle()).isEqualTo("title");
    }

    @Test
    @DisplayName("뉴스 목록 조회(GET /api/news)가 성공해야 한다")
    void getNewsList_success() {
        // given
        Page<NewsQueryResponse> page = Page.empty();
        given(getNewsList.findByPublishedDateBefore(any(), any())).willReturn(page);

        // when
        ApiResponse<GetNewsListResponse> response = newsController.getNewsList(Pageable.unpaged());

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().newsList()).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 뉴스 조회 시 에러를 던져야 한다")
    void getNews_fail_notFound() {
        // given
        given(getNewsDetail.findById(any())).willThrow(new BusinessException(ErrorCode.NEWS_NOT_FOUND));

        // when & then
        assertThatThrownBy(() -> newsController.getNews(999L))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.NEWS_NOT_FOUND);
    }
}
