package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.command.dto.MemberPasswordUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberPasswordUpdateResult;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberPasswordUpdateService {
    private final MemberDomainService domainService;

    public MemberPasswordUpdateResult updatePassword(MemberPasswordUpdateCommand command) {
        Member member = domainService.updatePassword(command.userId(), command.password());
        return MemberPasswordUpdateResult.from(member);
    }
}
