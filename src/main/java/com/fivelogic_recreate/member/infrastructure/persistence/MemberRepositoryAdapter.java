package com.fivelogic_recreate.member.infrastructure.persistence;

import com.fivelogic_recreate.member.domain.Email;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.Nickname;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberQueryRepositoryPort;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor//TODO MemberRepositoryAdapter -> MemberJpaRepositoryImpl로 이름 변경
public class MemberRepositoryAdapter implements MemberQueryRepositoryPort, MemberRepositoryPort {
    private final MemberJpaRepository repository;

    @Override
    public Optional<Member> findByUserId(UserId userId) {
        return repository.findByUserId(userId.value())
                .map(MemberJpaEntity::toDomain);
    }

    @Override
    public Optional<Member> findByNickname(Nickname nickname) {
        return repository.findByNickname(nickname.value())
                .map(MemberJpaEntity::toDomain);
    }

    @Override
    public Member save(Member member) {
        MemberJpaEntity entity = repository.save(MemberJpaEntity.from(member));
        return entity.toDomain();
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
        return repository.findAll().stream()
                .map(MemberJpaEntity::toDomain)
                .collect(Collectors.toList());
    }
}
