package com.fivelogic_recreate.member.application.command.dto;

import com.fivelogic_recreate.member.application.query.dto.MyProfile;

public record GetMyProfileResult(
        String nickname,
        String email,
        String bio
) {
    public static GetMyProfileResult from(MyProfile profile) {
        return new GetMyProfileResult(profile.nickname(), profile.email(), profile.bio());
    }
}
