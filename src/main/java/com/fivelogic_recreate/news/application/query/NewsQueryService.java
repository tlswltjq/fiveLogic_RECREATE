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
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsQueryService {
    private final NewsQueryRepositoryPort newsQueryRepository;

    public NewsQueryResponse findById(Long id) {
        return newsQueryRepository.findQueryById(id)
                .orElseThrow(NewsNotFoundException::new);
    }

    public List<NewsQueryResponse> findByTitle(String title) {
        return newsQueryRepository.findByTitle(title);
    }

    public List<NewsQueryResponse> findByContent(String textcontent) {
        return newsQueryRepository.findByContent(textcontent);
    }

    public List<NewsQueryResponse> findByAuthorId(String authorId) {
        return newsQueryRepository.findByAuthorId(authorId);
    }

    public List<NewsQueryResponse> findByStatus(String newsStatus) {
        return newsQueryRepository.findByNewsStatus(newsStatus);
    }

    public Page<NewsQueryResponse> findByPublishedDateAfter(
            LocalDateTime publishedDate,
            Pageable pageable
    ) {
        return newsQueryRepository
                .findByPublishedDateAfter(publishedDate, pageable);
    }

    public Page<NewsQueryResponse> findByPublishedDateBefore(
            LocalDateTime publishedDate,
            Pageable pageable
    ) {
        return newsQueryRepository
                .findByPublishedDateBefore(publishedDate, pageable);
    }

    public Page<NewsQueryResponse> findByPublishedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    ) {
        return newsQueryRepository
                .findByPublishedDateBetween(startDate, endDate, pageable);
    }
}
