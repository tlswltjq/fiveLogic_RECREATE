package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.command.dto.NewsHideCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsHideResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.service.NewsDomainService;
import com.fivelogic_recreate.news.exception.NewsHideNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsHideService {
    private final NewsDomainService newsDomainService;

    public NewsHideResult hideNews(NewsHideCommand command) {
        News news;
        try {
            news = newsDomainService.hide(command.newsId(), command.currentUserId());
        } catch (IllegalStateException e) {
            throw new NewsHideNotAllowedException();
        }
        return new NewsHideResult(news.getId(), news.getTitle().value(), news.getStatus());
    }
}
