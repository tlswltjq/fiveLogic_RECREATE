package com.fivelogic_recreate.news.application.query;

import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import com.fivelogic_recreate.news.domain.News;
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
        NewsQueryResponse queryResponse = newsQueryRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(NewsNotFoundException::new);

        return queryResponse;
    }

    public List<NewsQueryResponse> findByTitle(String title) {
        List<NewsQueryResponse> queryResponses = newsQueryRepository.findByTitle(title).stream()
                .map(this::toResponse)
                .toList();
        return queryResponses;
    }

    public List<NewsQueryResponse> findByContent(String textcontent) {
        return newsQueryRepository.findByContent(textcontent).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<NewsQueryResponse> findByAuthorId(String authorId) {
        return newsQueryRepository.findByAuthorId(authorId).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<NewsQueryResponse> findByStatus(String newsStatus) {
        return newsQueryRepository.findByNewsStatus(newsStatus).stream()
                .map(this::toResponse)
                .toList();
    }

    public Page<NewsQueryResponse> findByPublishedDateAfter(
            LocalDateTime publishedDate,
            Pageable pageable
    ) {
        return newsQueryRepository
                .findByPublishedDateAfter(publishedDate, pageable)
                .map(this::toResponse);
    }

    public Page<NewsQueryResponse> findByPublishedDateBefore(
            LocalDateTime publishedDate,
            Pageable pageable
    ) {
        return newsQueryRepository
                .findByPublishedDateBefore(publishedDate, pageable)
                .map(this::toResponse);
    }

    public Page<NewsQueryResponse> findByPublishedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    ) {
        return newsQueryRepository
                .findByPublishedDateBetween(startDate, endDate, pageable)
                .map(this::toResponse);
    }
    private NewsQueryResponse toResponse(News n) {
        return new NewsQueryResponse(
                n.getTitle().value(),
                n.getDescription().value(),
                n.getContent().text().value(),
                n.getContent().videoUrl().value(),
                n.getAuthorId().value(),
                n.getPublishedDate(),
                n.getStatus()
        );
    }
}
