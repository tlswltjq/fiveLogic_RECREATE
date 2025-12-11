package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.command.dto.MemberCreateCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberInfo;
import com.fivelogic_recreate.member.domain.Email;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import com.fivelogic_recreate.member.exception.EmailDuplicationException;
import com.fivelogic_recreate.member.exception.UserIdDuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCreateService {
    private final MemberRepositoryPort repository;

    public MemberInfo create(MemberCreateCommand command) {
        UserId userId = new UserId(command.userId());
        Email email = new Email(command.email());
        if (repository.existsByUserId(userId)) {
            throw new UserIdDuplicationException();
        }
        if (repository.existsByEmail(email)) {
            throw new EmailDuplicationException();
        }
        Member member = Member.join(command.userId(), command.password(), command.email(), command.firstname(), command.lastname(), command.nickname(), command.bio());

        return new MemberInfo(repository.save(member));
    }
}
