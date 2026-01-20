package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.MemberServicePolicyValidator;
import com.fivelogic_recreate.member.application.MemberStore;
import com.fivelogic_recreate.member.application.command.dto.SignUpCommand;
import com.fivelogic_recreate.member.application.command.dto.SignUpResult;
import com.fivelogic_recreate.member.domain.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SignUpTest {

    @InjectMocks
    private SignUp signUp;

    @Mock
    private MemberStore memberStore;

    @Mock
    private MemberServicePolicyValidator policyVerifier;

    @Test
    @DisplayName("회원가입이 성공적으로 완료되어야 한다")
    void register_success() {
        // given
        SignUpCommand command = new SignUpCommand(
                "testuser",
                "password123",
                "test@example.com",
                "John",
                "Doe",
                "johnny",
                "Hello World");

        // when
        SignUpResult result = signUp.register(command);

        // then
        verify(policyVerifier).checkSignUpPolicy(command);
        verify(memberStore).store(any(Member.class));

        assertThat(result).isNotNull();
        assertThat(result.userId()).isEqualTo(command.userId());
        assertThat(result.email()).isEqualTo(command.email());
    }
}
