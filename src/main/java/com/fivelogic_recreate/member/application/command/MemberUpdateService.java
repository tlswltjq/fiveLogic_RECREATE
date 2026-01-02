package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.command.dto.MemberUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberUpdateResult;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.service.MemberDomainService;
import com.fivelogic_recreate.member.domain.service.dto.MemberUpdateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberUpdateService {
    private final MemberDomainService domainService;

    public MemberUpdateResult update(MemberUpdateCommand command) {
        MemberUpdateInfo updateInfo = new MemberUpdateInfo(
                command.nickname(),
                command.firstname(),
                command.lastname(),
                command.email(),
                command.bio(),
                command.memberType()
        );

        Member updatedMember = domainService.updateMember(command.userId(), updateInfo);

        return MemberUpdateResult.from(updatedMember);
    }
}
