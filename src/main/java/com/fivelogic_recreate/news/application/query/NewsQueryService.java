package com.fivelogic_recreate.news.application.query;

import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import com.fivelogic_recreate.news.domain.port.NewsQueryRepositoryPort;
import com.fivelogic_recreate.news.exception.NewsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsQueryService {
    private final NewsQueryRepositoryPort newsQueryRepository;

    public NewsQueryResponse findById(Long id) {
        return newsQueryRepository.findQueryById(id)
                .orElseThrow(NewsNotFoundException::new);
    }

    public Page<NewsQueryResponse> findByTitle(String title, Pageable pageable) {
        return newsQueryRepository.findByTitle(title, pageable);
    }

    public Page<NewsQueryResponse> findByContent(String textcontent, Pageable pageable) {
        return newsQueryRepository.findByContent(textcontent, pageable);
    }

    public Page<NewsQueryResponse> findByAuthorId(String authorId, Pageable pageable) {
        return newsQueryRepository.findByAuthorId(authorId, pageable);
    }

    public Page<NewsQueryResponse> findByStatus(String newsStatus, Pageable pageable) {
        return newsQueryRepository.findByNewsStatus(newsStatus, pageable);
    }

    public Page<NewsQueryResponse> findByPublishedDateAfter(
            LocalDateTime publishedDate,
            Pageable pageable) {
        return newsQueryRepository
                .findByPublishedDateAfter(publishedDate, pageable);
    }

    public Page<NewsQueryResponse> findByPublishedDateBefore(
            LocalDateTime publishedDate,
            Pageable pageable) {
        return newsQueryRepository
                .findByPublishedDateBefore(publishedDate, pageable);
    }

    public Page<NewsQueryResponse> findByPublishedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable) {
        return newsQueryRepository
                .findByPublishedDateBetween(startDate, endDate, pageable);
    }
}
