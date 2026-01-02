package com.fivelogic_recreate.member.domain.port;

import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.UserId;

import java.util.List;
import java.util.Optional;

public interface MemberQueryRepositoryPort {
    Optional<Member> findByUserId(UserId userId);
    List<Member> findAll();
}
