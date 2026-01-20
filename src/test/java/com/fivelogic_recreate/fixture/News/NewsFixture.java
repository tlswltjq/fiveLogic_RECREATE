package com.fivelogic_recreate.fixture.News;

import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.UserId;
import com.fivelogic_recreate.news.domain.*;

import java.time.LocalDateTime;

import static org.springframework.test.util.ReflectionTestUtils.setField;

public class NewsFixture {
    private Long id = 1L;
    private String title = "Default News Title";
    private String description = "Default News Description";
    private String content = "Default News Content";
    private String videoUrl = "default-news-video-url.com";
    private UserId writerId = new UserId("default-writer-id");
    private LocalDateTime publishedDate = LocalDateTime.now();
    private NewsStatus status = NewsStatus.PUBLISHED;

    public NewsFixture withId(Long id) {
        this.id = id;
        return this;
    }

    public NewsFixture withTitle(String title) {
        this.title = title;
        return this;
    }

    public NewsFixture withDescription(String description) {
        this.description = description;
        return this;
    }

    public NewsFixture withContent(String content) {
        this.content = content;
        return this;
    }

    public NewsFixture withVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public NewsFixture withAuthor(Member author) {
        this.writerId = author.getUserId();
        return this;
    }

    public NewsFixture withWriterId(UserId writerId) {
        this.writerId = writerId;
        return this;
    }

    public NewsFixture withPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }

    public NewsFixture withStatus(NewsStatus status) {
        this.status = status;
        return this;
    }

    public NewsFixture withTextContent(String content) {
        this.content = content;
        return this;
    }

    public News build() {
        News news = News.draft(title, description, content, videoUrl, writerId);
        setField(news, "id", id);
        setField(news, "publishedDate", publishedDate);
        setField(news, "status", status);
        return news;
    }
}