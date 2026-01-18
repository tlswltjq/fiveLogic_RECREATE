package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.member.application.query.dto.MemberDetail;
import com.fivelogic_recreate.member.application.query.dto.MyProfile;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.MemberType;
import com.fivelogic_recreate.member.domain.model.UserId;
import com.fivelogic_recreate.member.domain.port.MemberQueryRepositoryPort;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberReader {
    private final MemberRepositoryPort repository;
    private final MemberQueryRepositoryPort queryRepository;

    public Member getMember(String userId) {
        return repository.findByUserId(new UserId(userId))
                .orElseThrow(MemberNotFoundException::new);
    }

    public MemberDetail getDetail(String userId) {
        return queryRepository.getMemberDetail(new UserId(userId))
                .orElseThrow(MemberNotFoundException::new);
    }

    public List<MemberDetail> getDetailsByType(String memberType) {
        return queryRepository.getMemberDetailsByType(MemberType.from(memberType));
    }

    public MyProfile getMemberProfile(String userId) {
        return queryRepository.getMemberProfile(new UserId(userId))
                .orElseThrow(MemberNotFoundException::new);
    }

    public List<MemberDetail> getDetailsExceptAdmin() {
        return queryRepository.getMemberDetailsExceptAdmin();
    }
}
