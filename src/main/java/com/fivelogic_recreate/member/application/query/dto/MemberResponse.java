package com.fivelogic_recreate.member.application.query.dto;

import com.fivelogic_recreate.member.domain.Member;


public record MemberResponse(
        String userId,
        String email,
        String name,
        String nickname,
        String memberType,
        String bio,
        boolean isActive

) {
    //TODO News도메인처럼 MemberQueryResponse로 이름을 변경하고 생성자 제거, QueryService에서 변환 메서드 추가할 것
    public MemberResponse(Member member) {
        this(
                member.getUserId().value(),
                member.getEmail().value(),
                member.getName().firstName() + " " + member.getName().lastName(),
                member.getNickname().value(),
                member.getMemberType().name(),
                member.getBio().value(),
                member.getIsActivated()
        );
    }
}
