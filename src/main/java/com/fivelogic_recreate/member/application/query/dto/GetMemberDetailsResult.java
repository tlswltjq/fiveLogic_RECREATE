package com.fivelogic_recreate.member.application.query.dto;

public record GetMemberDetailsResult(
        Long memberId,
        String userId,
        String nickname,
        String memberType,
        String name,
        String email,
        String bio,
        boolean isActive
) {
    public static GetMemberDetailsResult from(MemberDetail memberDetail) {
        return new GetMemberDetailsResult(memberDetail.memberId(), memberDetail.userId(), memberDetail.nickname(), memberDetail.memberType(), memberDetail.name(), memberDetail.email(), memberDetail.bio(), memberDetail.isActive());
    }
}
