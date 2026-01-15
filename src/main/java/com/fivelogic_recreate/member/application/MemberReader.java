package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.UserId;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberReader {
    private final MemberRepositoryPort repository;

    public Member getMember(String userId) {
        return repository.findByUserId(new UserId(userId))
                .orElseThrow(MemberNotFoundException::new);
    }
}
