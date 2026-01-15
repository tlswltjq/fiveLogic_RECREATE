package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.MemberPolicyVerifier;
import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.application.command.dto.InfoUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.InfoUpdateResult;
import com.fivelogic_recreate.member.domain.model.Bio;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.Nickname;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class InfoUpdateService {
    private final MemberReader memberReader;
    private final MemberPolicyVerifier policyVerifier;

    public InfoUpdateResult update(InfoUpdateCommand command) {
        policyVerifier.checkInfoUpdatePolicy(command);

        Member member = memberReader.getMember(command.userId());

        member.updateNickname(new Nickname(command.nickname()));
        member.updateBio(new Bio(command.bio()));

        return InfoUpdateResult.from(member);
    }
}
