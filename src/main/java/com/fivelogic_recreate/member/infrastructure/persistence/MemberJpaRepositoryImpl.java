package com.fivelogic_recreate.member.infrastructure.persistence;

import com.fivelogic_recreate.member.application.query.dto.MemberDetail;
import com.fivelogic_recreate.member.application.query.dto.MyProfile;
import com.fivelogic_recreate.member.domain.model.*;
import com.fivelogic_recreate.member.domain.port.MemberQueryRepositoryPort;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepositoryImpl implements MemberQueryRepositoryPort, MemberRepositoryPort {
    private final MemberJpaRepository repository;

    @Override
    public Optional<Member> findById(MemberId memberId) {
        return repository.findById(memberId.value());
    }

    @Override
    public Optional<Member> findByUserId(UserId userId) {
        return repository.findByUserId(userId.value());
    }

    @Override
    public Optional<Member> findByNickname(Nickname nickname) {
        return repository.findByNickname(nickname.value());
    }

    @Override
    public Member save(Member member) {
        return repository.save(member);
    }

    @Override
    public boolean existsByUserId(UserId userId) {
        return repository.existsByUserId((userId.value()));
    }

    @Override
    public boolean existsByEmail(Email email) {
        return repository.existsByEmail(email.value());
    }

    @Override
    public List<Member> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<MemberDetail> getMemberDetail(UserId userId) {
        return repository.findUserDetailByUserId(userId.value());
    }

    @Override
    public List<MemberDetail> getMemberDetailsByType(MemberType memberType) {
        return repository.findUserDetailsByType(memberType.name());
    }

    @Override
    public Optional<MyProfile> getMemberProfile(UserId userId) {
        return repository.findProfileByUserId(userId.value());
    }

    @Override
    public List<MemberDetail> getMemberDetailsExceptAdmin() {
        return repository.findUserDetailsExceptAdmin();
    }
}
