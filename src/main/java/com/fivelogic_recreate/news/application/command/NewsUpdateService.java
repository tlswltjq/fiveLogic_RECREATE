package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.member.domain.service.MemberDomainService;
import com.fivelogic_recreate.news.application.command.dto.NewsUpdateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsUpdateResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.service.NewsDomainService;
import com.fivelogic_recreate.news.domain.service.dto.NewsUpdateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsUpdateService {
    private final NewsDomainService newsDomainService;
    private final MemberDomainService memberDomainService;

    public NewsUpdateResult updateNews(NewsUpdateCommand command) {
        memberDomainService.getMember(command.currentUserId());

        NewsUpdateInfo updateInfo = new NewsUpdateInfo(
                command.title(),
                command.description(),
                command.textContent(),
                command.videoUrl());

        News news = newsDomainService.update(command.id(), command.currentUserId(), updateInfo);

        return NewsUpdateResult.from(news);
    }
}
