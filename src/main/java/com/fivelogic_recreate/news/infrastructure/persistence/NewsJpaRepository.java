package com.fivelogic_recreate.news.infrastructure.persistence;

import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

import java.util.Optional;

public interface NewsJpaRepository extends JpaRepository<News, Long> {
        @Query("SELECT n.id as id, n.title.value AS title, n.description.value AS description, n.textContent.value AS content, n.videoUrl.value AS videoUrl, n.author.userId.value AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM News n WHERE n.id = :id")
        Optional<NewsQueryResponse> findQueryById(@Param("id") Long id);

        @Query("SELECT n.id as id, n.title.value AS title, n.description.value AS description, n.textContent.value AS content, n.videoUrl.value AS videoUrl, n.author.userId.value AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM News n WHERE n.title.value = :title")
        Page<NewsQueryResponse> findQueryByTitle(@Param("title") String title, Pageable pageable);

        @Query("SELECT n.id as id, n.title.value AS title, n.description.value AS description, n.textContent.value AS content, n.videoUrl.value AS videoUrl, n.author.userId.value AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM News n WHERE n.textContent.value LIKE %:keyword%")
        Page<NewsQueryResponse> findQueryByContentContaining(@Param("keyword") String keyword, Pageable pageable);

        @Query("SELECT n.id as id, n.title.value AS title, n.description.value AS description, n.textContent.value AS content, n.videoUrl.value AS videoUrl, n.author.userId.value AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM News n WHERE n.author.userId.value = :authorUserId")
        Page<NewsQueryResponse> findQueryByAuthor_UserId(@Param("authorUserId") String authorUserId, Pageable pageable);

        @Query("SELECT n.id as id, n.title.value AS title, n.description.value AS description, n.textContent.value AS content, n.videoUrl.value AS videoUrl, n.author.userId.value AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM News n WHERE n.status = :status")
        Page<NewsQueryResponse> findQueryByStatus(@Param("status") NewsStatus status, Pageable pageable);

        @Query("SELECT n.id as id, n.title.value AS title, n.description.value AS description, n.textContent.value AS content, n.videoUrl.value AS videoUrl, n.author.userId.value AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM News n WHERE n.publishedDate > :publishedDate")
        Page<NewsQueryResponse> findQueryByPublishedDateAfter(@Param("publishedDate") LocalDateTime publishedDate,
                        Pageable pageable);

        @Query("SELECT n.id as id, n.title.value AS title, n.description.value AS description, n.textContent.value AS content, n.videoUrl.value AS videoUrl, n.author.userId.value AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM News n WHERE n.publishedDate < :publishedDate")
        Page<NewsQueryResponse> findQueryByPublishedDateBefore(@Param("publishedDate") LocalDateTime publishedDate,
                        Pageable pageable);

        @Query("SELECT n.id as id, n.title.value AS title, n.description.value AS description, n.textContent.value AS content, n.videoUrl.value AS videoUrl, n.author.userId.value AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM News n WHERE n.publishedDate BETWEEN :startDate AND :endDate")
        Page<NewsQueryResponse> findQueryByPublishedDateBetween(@Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate, Pageable pageable);
}
