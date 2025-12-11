package com.fivelogic_recreate.member.domain.port;


import com.fivelogic_recreate.member.domain.Email;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.Nickname;
import com.fivelogic_recreate.member.domain.UserId;

import java.util.Optional;

public interface MemberRepositoryPort {

    Optional<Member> findByUserId(UserId userId);

    Optional<Member> findByNickname(Nickname nickname);

    Member save(Member member);

    boolean existsByUserId(UserId userId);

    boolean existsByEmail(Email email);
}