package com.fivelogic_recreate.member.interfaces.rest.dto;

import com.fivelogic_recreate.member.application.query.dto.MemberQueryResponse;

import java.util.List;

public record GetAllMembersResponse(
        List<GetMemberResponse> memberList
) {
    public static GetAllMembersResponse from(List<MemberQueryResponse> memberInfoList) {
        return new GetAllMembersResponse(
                memberInfoList.stream()
                        .map(GetMemberResponse::new)
                        .toList()
        );
    }
}
