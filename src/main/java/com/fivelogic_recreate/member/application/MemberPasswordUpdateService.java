package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.member.application.command.MemberPasswordUpdateCommand;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPasswordUpdateService {
    private final MemberRepositoryPort repository;

    public Member updatePassword(MemberPasswordUpdateCommand command) {
        UserId userId = new UserId(command.userId());
        Member member = repository.findById(userId).orElseThrow(RuntimeException::new);
        member.updatePassword(command.password());
        return repository.save(member);
    }
}
