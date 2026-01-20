package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.MemberServicePolicyValidator;
import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.application.command.dto.WithdrawCommand;
import com.fivelogic_recreate.member.application.command.dto.WithdrawResult;
import com.fivelogic_recreate.member.domain.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WithdrawServiceTest {

    @InjectMocks
    private WithdrawService withdrawService;

    @Mock
    private MemberReader memberReader;

    @Mock
    private MemberServicePolicyValidator policyVerifier;

    @Mock
    private Member member;

    @Mock
    private com.fivelogic_recreate.member.domain.model.UserId userId;

    @Test
    @DisplayName("회원 탈퇴가 성공적으로 완료되어야 한다")
    void withdraw_success() {
        // given
        WithdrawCommand command = new WithdrawCommand("testuser", "reason");

        given(memberReader.getMember(anyString())).willReturn(member);
        given(member.getUserId()).willReturn(userId);
        given(userId.value()).willReturn("testuser");

        // when
        WithdrawResult result = withdrawService.withdraw(command);

        // then
        verify(policyVerifier).checkWithdrawPolicy(command);
        verify(memberReader).getMember(command.userId());
        verify(member).delete();

        assertThat(result).isNotNull();
        assertThat(result.userId()).isEqualTo(command.userId());
        assertThat(result.reasonWhy()).isEqualTo(command.reasonWhy());
    }
}
