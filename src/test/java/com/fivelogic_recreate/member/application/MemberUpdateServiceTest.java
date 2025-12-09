package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.application.command.MemberUpdateService;
import com.fivelogic_recreate.member.application.command.dto.MemberInfo;
import com.fivelogic_recreate.member.application.command.dto.MemberUpdateCommand;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.MemberType;
import com.fivelogic_recreate.member.domain.Name;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberUpdateServiceTest {

    @Mock
    MemberRepositoryPort memberRepositoryPort;

    @InjectMocks
    MemberUpdateService memberUpdateService;

    private MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("존재하는 Member의 정보를 업데이트할 수 있다.")
    void shouldUpdateMemberWhenMemberExists() {
        Member member = memberFixture.withUserId("userId").build();
        MemberUpdateCommand updateCommand = new MemberUpdateCommand(
                "userId",
                "updatedMail@email.com",
                "updated",
                "name",
                "updatednickname",
                "updated bio",
                "mentor"
        );
        when(memberRepositoryPort.findByUserId(any(UserId.class)))
                .thenReturn(Optional.of(member));
        when(memberRepositoryPort.save(any(Member.class)))
                .thenReturn(member);
        MemberInfo memberInfo = memberUpdateService.update(updateCommand);

        verify(memberRepositoryPort).save(member);
        assertThat(memberInfo).isNotNull();
        assertThat(memberInfo.email()).isEqualTo("updatedMail@email.com");
        assertThat(memberInfo.name()).isEqualTo("updated name");
        assertThat(memberInfo.nickname()).isEqualTo("updatednickname");
        assertThat(memberInfo.bio()).isEqualTo("updated bio");
        assertThat(memberInfo.memberType()).isEqualTo(MemberType.MENTOR.name());
    }

    @Test
    @DisplayName("존재하지 않는 Member의 정보를 업데이트하면 예외를 던진다.")
    void shouldThrowExceptionWhenMemberDoesNotExist() {
        MemberUpdateCommand updateCommand = new MemberUpdateCommand(
                "userId",
                "example@email.com",
                "Kim",
                "Minsoo",
                "KimKim",
                "bio",
                "mentor"
        );
        when(memberRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberUpdateService.update(updateCommand))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("null이 아닌 필드만 업데이트된다 - nickname")
    void shouldUpdateNicknameWhenNotNull() {
        Member member = memberFixture.withUserId("userId").build();
        String originalEmail = member.getEmail().value();
        Name originalName = member.getName();
        String originalBio = member.getBio().value();
        MemberType originalMemberType = member.getMemberType();

        MemberUpdateCommand updateCommand = new MemberUpdateCommand(
                "userId",
                null,
                null,
                null,
                "updatedNickname",
                null,
                null
        );
        when(memberRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.of(member));
        when(memberRepositoryPort.save(any(Member.class))).thenReturn(member);

        memberUpdateService.update(updateCommand);

        verify(memberRepositoryPort).save(member);
        assertThat(member.getNickname().value()).isEqualTo("updatedNickname");
        assertThat(member.getEmail().value()).isEqualTo(originalEmail);
        assertThat(member.getName()).isEqualTo(originalName);
        assertThat(member.getBio().value()).isEqualTo(originalBio);
        assertThat(member.getMemberType()).isEqualTo(originalMemberType);
    }

    @Test
    @DisplayName("null이 아닌 필드만 업데이트된다 - memberType")
    void shouldUpdateMemberTypeWhenNotNull() {
        Member member = memberFixture.withUserId("userId").build();
        MemberUpdateCommand updateCommand = new MemberUpdateCommand(
                "userId", null, null, null, null, null, "MENTOR"
        );
        when(memberRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.of(member));
        when(memberRepositoryPort.save(any(Member.class))).thenReturn(member);

        memberUpdateService.update(updateCommand);

        verify(memberRepositoryPort).save(member);
        assertThat(member.getMemberType()).isEqualTo(MemberType.MENTOR);
    }

    @Test
    @DisplayName("null이 아닌 필드만 업데이트된다 - firstname/lastname")
    void shouldUpdateNameWhenFirstnameAndLastnameProvided() {
        Member member = memberFixture.withUserId("userId").build();
        MemberUpdateCommand updateCommand = new MemberUpdateCommand(
                "userId", null, "updatedFirst", "updatedLast", null, null, null
        );
        when(memberRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.of(member));
        when(memberRepositoryPort.save(any(Member.class))).thenReturn(member);

        memberUpdateService.update(updateCommand);

        verify(memberRepositoryPort).save(member);
        assertThat(member.getName()).isEqualTo(new Name("updatedFirst", "updatedLast"));
    }

    @Test
    @DisplayName("firstname만 있고 lastname이 없으면 이름은 업데이트되지 않는다.")
    void shouldNotUpdateNameWhenLastnameIsMissing() {
        Member member = memberFixture.withUserId("userId").build();
        Name originalName = member.getName();
        MemberUpdateCommand updateCommand = new MemberUpdateCommand(
                "userId", null, "updatedFirst", null, null, null, null
        );
        when(memberRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.of(member));
        when(memberRepositoryPort.save(any(Member.class))).thenReturn(member);

        memberUpdateService.update(updateCommand);

        assertThat(member.getName()).isEqualTo(originalName);
    }

    @Test
    @DisplayName("null이 아닌 필드만 업데이트된다 - email")
    void shouldUpdateEmailWhenNotNull() {
        Member member = memberFixture.withUserId("userId").build();
        MemberUpdateCommand updateCommand = new MemberUpdateCommand(
                "userId", "updated@email.com", null, null, null, null, null
        );
        when(memberRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.of(member));
        when(memberRepositoryPort.save(any(Member.class))).thenReturn(member);

        memberUpdateService.update(updateCommand);

        verify(memberRepositoryPort).save(member);
        assertThat(member.getEmail().value()).isEqualTo("updated@email.com");
    }

    @Test
    @DisplayName("null이 아닌 필드만 업데이트된다 - bio")
    void shouldUpdateBioWhenNotNull() {
        Member member = memberFixture.withUserId("userId").build();
        MemberUpdateCommand updateCommand = new MemberUpdateCommand(
                "userId", null, null, null, null, "updated bio", null
        );
        when(memberRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.of(member));
        when(memberRepositoryPort.save(any(Member.class))).thenReturn(member);

        memberUpdateService.update(updateCommand);

        verify(memberRepositoryPort).save(member);
        assertThat(member.getBio().value()).isEqualTo("updated bio");
    }
}
