package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.application.command.MemberCreateService;
import com.fivelogic_recreate.member.application.command.dto.MemberCreateCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberInfo;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
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

        MemberInfo createdMember = memberCreateService.create(command);

        verify(memberRepositoryPort).existsByUserId(new UserId("user1"));
        verify(memberRepositoryPort).save(any(Member.class));
        assertThat(createdMember.id()).isEqualTo(mockedMember.getId().value());
        assertThat(createdMember.userId()).isEqualTo(mockedMember.getUserId().value());
        assertThat(createdMember.password()).isEqualTo(mockedMember.getPassword().value());
        assertThat(createdMember.name()).isEqualTo(mockedMember.getName().value());
        assertThat(createdMember.nickname()).isEqualTo(mockedMember.getNickname().value());
        assertThat(createdMember.memberType()).isEqualTo(mockedMember.getMemberType().name());
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

        when(memberRepositoryPort.existsByUserId(any(UserId.class)))
                .thenReturn(true);

        assertThatThrownBy(() -> memberCreateService.create(command))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Member already exists");

        verify(memberRepositoryPort).existsByUserId(any(UserId.class));
        verify(memberRepositoryPort, never()).save(any());
    }
}