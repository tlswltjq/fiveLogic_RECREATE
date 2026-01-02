package com.fivelogic_recreate.member.infrastructure.persistence;

import com.fivelogic_recreate.member.domain.model.Email;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.MemberId;
import com.fivelogic_recreate.member.domain.model.Nickname;
import com.fivelogic_recreate.member.domain.model.UserId;
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
}
