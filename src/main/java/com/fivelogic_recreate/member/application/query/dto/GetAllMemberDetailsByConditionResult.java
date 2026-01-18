package com.fivelogic_recreate.member.application.query.dto;

import java.util.List;

public record GetAllMemberDetailsByConditionResult(
        List<MemberDetail> memberDetails
) {
    public static GetAllMemberDetailsByConditionResult from(List<MemberDetail> memberDetails){
        return new GetAllMemberDetailsByConditionResult(memberDetails);
    }
}
