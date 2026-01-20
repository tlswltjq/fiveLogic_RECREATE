//package com.fivelogic_recreate.news.application.command;
//
//import com.fivelogic_recreate.member.domain.service.MemberDomainService;
//import com.fivelogic_recreate.news.application.command.dto.NewsDeleteCommand;
//import com.fivelogic_recreate.news.application.command.dto.NewsDeleteResult;
//import com.fivelogic_recreate.news.domain.News;
//import com.fivelogic_recreate.news.domain.service.NewsDomainService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class NewsDeleteService {
//    private final NewsDomainService newsDomainService;
//    private final MemberDomainService memberDomainService;
//
//    public NewsDeleteResult deleteNews(NewsDeleteCommand command) {
//        memberDomainService.getMember(command.currentUserId());
//
//        News news = newsDomainService.delete(command.newsId(), command.currentUserId());
//
//        return NewsDeleteResult.from(news);
//    }
//}
