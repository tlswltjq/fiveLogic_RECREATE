package com.fivelogic_recreate.member.domain.port;

import com.fivelogic_recreate.member.domain.model.Email;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.MemberId;
import com.fivelogic_recreate.member.domain.model.Nickname;
import com.fivelogic_recreate.member.domain.model.UserId;

import java.util.Optional;

public interface MemberRepositoryPort {

    Optional<Member> findById(MemberId memberId);

    Optional<Member> findByUserId(UserId userId);

    Optional<Member> findByNickname(Nickname nickname);

    Member save(Member member);

//    boolean existsByUserId(UserId userId);

//    boolean existsByEmail(Email email);
}