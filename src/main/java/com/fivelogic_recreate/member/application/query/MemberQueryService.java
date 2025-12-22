package com.fivelogic_recreate.member.application.query;

import com.fivelogic_recreate.member.application.query.dto.MemberQueryResponse;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberQueryRepositoryPort;
import com.fivelogic_recreate.member.exception.MemberNotFoundException;
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

    public MemberQueryResponse getByUserId(String userId) {
        return repository.findByUserId(new UserId(userId))
                .map(this::toResponse)
                .orElseThrow(MemberNotFoundException::new);
    }

    public List<MemberQueryResponse> getAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private MemberQueryResponse toResponse(Member member) {
        return new MemberQueryResponse(
                member.getUserId().value(),
                member.getEmail().value(),
                member.getName().firstName() + " " + member.getName().lastName(),
                member.getNickname().value(),
                member.getMemberType().name(),
                member.getBio().value(),
                member.getIsActivated()
        );
    }
}
