package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.MemberServicePolicyValidator;
import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.application.command.dto.EmailUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.EmailUpdateResult;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.Email;
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
class UpdateEmailTest {

    @InjectMocks
    private UpdateEmail updateEmail;

    @Mock
    private MemberReader memberReader;

    @Mock
    private MemberServicePolicyValidator policyVerifier;

    @Mock
    private Member member;

    @Mock
    private com.fivelogic_recreate.member.domain.model.UserId userId;

    @Mock
    private com.fivelogic_recreate.member.domain.model.Email email;

    @Test
    @DisplayName("이메일 수정이 성공적으로 완료되어야 한다")
    void update_success() {
        // given
        EmailUpdateCommand command = new EmailUpdateCommand("testuser", "newemail@example.com");

        given(memberReader.getMember(anyString())).willReturn(member);
        given(member.getUserId()).willReturn(userId);
        given(member.getEmail()).willReturn(email);
        given(userId.value()).willReturn("testuser");
        given(email.value()).willReturn("newemail@example.com");

        // when
        EmailUpdateResult result = updateEmail.update(command);

        // then
        verify(policyVerifier).checkEmailUpdatePolicy(command);
        verify(memberReader).getMember(command.userId());
        verify(member).updateEmail(any(Email.class));

        assertThat(result).isNotNull();
        assertThat(result.userId()).isEqualTo(command.userId());
        assertThat(result.email()).isEqualTo(command.email());
    }
}
