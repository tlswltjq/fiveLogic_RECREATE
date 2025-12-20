package com.fivelogic_recreate.news.domain.port;

import com.fivelogic_recreate.news.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NewsQueryRepositoryPort {
    Optional<News> findById(Long id);
    List<News> findByTitle(String title);
    List<News> findByContent(String textContent);
    List<News> findByAuthorId(String authorId);
    List<News> findByNewsStatus(String newsStatus);
    Page<News> findByPublishedDateAfter(LocalDateTime publishedDate, Pageable pageable);
    Page<News> findByPublishedDateBefore(LocalDateTime publishedDate, Pageable pageable);
    Page<News> findByPublishedDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
