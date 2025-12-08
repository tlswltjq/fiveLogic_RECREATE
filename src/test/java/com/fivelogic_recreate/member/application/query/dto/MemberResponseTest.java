package com.fivelogic_recreate.member.application.query.dto;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberResponseTest {
    private MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("Member를 이용해 생성할 수 있다.")
    void shouldCreateMemberResponse() {
        Member member = memberFixture.build();

        MemberResponse dto = new MemberResponse(member);

        assertThat(dto).isNotNull();
        assertThat(dto.userId()).isEqualTo(member.getUserId().value());
        assertThat(dto.email()).isEqualTo(member.getEmail().value());
        assertThat(dto.name()).isEqualTo(member.getName().firstName() + " " + member.getName().lastName());
        assertThat(dto.email()).isEqualTo(member.getEmail().value());
        assertThat(dto.nickname()).isEqualTo(member.getNickname().value());
        assertThat(dto.memberType()).isEqualTo(member.getMemberType().name());
        assertThat(dto.bio()).isEqualTo(member.getBio().value());
        assertThat(dto.isActive()).isEqualTo(member.getIsActivated());
    }

}