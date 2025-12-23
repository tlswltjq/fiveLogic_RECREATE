package com.fivelogic_recreate.news.application;


import com.fivelogic_recreate.news.application.command.*;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateResult;
import com.fivelogic_recreate.news.application.query.NewsQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsCreateService newsCreateService;
    private final NewsDeleteService newsDeleteService;
    private final NewsHideService newsHideService;
    private final NewsPublishService newsPublishService;
    private final NewsUnHideService newsUnHideService;
    private final NewsQueryService newsQueryService;

}
