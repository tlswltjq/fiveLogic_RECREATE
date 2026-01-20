package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.MemberServicePolicyValidator;
import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.application.command.dto.PasswordUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.PasswordUpdateResult;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.UserPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PasswordUpdateServiceTest {

    @InjectMocks
    private PasswordUpdateService passwordUpdateService;

    @Mock
    private MemberReader memberReader;

    @Mock
    private MemberServicePolicyValidator policyVerifier;

    @Mock
    private Member member;

    @Mock
    private com.fivelogic_recreate.member.domain.model.UserId userId;

    @Test
    @DisplayName("비밀번호 수정이 성공적으로 완료되어야 한다")
    void update_success() {
        // given
        PasswordUpdateCommand command = new PasswordUpdateCommand("testuser", "oldPassword", "newPassword");

        given(memberReader.getMember(anyString())).willReturn(member);
        given(member.getUserId()).willReturn(userId);
        given(userId.value()).willReturn("testuser");
        given(member.getId()).willReturn(1L);

        // when
        PasswordUpdateResult result = passwordUpdateService.update(command);

        // then
        verify(policyVerifier).checkPasswordUpdatePolicy(command);
        verify(memberReader).getMember(command.userId());
        verify(member).checkPassword(any(UserPassword.class));
        verify(member).updatePassword(any(UserPassword.class));

        assertThat(result).isNotNull();
        assertThat(result.userId()).isEqualTo(command.userId());
        assertThat(result.id()).isEqualTo(1L);
    }
}
