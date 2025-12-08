package com.fivelogic_recreate.member.interfaces.rest.dto;

import com.fivelogic_recreate.member.application.query.dto.MemberResponse;

import java.util.List;

public record GetAllMembersResponse(
        List<GetMemberResponse> memberList
) {
    public static GetAllMembersResponse from(List<MemberResponse> memberInfoList) {
        return new GetAllMembersResponse(
                memberInfoList.stream()
                        .map(GetMemberResponse::new)
                        .toList()
        );
    }
}
