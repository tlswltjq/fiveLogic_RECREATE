package com.fivelogic_recreate.member.infrastructure.persistence;

import com.fivelogic_recreate.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);

    Optional<Member> findByNickname(String nickname);

    boolean existsByUserId(String userId);

    boolean existsByEmail(String value);
}
