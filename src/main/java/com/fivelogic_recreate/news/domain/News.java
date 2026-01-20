package com.fivelogic_recreate.news.domain;

import com.fivelogic_recreate.member.domain.model.UserId;
import com.fivelogic_recreate.news.exception.NewsAccessDeniedException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "News")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "news_title", nullable = false))
    private Title title;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "description", nullable = false))
    private Description description;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "content", nullable = false))
    private TextContent textContent;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "videoUrl", nullable = false))
    private VideoUrl videoUrl;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "writer_user_id", nullable = false))
    private UserId writerId;

    private LocalDateTime publishedDate;

    @Enumerated(EnumType.STRING)
    private NewsStatus status;
    // 조회수는 통계 도메인으로 분리해보자

    private News(Long id, Title title, Description description, TextContent textContent, VideoUrl videoUrl,
            UserId writerId, LocalDateTime publishedDate, NewsStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.textContent = textContent;
        this.videoUrl = videoUrl;
        this.writerId = writerId;
        this.publishedDate = publishedDate;
        this.status = status;
    }

    public static News draft(String title, String description, String content, String videoUrl, UserId writerId) {
        return new News(
                null,
                new Title(title),
                new Description(description),
                new TextContent(content),
                new VideoUrl(videoUrl),
                writerId,
                null,
                NewsStatus.DRAFT);
    }

    public void processing() {
        this.status = this.status.transitTo(NewsStatus.PROCESSING);
    }

    public void ready() {
        this.status = this.status.transitTo(NewsStatus.READY);
    }

    public void publish() {
        this.status = this.status.transitTo(NewsStatus.PUBLISHED);
        this.publishedDate = LocalDateTime.now();
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

    public void changeTitle(Title newTitle) {
        this.title = newTitle;
    }

    public void changeDescription(Description newDescription) {
        this.description = newDescription;
    }

    public void changeTextContent(TextContent newText) {
        this.textContent = newText;
    }

    public void changeVideoUrl(VideoUrl newUrl) {
        this.videoUrl = newUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        News news = (News) o;
        return Objects.equals(id, news.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void validateOwner(String requesterId) {
        if (!this.writerId.value().equals(requesterId)) {
            throw new NewsAccessDeniedException();
        }
    }
}
