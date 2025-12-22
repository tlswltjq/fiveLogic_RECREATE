package com.fivelogic_recreate.news.domain.port;

import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NewsQueryRepositoryPort {
    Optional<NewsQueryResponse> findQueryById(Long id);
    List<NewsQueryResponse> findByTitle(String title);
    List<NewsQueryResponse> findByContent(String textContent);
    List<NewsQueryResponse> findByAuthorId(String authorId);
    List<NewsQueryResponse> findByNewsStatus(String newsStatus);
    Page<NewsQueryResponse> findByPublishedDateAfter(LocalDateTime publishedDate, Pageable pageable);
    Page<NewsQueryResponse> findByPublishedDateBefore(LocalDateTime publishedDate, Pageable pageable);
    Page<NewsQueryResponse> findByPublishedDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
