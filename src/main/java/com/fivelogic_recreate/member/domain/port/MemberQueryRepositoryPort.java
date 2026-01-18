package com.fivelogic_recreate.member.domain.port;

import com.fivelogic_recreate.member.application.query.dto.MemberDetail;
import com.fivelogic_recreate.member.application.query.dto.MyProfile;
import com.fivelogic_recreate.member.domain.model.Email;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.UserId;

import java.util.List;
import java.util.Optional;

public interface MemberQueryRepositoryPort {
    Optional<Member> findByUserId(UserId userId);
    List<Member> findAll();
    boolean existsByUserId(UserId userId);
    boolean existsByEmail(Email email);
    Optional<MemberDetail> getMemberDetail(UserId userId);
    Optional<MyProfile> getMemberProfile(UserId userId);
}
