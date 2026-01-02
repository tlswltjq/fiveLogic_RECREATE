package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.application.command.MemberCreateService;
import com.fivelogic_recreate.member.application.command.dto.MemberCreateCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberCreateResult;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.service.MemberDomainService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberCreateServiceTest {
    @Mock
    MemberDomainService memberDomainService;

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

        when(memberDomainService.register(any(Member.class))).thenReturn(mockedMember);

        MemberCreateResult createdMember = memberCreateService.create(command);

        verify(memberDomainService).register(any(Member.class));
        assertThat(createdMember.userId()).isEqualTo(mockedMember.getUserId().value());
        assertThat(createdMember.name()).isEqualTo(mockedMember.getName().firstName() + " " + mockedMember.getName().lastName());
        assertThat(createdMember.email()).isEqualTo(mockedMember.getEmail().value());
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

        when(memberDomainService.register(any(Member.class))).thenThrow(new UserIdDuplicationException());

        assertThatThrownBy(() -> memberCreateService.create(command))
                .isInstanceOf(UserIdDuplicationException.class)
                .hasMessage("이미 사용중인 사용자 ID 입니다");
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

        when(memberDomainService.register(any(Member.class))).thenThrow(new EmailDuplicationException());

        assertThatThrownBy(() -> memberCreateService.create(command))
                .isInstanceOf(EmailDuplicationException.class)
                .hasMessage("이미 사용중인 Email 입니다");
    }
}