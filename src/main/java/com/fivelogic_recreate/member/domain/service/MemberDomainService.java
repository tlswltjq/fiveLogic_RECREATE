package com.fivelogic_recreate.member.domain.service;

import com.fivelogic_recreate.member.domain.model.*;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import com.fivelogic_recreate.member.domain.service.dto.MemberUpdateInfo;
import com.fivelogic_recreate.member.exception.EmailDuplicationException;
import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import com.fivelogic_recreate.member.exception.SamePasswordException;
import com.fivelogic_recreate.member.exception.UserIdDuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDomainService {
    private final MemberRepositoryPort repository;

    public Member register(Member member) {
        if (repository.existsByUserId(member.getUserId())) {
            throw new UserIdDuplicationException();
        }
        if (repository.existsByEmail(member.getEmail())) {
            throw new EmailDuplicationException();
        }
        return repository.save(member);
    }

    public Member delete(String userId) {
        Member member = getMemberById(userId);
        member.delete();
        return member;
    }

    public Member updatePassword(String userId, String password) {
        Member member = getMemberById(userId);

        UserPassword newPassword = new UserPassword(password);
        if (member.getPassword().equals(newPassword)) {
            throw new SamePasswordException();
        }

        member.updatePassword(newPassword);
        return member;
    }

    public Member updateMember(String userId, MemberUpdateInfo info) {
        Member member = getMemberById(userId);

        if (shouldUpdateEmail(member, info.email())) {
            validateEmailUniqueness(info.email());
            member.updateEmail(new Email(info.email()));
        }

        if (info.memberType() != null && !info.memberType().isBlank()) {
            member.updateMemberType(MemberType.from(info.memberType()));
        }

        updateBasicProfile(member, info);

        return member;
    }

    public Member getMember(String userId) {
        return getMemberById(userId);
    }

    public Member validateMember(String userId, String rawPassword) {
        Member member = getMemberById(userId);
        member.checkPassword(new UserPassword(rawPassword));
        return member;
    }

    private Member getMemberById(String userId) {
        return repository.findByUserId(new UserId(userId))
                .orElseThrow(MemberNotFoundException::new);
    }

    private boolean shouldUpdateEmail(Member member, String newEmail) {
        return newEmail != null && !newEmail.isBlank()
                && !member.getEmail().value().equals(newEmail);
    }

    private void validateEmailUniqueness(String email) {
        if (repository.existsByEmail(new Email(email))) {
            throw new EmailDuplicationException();
        }
    }

    private void updateBasicProfile(Member member, MemberUpdateInfo info) {
        if (info.nickname() != null && !info.nickname().isBlank()) {
            member.updateNickname(new Nickname(info.nickname()));
        }
        if (info.firstname() != null && !info.lastname().isBlank()) {
            member.updateName(new Name(info.firstname(), info.lastname()));
        }
        if (info.bio() != null && !info.bio().isBlank()) {
            member.updateBio(new Bio(info.bio()));
        }
    }
}
