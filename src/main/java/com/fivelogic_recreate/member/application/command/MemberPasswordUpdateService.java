package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.command.dto.MemberPasswordUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberPasswordUpdateResult;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberPasswordUpdateService {
    private final MemberRepositoryPort repository;

    public MemberPasswordUpdateResult updatePassword(MemberPasswordUpdateCommand command) {
        UserId userId = new UserId(command.userId());
        Member member = repository.findByUserId(userId).orElseThrow(MemberNotFoundException::new);
        member.updatePassword(command.password());
        repository.save(member);
        return new MemberPasswordUpdateResult(member.getId().value(), member.getUserId().value());
    }
}
