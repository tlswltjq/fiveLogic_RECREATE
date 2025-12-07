package com.fivelogic_recreate.member.application.query;

import com.fivelogic_recreate.member.application.query.dto.MemberResponse;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberQueryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberQueryRepositoryPort repository;

    public MemberResponse getByUserId(String userId) {
        return repository.findByUserId(new UserId(userId))
                .map(MemberResponse::new)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<MemberResponse> getAll() {
        return repository.findAll().stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());
    }
}
