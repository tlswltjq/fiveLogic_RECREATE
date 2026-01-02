package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.command.dto.MemberCreateCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberCreateResult;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCreateService {
    private final MemberDomainService domainService;

    public MemberCreateResult create(MemberCreateCommand command) {
        Member member = Member.join(command.userId(), command.password(), command.email(), command.firstname(), command.lastname(), command.nickname(), command.bio());

        domainService.register(member);

        return MemberCreateResult.from(member);
    }
}
