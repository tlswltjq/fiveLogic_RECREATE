package com.fivelogic_recreate.member.domain.service.dto;

public record MemberUpdateInfo(
        String nickname,
        String firstname,
        String lastname,
        String email,
        String bio,
        String memberType
) {}