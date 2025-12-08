package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.member.application.command.MemberCreateCommand;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCreateService {
    private final MemberRepositoryPort repository;

    public Member create(MemberCreateCommand command) {
        UserId userId = new UserId(command.userId());
        if(repository.existsByUserId(userId)){
            throw new RuntimeException("Member already exists");
        }
        Member member = Member.join(command.userId(), command.password(), command.email(), command.firstname(), command.lastname(), command.nickname(),command.bio());
        return repository.save(member);
    }
}
