package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.member.application.command.MemberUpdateCommand;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.MemberType;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberUpdateService {
    private final MemberRepositoryPort repository;

    //todo DTO로 감쌀 것
    public Member update(MemberUpdateCommand command) {
        UserId userId = new UserId(command.userId());
        Member member = repository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));

        String nickname = command.nickname();
        if (nickname != null && !nickname.isBlank()) {
            member.updateNickname(nickname);
        }

        String memberType = command.memberType();
        if (memberType != null && !memberType.isBlank()) {
            member.updateMemberType(MemberType.from(memberType));
        }

        String firstname = command.firstname();
        String lastname = command.lastname();
        if (firstname != null && !firstname.isBlank()) {
            if (lastname != null && !lastname.isBlank()) {
                member.updateName(firstname, lastname);
            }
        }

        String email = command.email();
        if (email != null && !email.isBlank()) {
            member.updateEmail(email);
        }

        String bio = command.bio();
        if (bio != null && !bio.isBlank()) {
            member.updateBio(bio);
        }
        return repository.save(member);
    }
}
