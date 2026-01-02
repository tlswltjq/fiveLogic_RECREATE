package com.fivelogic_recreate.member.domain.service;

import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import com.fivelogic_recreate.member.exception.EmailDuplicationException;
import com.fivelogic_recreate.member.exception.UserIdDuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDomainService {
    private final MemberRepositoryPort repository;

    public Member register(Member member) {
        validateDuplicate(member);
        return repository.save(member);
    }

    private void validateDuplicate(Member member) {
        if (repository.existsByUserId(member.getUserId())) {
            throw new UserIdDuplicationException();
        }
        if (repository.existsByEmail(member.getEmail())) {
            throw new EmailDuplicationException();
        }
    }
}
