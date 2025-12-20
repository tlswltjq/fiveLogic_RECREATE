package com.fivelogic_recreate.news.infrastructure.persistence;

import com.fivelogic_recreate.news.domain.NewsStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsJpaEntityRepository extends JpaRepository<NewsJpaEntity, Long> {
    List<NewsJpaEntity> findByTitle(String title);
    List<NewsJpaEntity> findByContentContaining(String keyword);
    List<NewsJpaEntity> findByAuthor_UserId(String authorUserId);
    List<NewsJpaEntity> findByStatus(NewsStatus status);
    Page<NewsJpaEntity> findByPublishedDateAfter(LocalDateTime publishedDate, Pageable pageable);
    Page<NewsJpaEntity> findByPublishedDateBefore(LocalDateTime publishedDate, Pageable pageable);
    Page<NewsJpaEntity> findByPublishedDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}

