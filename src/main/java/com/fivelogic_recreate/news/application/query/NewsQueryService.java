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

    /** TODO
     *  - Query 유스케이스임에도 도메인 객체(News)를 데이터 캐리어처럼 사용 중
     *  - 메서드들은 비즈니스 행위가 없으며 Aggregate Root를 통과할 필요가 없음
     *  - Projection 도입 시 toDomain() 재사용 불가 → 구조적 문제 노출
     * Action Plan
     * - NewsJpaRepositoryImpl(Query 역할)에서 Domain 반환 로직 제거
     * - Query 전용 Read Model(DTO) 도입 (e.g. NewsSummary, NewsResult 등)
     * - Infrastructure 계층에서 인터페이스 기반 Projection 적용
     * - QueryService는 Read Model을 그대로 사용 (Domain 의존 제거)
     */

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
