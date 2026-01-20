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
    private final NewsJpaRepository newsRepository;

    @Override
    public Optional<NewsQueryResponse> findQueryById(Long id) {
        return newsRepository.findQueryById(id);
    }

    @Override
    public Page<NewsQueryResponse> findByTitle(String title, Pageable pageable) {
        return newsRepository.findQueryByTitle(title, pageable);
    }

    @Override
    public Page<NewsQueryResponse> findByContent(String textContent, Pageable pageable) {
        return newsRepository.findQueryByContentContaining(textContent, pageable);
    }

    @Override
    public Page<NewsQueryResponse> findByAuthorId(String authorId, Pageable pageable) {
        return newsRepository.findQueryByAuthor_UserId(authorId, pageable);
    }

    @Override
    public Page<NewsQueryResponse> findByNewsStatus(String status, Pageable pageable) {
        NewsStatus newsStatus = NewsStatus.from(status);
        return newsRepository.findQueryByStatus(newsStatus, pageable);
    }

    @Override
    public Page<NewsQueryResponse> findByPublishedDateAfter(LocalDateTime publishedDate, Pageable pageable) {
        return newsRepository
                .findQueryByPublishedDateAfter(publishedDate, pageable);
    }

    @Override
    public Page<NewsQueryResponse> findByPublishedDateBefore(LocalDateTime publishedDate, Pageable pageable) {
        return newsRepository
                .findQueryByPublishedDateBefore(publishedDate, pageable);
    }

    @Override
    public Page<NewsQueryResponse> findByPublishedDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return newsRepository
                .findQueryByPublishedDateBetween(startDate, endDate, pageable);
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    public Optional<News> findById(NewsId id) {
        return newsRepository.findById(id.value());
    }
}
