package com.fivelogic_recreate.news.infrastructure.persistence;

import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsId;
import com.fivelogic_recreate.news.domain.NewsStatus;
import com.fivelogic_recreate.news.domain.port.NewsQueryRepositoryPort;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NewsRepositoryAdapter implements NewsRepositoryPort, NewsQueryRepositoryPort {
    private final NewsJpaRepository repository;

    @Override
    public News save(News news) {
        return repository.save(news);
    }

    @Override
    public Optional<News> findById(NewsId id) {
        return repository.findById(id.value());
    }

    @Override
    public Optional<NewsQueryResponse> findQueryById(Long id) {
        return repository.findQueryById(id);
    }

    @Override
    public Page<NewsQueryResponse> findByTitle(String title, Pageable pageable) {
        return repository.findQueryByTitle(title, pageable);
    }

    @Override
    public Page<NewsQueryResponse> findByContent(String textContent, Pageable pageable) {
        return repository.findQueryByContentContaining(textContent, pageable);
    }

    @Override
    public Page<NewsQueryResponse> findByAuthorId(String authorId, Pageable pageable) {
        return repository.findQueryByAuthor_UserId(authorId, pageable);
    }

    @Override
    public Page<NewsQueryResponse> findByNewsStatus(String newsStatus, Pageable pageable) {
        return repository.findQueryByStatus(NewsStatus.valueOf(newsStatus), pageable);
    }

    @Override
    public Page<NewsQueryResponse> findByPublishedDateAfter(LocalDateTime publishedDate, Pageable pageable) {
        return repository.findQueryByPublishedDateAfter(publishedDate, pageable);
    }

    @Override
    public Page<NewsQueryResponse> findByPublishedDateBefore(LocalDateTime publishedDate, Pageable pageable) {
        return repository.findQueryByPublishedDateBefore(publishedDate, pageable);
    }

    @Override
    public Page<NewsQueryResponse> findByPublishedDateBetween(LocalDateTime startDate, LocalDateTime endDate,
            Pageable pageable) {
        return repository.findQueryByPublishedDateBetween(startDate, endDate, pageable);
    }
}
