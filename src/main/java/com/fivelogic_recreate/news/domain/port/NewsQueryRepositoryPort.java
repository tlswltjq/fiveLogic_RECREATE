package com.fivelogic_recreate.news.domain.port;

import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface NewsQueryRepositoryPort {
    Optional<NewsQueryResponse> findQueryById(Long id);

    Page<NewsQueryResponse> findByTitle(String title, Pageable pageable);

    Page<NewsQueryResponse> findByContent(String textContent, Pageable pageable);

    Page<NewsQueryResponse> findByAuthorId(String authorId, Pageable pageable);

    Page<NewsQueryResponse> findByNewsStatus(String newsStatus, Pageable pageable);

    Page<NewsQueryResponse> findByPublishedDateAfter(LocalDateTime publishedDate, Pageable pageable);

    Page<NewsQueryResponse> findByPublishedDateBefore(LocalDateTime publishedDate, Pageable pageable);

    Page<NewsQueryResponse> findByPublishedDateBetween(LocalDateTime startDate, LocalDateTime endDate,
                                                       Pageable pageable);
}
