package com.fivelogic_recreate.news.domain;

import com.fivelogic_recreate.fixture.News.NewsFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NewsTest {
    private NewsFixture newsFixture = new NewsFixture();

    @Test
    @DisplayName("News객체가 주어진 정보를 바탕으로 복원된다.")
    void shouldReturnNews() {
        NewsId id = new NewsId(1L);
        Title title = new Title("title");
        Description description = new Description("description");
        Content content = new Content("content", "video.com");
        AuthorId authorId = new AuthorId("authorId");
        LocalDateTime publishedDate = LocalDateTime.now();
        NewsStatus status = NewsStatus.DRAFT;

        News news = News.reconsitute(id, title, description, content, authorId, publishedDate, status);

        assertThat(news).isNotNull();
        assertThat(news.getId()).isEqualTo(id);
        assertThat(news.getTitle()).isEqualTo(title);
        assertThat(news.getDescription()).isEqualTo(description);
        assertThat(news.getContent().text()).isEqualTo("content");
        assertThat(news.getContent().videoUrl()).isEqualTo("video.com");
        assertThat(news.getAuthorId()).isEqualTo(authorId);
        assertThat(news.getPublishedDate()).isEqualTo(publishedDate);
        assertThat(news.getStatus()).isEqualTo(status);
    }

    @Test
    @DisplayName("초안을 작성할 수 있다.")
    void shouldReturnDraft() {
        String author = "authorId";
        String title = "title";
        String description = "description";
        String content = "content";
        String video = "videoUrl";
        News draft = News.draft(title, description, content, video, author);

        assertThat(draft).isNotNull();
        assertThat(draft.getTitle().value()).isEqualTo(title);
        assertThat(draft.getDescription().value()).isEqualTo(description);
        assertThat(draft.getContent().text()).isEqualTo(content);
        assertThat(draft.getContent().videoUrl()).isEqualTo(video);
        assertThat(draft.getAuthorId().value()).isEqualTo(author);
        assertThat(draft.getPublishedDate()).isBefore(LocalDateTime.now());
        assertThat(draft.getStatus()).isEqualTo(NewsStatus.DRAFT);
    }

    @Test
    @DisplayName("News가 READY, HIDDEN 상태이면 발행할 수 있다.")
    void shouldPublishWhenStatusIsREADYorHIDDEN() {
        News readyNews = newsFixture.withStatus(NewsStatus.READY).build();
        News hiddenNews = newsFixture.withStatus(NewsStatus.HIDDEN).build();

        readyNews.publish();
        hiddenNews.publish();

        assertThat(readyNews.getStatus()).isEqualTo(NewsStatus.PUBLISHED);
        assertThat(hiddenNews.getStatus()).isEqualTo(NewsStatus.PUBLISHED);
    }

    @Test
    @DisplayName("News가 DRAFT, PROCESSING, PUBLISHED, DELETED 상태이면 발행할 수 없다.")
    void shouldNotPublishWhenStatusIsNotREADYorHIDDEN() {
        News draftNews = newsFixture.withStatus(NewsStatus.DRAFT).build();
        News processingNews = newsFixture.withStatus(NewsStatus.PROCESSING).build();
        News publishedNews = newsFixture.withStatus(NewsStatus.PUBLISHED).build();
        News deletedNews = newsFixture.withStatus(NewsStatus.DELETED).build();

        assertThatThrownBy(draftNews::publish).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(processingNews::publish).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(publishedNews::publish).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(deletedNews::publish).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("News가 PUBLISHED 상태이면 숨김 처리할 수 있다.")
    void shouldHideWhenStatusIsPUBLISHED() {
        News publishedNews = newsFixture.withStatus(NewsStatus.PUBLISHED).build();

        publishedNews.hide();

        assertThat(publishedNews.getStatus()).isEqualTo(NewsStatus.HIDDEN);
    }

    @Test
    @DisplayName("News가 DRAFT, PROCESSING, HIDDEN, DELETED 상태이면 숨김 처리할 수 없다.")
    void shouldNotHideWhenStatusIsDRAFTorPROCESSINGorHIDDENorDELETED() {
        News draftNews = newsFixture.withStatus(NewsStatus.DRAFT).build();
        News processingNews = newsFixture.withStatus(NewsStatus.PROCESSING).build();
        News hiddenNews = newsFixture.withStatus(NewsStatus.HIDDEN).build();
        News deletedNews = newsFixture.withStatus(NewsStatus.DELETED).build();

        assertThatThrownBy(draftNews::hide).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(processingNews::hide).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(hiddenNews::hide).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(deletedNews::hide).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("News가 HIDDEN 상태이면 숨김 상태를 취소할 수 있다.")
    void shouldUnHideWhenStatusIsHIDDEN() {
        News hiddenNews = newsFixture.withStatus(NewsStatus.HIDDEN).build();

        hiddenNews.unhide();

        assertThat(hiddenNews.getStatus()).isEqualTo(NewsStatus.PUBLISHED);
    }

    @Test
    @DisplayName("News가 DRAFT, PROCESSING, PUBLISHED, DELETED 상태이면 숨김 상태를 취소할 수 없다.")
    void shouldNotUnhideWhenStatusIsDRAFTorPROCESSINGorPUBLISHEDorDELETED() {
        News draftNews = newsFixture.withStatus(NewsStatus.DRAFT).build();
        News processingNews = newsFixture.withStatus(NewsStatus.PROCESSING).build();
        News publishedNews = newsFixture.withStatus(NewsStatus.PUBLISHED).build();
        News deletedNews = newsFixture.withStatus(NewsStatus.DELETED).build();

        assertThatThrownBy(draftNews::unhide).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(processingNews::unhide).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(publishedNews::unhide).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(deletedNews::unhide).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("News가 DRAFT, PROCESSING, PUBLISHED ,HIDDEN 상태이면 삭제할 수 있다.")
    void shouldDeleteNews() {
        News draftNews = newsFixture.withStatus(NewsStatus.DRAFT).build();
        News processingNews = newsFixture.withStatus(NewsStatus.PROCESSING).build();
        News publishedNews = newsFixture.withStatus(NewsStatus.PUBLISHED).build();
        News hiddenNews = newsFixture.withStatus(NewsStatus.HIDDEN).build();

        draftNews.delete();
        processingNews.delete();
        publishedNews.delete();
        hiddenNews.delete();

        assertThat(draftNews.getStatus()).isEqualTo(NewsStatus.DELETED);
        assertThat(processingNews.getStatus()).isEqualTo(NewsStatus.DELETED);
        assertThat(publishedNews.getStatus()).isEqualTo(NewsStatus.DELETED);
        assertThat(hiddenNews.getStatus()).isEqualTo(NewsStatus.DELETED);
    }

    @Test
    @DisplayName("News가 DELETED 상태이면 삭제할 수 없다.")
    void shouldNotDeleteNews() {
        News deletedNews = newsFixture.withStatus(NewsStatus.DELETED).build();

        assertThatThrownBy(deletedNews::delete).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("News의 제목을 변경할 수 있다.")
    void shouldChangeNewsTitle() {
        News news = newsFixture.build();
        String newTitle = "new title!";

        news.changeTitle(newTitle);

        assertThat(news.getTitle().value()).isEqualTo(newTitle);
    }

    @Test
    @DisplayName("News의 설명을 변경할 수 있다.")
    void shouldChangeDescription() {
        News news = newsFixture.build();
        String newDescription = "new description!";

        news.changeDescription(newDescription);

        assertThat(news.getDescription().value()).isEqualTo(newDescription);
    }

    @Test
    @DisplayName("News의 본문을 변경할 수 있다. - 텍스트")
    void shouldChangeContentWithText() {
        News news = newsFixture.build();
        String newContent = "new content!";

        news.changeTextContent(newContent);

        assertThat(news.getContent().text()).isEqualTo(newContent);
    }

    @Test
    @DisplayName("News의 본문을 변경할 수 있다. - 영상링크")
    void shouldChangeContentWithVideoLink() {
        News news = newsFixture.build();
        String newVideoLink = "new video link!";

        news.changeVideoUrl(newVideoLink);

        assertThat(news.getContent().videoUrl()).isEqualTo(newVideoLink);
    }
}