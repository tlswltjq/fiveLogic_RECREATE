package com.fivelogic_recreate.member.application.command.dto;

import com.fivelogic_recreate.member.domain.Member;

public record MemberInfo(
        Long id,
        String userId,
        String password,
        String name,
        String nickname,
        String memberType,
        Boolean isActivated,
        String email,
        String bio
) {
    public MemberInfo (Member member){
        this(member.getId().value(), member.getUserId().value(), member.getPassword().value(), member.getName().value(), member.getNickname().value(), member.getMemberType().name(), member.getIsActivated(), member.getEmail().value(), member.getBio().value());
    }
    //TODO 뉴스 도메인 처럼 COMMAND의 요청 처리를담을 DTO를 의도가 확실하게 보이도록 작성, 리팩토링할것
}
