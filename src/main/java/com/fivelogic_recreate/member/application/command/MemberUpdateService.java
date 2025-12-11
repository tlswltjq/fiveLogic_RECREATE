package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.command.dto.MemberInfo;
import com.fivelogic_recreate.member.application.command.dto.MemberUpdateCommand;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.MemberType;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberUpdateService {
    private final MemberRepositoryPort repository;

    public MemberInfo update(MemberUpdateCommand command) {
        UserId userId = new UserId(command.userId());
        Member member = repository.findByUserId(userId).orElseThrow(MemberNotFoundException::new);

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

        return new MemberInfo(repository.save(member));
    }
}
