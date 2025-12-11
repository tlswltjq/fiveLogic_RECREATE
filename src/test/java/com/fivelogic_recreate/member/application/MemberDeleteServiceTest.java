package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.application.command.MemberDeleteService;
import com.fivelogic_recreate.member.application.command.dto.MemberDeleteCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberInfo;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MemberDeleteServiceTest {
    @Mock
    MemberRepositoryPort memberRepositoryPort;

    @InjectMocks
    MemberDeleteService memberDeleteService;

    private MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("UserId가 주어지면 사용자를 삭제할 수 있다.")
    void shouldDeleteMember() {
        MemberDeleteCommand deleteCommand = new MemberDeleteCommand("userId");
        Member member = memberFixture.withUserId("userId").build();

        when(memberRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.of(member));
        when(memberRepositoryPort.save(any(Member.class))).thenAnswer(i -> i.getArgument(0));

        MemberInfo result = memberDeleteService.delete(deleteCommand);

        verify(memberRepositoryPort).save(any(Member.class));
        assertThat(result.isActivated()).isFalse();
    }

    @Test
    @DisplayName("존재하지 않는 UserId를 이용해 삭제하려하면 에러를 반환한다.")
    void shouldThrowExceptionWhenDeleteNonExistingMember() {
        MemberDeleteCommand deleteCommand = new MemberDeleteCommand("nonExistingUserId");

        assertThatThrownBy(() -> memberDeleteService.delete(deleteCommand))
                .isInstanceOf(MemberNotFoundException.class);
    }
}