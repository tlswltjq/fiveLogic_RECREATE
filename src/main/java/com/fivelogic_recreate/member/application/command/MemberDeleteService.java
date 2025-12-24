package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.command.dto.MemberDeleteCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberDeleteResult;
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
public class MemberDeleteService {
    private final MemberRepositoryPort repository;

    public MemberDeleteResult delete(MemberDeleteCommand command) {
        UserId userId = new UserId(command.userId());
        Member member = repository.findByUserId(userId).orElseThrow(MemberNotFoundException::new);
        member.delete();
        repository.save(member);

        return new MemberDeleteResult(member.getId(), member.getUserId().value(), member.getName().value(), member.getNickname().value(), member.getMemberType().name(), member.getIsActivated());
    }
}
