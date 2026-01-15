package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.MemberPolicyVerifier;
import com.fivelogic_recreate.member.application.MemberStore;
import com.fivelogic_recreate.member.application.command.dto.SignUpCommand;
import com.fivelogic_recreate.member.application.command.dto.SignUpResult;
import com.fivelogic_recreate.member.domain.model.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService {
    private final MemberStore memberStore;
    private final MemberPolicyVerifier policyVerifier;

    public SignUpResult register(SignUpCommand command) {

        policyVerifier.checkSignUpPolicy(command);

        Member member = Member.join(command.userId(), command.password(), command.email(), command.firstname(),
                command.lastname(), command.nickname(), command.bio());
        memberStore.store(member);

        return SignUpResult.from(member);
    }
}
