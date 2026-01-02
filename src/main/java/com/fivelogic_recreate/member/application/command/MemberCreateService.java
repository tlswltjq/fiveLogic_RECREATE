package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.command.dto.MemberCreateCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberCreateResult;
import com.fivelogic_recreate.member.domain.model.Email;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.UserId;
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

    public MemberCreateResult create(MemberCreateCommand command) {
        UserId userId = new UserId(command.userId());
        Email email = new Email(command.email());

        if (repository.existsByUserId(userId)) {
            throw new UserIdDuplicationException();
        }
        if (repository.existsByEmail(email)) {
            throw new EmailDuplicationException();
        }

        Member member = Member.join(command.userId(), command.password(), command.email(), command.firstname(), command.lastname(), command.nickname(), command.bio());
        repository.save(member);

        return new MemberCreateResult(member.getUserId().value(), member.getName().value(), member.getNickname().value(), member.getMemberType().name(), member.getIsActivated(), member.getEmail().value(), member.getBio().value());
    }
}
