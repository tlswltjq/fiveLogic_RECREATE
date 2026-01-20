package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.MemberServicePolicyValidator;
import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.application.command.dto.PasswordUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.PasswordUpdateResult;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.UserPassword;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdatePassword {
    private final MemberReader memberReader;
    private final MemberServicePolicyValidator policyVerifier;

    public PasswordUpdateResult update(PasswordUpdateCommand command) {
        policyVerifier.checkPasswordUpdatePolicy(command);

        Member member = memberReader.getMember(command.userId());
        member.checkPassword(new UserPassword(command.currentPassword()));
        member.updatePassword(new UserPassword(command.newPassword()));

        return PasswordUpdateResult.from(member);
    }
}
