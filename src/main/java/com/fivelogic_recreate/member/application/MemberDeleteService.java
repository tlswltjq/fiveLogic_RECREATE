package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.member.application.command.MemberDeleteCommand;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDeleteService {
    private final MemberRepositoryPort repository;

    public Member delete(MemberDeleteCommand command) {
        UserId userId = new UserId(command.userId());
        Member member = repository.findById(userId).orElseThrow(() -> new RuntimeException("User " + userId + " does not exist"));

        member.delete();
        return repository.save(member);
    }
}
