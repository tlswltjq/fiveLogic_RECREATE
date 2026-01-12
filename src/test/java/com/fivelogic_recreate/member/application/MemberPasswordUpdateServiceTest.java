package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.application.command.MemberPasswordUpdateService;
import com.fivelogic_recreate.member.application.command.dto.MemberPasswordUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberPasswordUpdateResult;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.UserPassword;
import com.fivelogic_recreate.member.domain.service.MemberDomainService;
import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import com.fivelogic_recreate.member.exception.SamePasswordException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberPasswordUpdateServiceTest {
    @Mock
    MemberDomainService memberDomainService;

    @InjectMocks
    MemberPasswordUpdateService memberPasswordUpdateService;

    private MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("성공적으로 업데이트할 수 있다.")
    void shouldUpdateMemberPasswordSuccessfully() {
        Member member = memberFixture.withPassword("password").build();
        String password = "password";
        member.checkPassword(new UserPassword(password));
        password = "anotherPassword";

        when(memberDomainService.updatePassword(eq(member.getUserId().value()), any()))
                .thenReturn(member);

        MemberPasswordUpdateCommand command = new MemberPasswordUpdateCommand(member.getUserId().value(), password);

        MemberPasswordUpdateResult result = memberPasswordUpdateService.updatePassword(command);

        assertThat(result.userId()).isEqualTo(member.getUserId().value());
        verify(memberDomainService).updatePassword(eq(member.getUserId().value()), eq(password));
    }

    @Test
    @DisplayName("존재하지 않는 Member의 비밀번호를 업데이트하면 예외를 던진다.")
    void shouldThrowExceptionWhenMemberNotFound() {
        String nonExistUserId = "nonExistUserId";
        String newPassword = "newpassword1234!";
        MemberPasswordUpdateCommand command = new MemberPasswordUpdateCommand(nonExistUserId, newPassword);

        when(memberDomainService.updatePassword(eq(nonExistUserId), eq(newPassword)))
                .thenThrow(MemberNotFoundException.class);

        Assertions.assertThatThrownBy(() -> memberPasswordUpdateService.updatePassword(command))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("기존 비밀번호와 동일한 비밀번호로 변경하면 예외를 던진다.")
    void shouldThrowExceptionWhenSamePassword() {
        String userId = "userId";
        String samePassword = "samePassword";

        MemberPasswordUpdateCommand command = new MemberPasswordUpdateCommand(userId, samePassword);

        when(memberDomainService.updatePassword(eq(userId), eq(samePassword)))
                .thenThrow(SamePasswordException.class);

        Assertions.assertThatThrownBy(() -> memberPasswordUpdateService.updatePassword(command))
                .isInstanceOf(SamePasswordException.class);
    }
}