package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.command.dto.NewsPublishCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsPublishResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.service.NewsDomainService;
import com.fivelogic_recreate.news.exception.NewsPublishNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsPublishService {
    private final NewsDomainService newsDomainService;

    public NewsPublishResult publishNews(NewsPublishCommand command) {
        News news;
        try {
            news = newsDomainService.publish(command.newsId(), command.currentUserId());
        } catch (IllegalStateException e) {
            throw new NewsPublishNotAllowedException();
        }
        return new NewsPublishResult(news.getId(), news.getTitle().value(), news.getPublishedDate(), news.getStatus());
    }
}
