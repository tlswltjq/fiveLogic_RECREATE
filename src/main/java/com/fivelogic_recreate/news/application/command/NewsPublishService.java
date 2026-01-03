package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.member.domain.service.MemberDomainService;
import com.fivelogic_recreate.news.application.command.dto.NewsPublishCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsPublishResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.service.NewsDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsPublishService {
    private final NewsDomainService newsDomainService;
    private final MemberDomainService memberDomainService;

    public NewsPublishResult publishNews(NewsPublishCommand command) {
        memberDomainService.getMember(command.currentUserId());

        News news = newsDomainService.publish(command.newsId(), command.currentUserId());
        return new NewsPublishResult(news.getId(), news.getTitle().value(), news.getPublishedDate(), news.getStatus());
    }
}
