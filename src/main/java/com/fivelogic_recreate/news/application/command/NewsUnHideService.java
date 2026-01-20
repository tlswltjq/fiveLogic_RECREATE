//package com.fivelogic_recreate.news.application.command;
//
//import com.fivelogic_recreate.member.domain.service.MemberDomainService;
//import com.fivelogic_recreate.news.application.command.dto.NewsHideResult;
//import com.fivelogic_recreate.news.application.command.dto.NewsUnHideCommand;
//import com.fivelogic_recreate.news.domain.News;
//import com.fivelogic_recreate.news.domain.service.NewsDomainService;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class NewsUnHideService {
//    private final NewsDomainService newsDomainService;
//    private final MemberDomainService memberDomainService;
//
//    public NewsHideResult unHideNews(NewsUnHideCommand command) {
//        memberDomainService.getMember(command.currentUserId());
//
//        News news = newsDomainService.unhide(command.newsId(), command.currentUserId());
//        return NewsHideResult.from(news);
//    }
//}
