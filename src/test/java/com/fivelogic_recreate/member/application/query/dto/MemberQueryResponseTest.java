package com.fivelogic_recreate.member.application.query.dto;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberQueryResponseTest {
    private final MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("MemberQueryResponse가 정상적으로 생성된다.")
    void shouldCreateMemberQueryResponse() {
        Member member = memberFixture.build();

        MemberQueryResponse dto = new MemberQueryResponse(
                member.getUserId().value(),
                member.getEmail().value(),
                member.getName().firstName() + " " + member.getName().lastName(),
                member.getNickname().value(),
                member.getMemberType().name(),
                member.getBio().value(),
                member.getIsActivated()
        );

        assertThat(dto).isNotNull();
        assertThat(dto.userId()).isEqualTo(member.getUserId().value());
        assertThat(dto.email()).isEqualTo(member.getEmail().value());
        assertThat(dto.name()).isEqualTo(
                member.getName().firstName() + " " + member.getName().lastName()
        );
        assertThat(dto.nickname()).isEqualTo(member.getNickname().value());
        assertThat(dto.memberType()).isEqualTo(member.getMemberType().name());
        assertThat(dto.bio()).isEqualTo(member.getBio().value());
        assertThat(dto.isActive()).isEqualTo(member.getIsActivated());
    }
}
