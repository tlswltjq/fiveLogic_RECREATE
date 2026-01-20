package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.MemberServicePolicyValidator;
import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.application.command.dto.WithdrawCommand;
import com.fivelogic_recreate.member.application.command.dto.WithdrawResult;
import com.fivelogic_recreate.member.domain.model.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class Withdraw {
    private final MemberReader memberReader;
    private final MemberServicePolicyValidator policyVerifier;

    public WithdrawResult withdraw(WithdrawCommand command) {
        policyVerifier.checkWithdrawPolicy(command);
        Member member = memberReader.getMember(command.userId());
        member.delete();
        return new WithdrawResult(member.getUserId().value(), command.reasonWhy());
    }
}
