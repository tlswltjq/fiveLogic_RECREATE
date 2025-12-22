package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.application.command.MemberPasswordUpdateService;
import com.fivelogic_recreate.member.application.command.dto.MemberPasswordUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberPasswordUpdateResult;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberPasswordUpdateServiceTest {
    @Mock
    MemberRepositoryPort memberRepositoryPort;

    @InjectMocks
    MemberPasswordUpdateService memberPasswordUpdateService;

    private MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("성공적으로 업데이트할 수 있다.")
    void shouldUpdateMemberPasswordSuccessfully() {
        Member member = memberFixture.build();
        String newPassword = "newpassword";
        MemberPasswordUpdateCommand command = new MemberPasswordUpdateCommand(member.getUserId().value(), newPassword);

        when(memberRepositoryPort.findByUserId(any(UserId.class)))
                .thenReturn(Optional.of(member));
        when(memberRepositoryPort.save(any(Member.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MemberPasswordUpdateResult result = memberPasswordUpdateService.updatePassword(command);

        verify(memberRepositoryPort).save(any(Member.class));
        assertThat(result.userId()).isEqualTo(member.getUserId().value());
    }

    @Test
    @DisplayName("존재하지 않는 Member의 비밀번호를 업데이트하면 예외를 던진다.")
    void shouldThrowExceptionWhenMemberNotFound() {
        String nonExistUserId = "nonExistUserId";
        String newPassword = "newpassword1234!";
        MemberPasswordUpdateCommand command = new MemberPasswordUpdateCommand(nonExistUserId, newPassword);

        Assertions.assertThatThrownBy(() -> memberPasswordUpdateService.updatePassword(command))
                .isInstanceOf(MemberNotFoundException.class);
    }
}