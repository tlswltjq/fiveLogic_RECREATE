package com.fivelogic_recreate.news.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class News {
    private final NewsId id;
    private Title title;
    private Description description;
    private Content content;
    private Author author;
    private LocalDateTime publishedDate;
    private NewsStatus status;
    //조회수는 통계 도메인으로 분리해보자

    private News(NewsId id, Title title, Description description, Content content, Author author, LocalDateTime publishedDate, NewsStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.author = author;
        this.publishedDate = publishedDate;
        this.status = status;
    }

    public static News reconsitute(NewsId id, Title title, Description description, Content content, Author author, LocalDateTime publishedDate, NewsStatus status) {
        return new News(id, title, description, content, author, publishedDate, status);
    }
    //도메인 메서드 추가
}
