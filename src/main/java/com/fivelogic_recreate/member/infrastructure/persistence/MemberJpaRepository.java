package com.fivelogic_recreate.member.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {
    Optional<MemberJpaEntity> findByUserId(String userId);

    Optional<MemberJpaEntity> findByNickname(String nickname);

    boolean existsByUserId(String userId);

    boolean existsByEmail(String value);
}
