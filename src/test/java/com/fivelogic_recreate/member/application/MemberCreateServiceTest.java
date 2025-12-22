package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.application.command.MemberCreateService;
import com.fivelogic_recreate.member.application.command.dto.MemberCreateCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberCreateResult;
import com.fivelogic_recreate.member.domain.Email;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import com.fivelogic_recreate.member.exception.EmailDuplicationException;
import com.fivelogic_recreate.member.exception.UserIdDuplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberCreateServiceTest {
    @Mock
    MemberRepositoryPort memberRepositoryPort;

    @InjectMocks
    MemberCreateService memberCreateService;

    private MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("사용자 정보가 주어지면 성공적으로 Member객체를 저장하고 반환한다.")
    void shouldSaveAndReturnMemberWhenValidUserInfoProvided() {
        MemberCreateCommand command = new MemberCreateCommand(
                "user1",
                "password",
                "email@test.com",
                "John",
                "Doe",
                "johnd",
                "Hello bio"
        );

        Member mockedMember = memberFixture.withMemberId(1L).build();

        when(memberRepositoryPort.save(any(Member.class))).thenReturn(mockedMember);

        MemberCreateResult createdMember = memberCreateService.create(command);

        verify(memberRepositoryPort).existsByUserId(new UserId("user1"));
        verify(memberRepositoryPort).existsByEmail(new Email("email@test.com"));
        verify(memberRepositoryPort).save(any(Member.class));
        assertThat(createdMember.userId()).isEqualTo(mockedMember.getUserId().value());
        assertThat(createdMember.name()).isEqualTo(mockedMember.getName().firstName() + " " + mockedMember.getName().lastName());
        assertThat(createdMember.nickname()).isEqualTo(mockedMember.getNickname().value());
        assertThat(createdMember.memberType()).isEqualTo(mockedMember.getMemberType().toString());
        assertThat(createdMember.isActivated()).isEqualTo(mockedMember.getIsActivated());
        assertThat(createdMember.email()).isEqualTo(mockedMember.getEmail().value());
        assertThat(createdMember.bio()).isEqualTo(mockedMember.getBio().value());

    }

    @Test
    @DisplayName("동일한 UserId로는 객체를 생성할 수 없다.")
    void shouldThrowException_whenUserIdAlreadyExists() {
        String userId = "user1d";
        MemberCreateCommand command = new MemberCreateCommand(
                userId,
                "password",
                "email@test.com",
                "John",
                "Doe",
                "johnd",
                "Hello bio"
        );

        when(memberRepositoryPort.existsByUserId(any(UserId.class))).thenReturn(true);

        assertThatThrownBy(() -> memberCreateService.create(command))
                .isInstanceOf(UserIdDuplicationException.class)
                .hasMessage("이미 사용중인 사용자 ID 입니다");

        verify(memberRepositoryPort).existsByUserId(any(UserId.class));
        verify(memberRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("동일한 Email로는 객체를 생성할 수 없다.")
    void shouldThrowException_whenEmailAlreadyExists() {
        String existEmail = "email@test.com";
        MemberCreateCommand command = new MemberCreateCommand(
                "user1d",
                "password",
                existEmail,
                "John",
                "Doe",
                "johnd",
                "Hello bio"
        );
        Member mockedMember = memberFixture.withMemberId(1L).build();

        when(memberRepositoryPort.existsByEmail(any(Email.class))).thenReturn(true);

        assertThatThrownBy(() -> memberCreateService.create(command))
                .isInstanceOf(EmailDuplicationException.class)
                .hasMessage("이미 사용중인 Email 입니다");
    }
}