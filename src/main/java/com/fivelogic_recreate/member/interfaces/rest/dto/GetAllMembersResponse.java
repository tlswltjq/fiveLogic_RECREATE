package com.fivelogic_recreate.member.interfaces.rest.dto;

import java.util.List;

public record GetAllMembersResponse(
        List<GetMemberResponse> memberList
) {
}
