package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.service.MemberDomainService;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.service.NewsDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsCreateService {
    private final NewsDomainService newsDomainService;
    private final MemberDomainService memberDomainService;

    public NewsCreateResult createNews(NewsCreateCommand command) {
        Member author = memberDomainService.getMember(command.authorId());

        News draft = News.draft(command.title(), command.description(), command.textContent(), command.videoUrl(), author);

        draft.processing();
        News saved = newsDomainService.create(draft);
        return new NewsCreateResult(saved.getId(), saved.getTitle().value(),
                saved.getAuthor().getUserId().value(),
                saved.getStatus());
    }
}
