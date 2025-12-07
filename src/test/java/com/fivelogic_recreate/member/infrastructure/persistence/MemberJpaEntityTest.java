package com.fivelogic_recreate.member.infrastructure.persistence;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberJpaEntityTest {
    private MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("도메인 객체로부터 엔티티가 생성된다.")
    void shouldCreateEntityFromMember() {
        Member member = memberFixture.build();
        MemberJpaEntity entity = MemberJpaEntity.from(member);

        assertThat(entity).isNotNull();
        assertThat(entity.getUserId()).isEqualTo(member.getUserId().value());
        assertThat(entity.getName()).isEqualTo(member.getName().value());
        assertThat(entity.getEmail()).isEqualTo(member.getEmail().value());
        assertThat(entity.getNickname()).isEqualTo(member.getNickname().value());
        assertThat(entity.getBio()).isEqualTo(member.getBio().value());
        assertThat(entity.getMemberType()).isEqualTo(member.getMemberType());
        assertThat(entity.getIsActivated()).isEqualTo(member.getIsActivated());
    }

    @Test
    @DisplayName("엔티티로부터 도메인 객체가 생성된다.")
    void shouldCreateMemberFromEntity() {
        Member originalMember = memberFixture.build();
        MemberJpaEntity entity = MemberJpaEntity.from(originalMember);

        Member resultingMember = entity.toDomain();

        assertThat(resultingMember).isNotNull();
        assertThat(resultingMember.getId().value()).isEqualTo(originalMember.getId().value());
        assertThat(resultingMember.getUserId().value()).isEqualTo(originalMember.getUserId().value());
        assertThat(resultingMember.getPassword().value()).isEqualTo(originalMember.getPassword().value());
        assertThat(resultingMember.getName().value()).isEqualTo(originalMember.getName().value());
        assertThat(resultingMember.getNickname().value()).isEqualTo(originalMember.getNickname().value());
        assertThat(resultingMember.getEmail().value()).isEqualTo(originalMember.getEmail().value());
        assertThat(resultingMember.getBio().value()).isEqualTo(originalMember.getBio().value());
        assertThat(resultingMember.getMemberType()).isEqualTo(originalMember.getMemberType());
        assertThat(resultingMember.getIsActivated()).isEqualTo(originalMember.getIsActivated());
    }
}