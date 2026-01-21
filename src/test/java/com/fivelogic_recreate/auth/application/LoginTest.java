package com.fivelogic_recreate.auth.application;

import com.fivelogic_recreate.auth.application.dto.AuthTokens;
import com.fivelogic_recreate.auth.application.dto.LoginCommand;
import com.fivelogic_recreate.auth.application.dto.LoginResult;
import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.MemberType;
import com.fivelogic_recreate.member.domain.model.UserId;
import com.fivelogic_recreate.member.domain.model.UserPassword;
import com.fivelogic_recreate.member.exception.MemberNotActivatedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoginTest {

    @InjectMocks
    private Login login;

    @Mock
    private MemberReader memberReader;

    @Mock
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        // given
        LoginCommand command = new LoginCommand("user1", "password");
        Member member = mock(Member.class);
        AuthTokens tokens = new AuthTokens("access", "refresh");

        given(memberReader.getMember("user1")).willReturn(member);
        given(member.isActivate()).willReturn(true);
        given(member.getUserId()).willReturn(new UserId("user1"));
        given(member.getMemberType()).willReturn(MemberType.MENTEE);
        given(tokenProvider.issueTokens("user1", "MENTEE")).willReturn(tokens);

        // when
        LoginResult result = login.execute(command);

        // then
        assertThat(result.accessToken()).isEqualTo("access");
        assertThat(result.refreshToken()).isEqualTo("refresh");
        verify(member).checkPassword(any(UserPassword.class));
    }

    @Test
    @DisplayName("비활성화된 회원은 로그인 실패")
    void login_fail_inactive() {
        // given
        LoginCommand command = new LoginCommand("user1", "password");
        Member member = mock(Member.class);

        given(memberReader.getMember("user1")).willReturn(member);
        given(member.isActivate()).willReturn(false);

        // when & then
        assertThatThrownBy(() -> login.execute(command))
                .isInstanceOf(MemberNotActivatedException.class);
    }

    @Test
    @DisplayName("비밀번호 불일치 시 로그인 실패")
    void login_fail_password() {
        // given
        LoginCommand command = new LoginCommand("user1", "wrong_password");
        Member member = mock(Member.class);

        given(memberReader.getMember("user1")).willReturn(member);
        given(member.isActivate()).willReturn(true);
        willThrow(new IllegalArgumentException("비밀번호 불일치")).given(member).checkPassword(any());

        // when & then
        assertThatThrownBy(() -> login.execute(command))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
