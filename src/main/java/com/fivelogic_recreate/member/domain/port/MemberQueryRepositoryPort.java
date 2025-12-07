package com.fivelogic_recreate.member.domain.port;

import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.UserId;

import java.util.List;
import java.util.Optional;

public interface MemberQueryRepositoryPort {
    Optional<Member> findByUserId(UserId userId);
    List<Member> findAll();
}
