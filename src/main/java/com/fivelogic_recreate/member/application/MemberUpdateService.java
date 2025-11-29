package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.member.application.command.MemberUpdateCommand;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.MemberType;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {
    private final MemberRepositoryPort repository;

    public Member update(MemberUpdateCommand command) {
        UserId userId = new UserId(command.userId());
        Member member = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        String nickname = command.nickname();
        if (nickname != null && !nickname.isBlank()) {
            member.updateNickname(nickname);
        }

        String memberType = command.memberType();
        if (memberType != null && !memberType.isBlank()) {
            member.updateMemberType(MemberType.from(memberType));
        }

        return member;
    }
}
