package com.fivelogic_recreate.news.infrastructure.persistence;

import com.fivelogic_recreate.member.infrastructure.persistence.MemberJpaEntity;
import com.fivelogic_recreate.news.domain.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "News")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String videoUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberJpaEntity author;
    private LocalDateTime publishedDate;
    @Enumerated(EnumType.STRING)
    private NewsStatus status;

    @Builder(access = AccessLevel.PRIVATE)
    public NewsJpaEntity(Long id, String title, String description, String content, String videoUrl, MemberJpaEntity author, LocalDateTime publishedDate, NewsStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.videoUrl = videoUrl;
        this.author = author;
        this.publishedDate = publishedDate;
        this.status = status;
    }

    public static NewsJpaEntity from(News news, MemberJpaEntity author) {
        return NewsJpaEntity.builder()
                .id(news.getId() != null ? news.getId().value() : null)
                .title(news.getTitle().value())
                .description(news.getDescription().value())
                .content(news.getContent().text().value())
                .videoUrl(news.getContent().videoUrl().value())
                .author(author)
                .publishedDate(news.getPublishedDate())
                .status(news.getStatus())
                .build();
    }

    public News toDomain() {
        return News.reconsitute(new NewsId(this.id), new Title(this.title), new Description(this.description), new Content(this.content, this.videoUrl), new AuthorId(this.author.getUserId()), this.publishedDate, this.status);
    }
}
