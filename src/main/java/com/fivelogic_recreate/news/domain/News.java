package com.fivelogic_recreate.news.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class News {
    private final NewsId id;
    private Title title;
    private Description description;
    private Content content;
    private AuthorId authorId;
    private LocalDateTime publishedDate;
    private NewsStatus status;
    //조회수는 통계 도메인으로 분리해보자

    private News(NewsId id, Title title, Description description, Content content, AuthorId authorId, LocalDateTime publishedDate, NewsStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.authorId = authorId;
        this.publishedDate = publishedDate;
        this.status = status;
    }

    public static News reconsitute(NewsId id, Title title, Description description, Content content, AuthorId authorId, LocalDateTime publishedDate, NewsStatus status) {
        return new News(id, title, description, content, authorId, publishedDate, status);
    }

    public static News draft(String title, String description, String content, String videoUrl, String authorId) {
        return new News(
                null,
                new Title(title),
                new Description(description),
                new Content(content, videoUrl),
                new AuthorId(authorId),
                LocalDateTime.now(),
                NewsStatus.DRAFT
        );
    }

    public void processing() {
        this.status = this.status.transitTo(NewsStatus.PROCESSING);
    }

    public void ready() {
        this.status = this.status.transitTo(NewsStatus.READY);
    }

    public void publish() {
        this.status = this.status.transitTo(NewsStatus.PUBLISHED);
    }

    public void hide() {
        this.status = this.status.transitTo(NewsStatus.HIDDEN);
    }

    public void unhide() {
        this.status = this.status.transitTo(NewsStatus.PUBLISHED);
    }

    public void delete() {
        this.status = this.status.transitTo(NewsStatus.DELETED);
    }

    public void changeTitle(String newTitle) {
        this.title = new Title(newTitle);
    }

    public void changeDescription(String newDescription) {
        this.description = new Description(newDescription);
    }

    public void changeTextContent(String newTextContent) {
        this.content = new Content(newTextContent, this.content.videoUrl());
    }

    public void changeVideoUrl(String newVideoUrl) {
        this.content = new Content(this.content.text(), newVideoUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
