package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberStore {
    private final MemberRepositoryPort repository;

    public Member store(Member member){
        return repository.save(member);
    }
}
