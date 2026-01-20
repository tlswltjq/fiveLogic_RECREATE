package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.MemberServicePolicyValidator;
import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.application.command.dto.EmailUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.EmailUpdateResult;
import com.fivelogic_recreate.member.domain.model.Email;
import com.fivelogic_recreate.member.domain.model.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateEmail {
    private final MemberReader memberReader;
    private final MemberServicePolicyValidator policyVerifier;

    public EmailUpdateResult update(EmailUpdateCommand command) {
        policyVerifier.checkEmailUpdatePolicy(command);

        Member member = memberReader.getMember(command.userId());
        member.updateEmail(new Email(command.email()));

        return EmailUpdateResult.from(member);
    }
}
