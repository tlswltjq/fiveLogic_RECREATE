package com.fivelogic_recreate.news.application.query;

import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import com.fivelogic_recreate.news.domain.port.NewsQueryRepositoryPort;
import com.fivelogic_recreate.news.exception.NewsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetNewsDetail {
    private final NewsQueryRepositoryPort newsQueryRepository;

    public NewsQueryResponse findById(Long id) {
        return newsQueryRepository.findQueryById(id)
                .orElseThrow(NewsNotFoundException::new);
    }
}
