package com.fivelogic_recreate.member.application.command;

import com.fivelogic_recreate.member.application.MemberServicePolicyValidator;
import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.application.command.dto.InfoUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.InfoUpdateResult;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.Bio;
import com.fivelogic_recreate.member.domain.model.Nickname;
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
class UpdateInfoTest {

    @InjectMocks
    private UpdateInfo updateInfo;

    @Mock
    private MemberReader memberReader;

    @Mock
    private MemberServicePolicyValidator policyVerifier;

    @Mock
    private Member member;

    @Mock
    private com.fivelogic_recreate.member.domain.model.UserId userId;
    @Mock
    private Nickname nickname;
    @Mock
    private Bio bio;

    @Test
    @DisplayName("회원 정보(닉네임, 바이오) 수정이 성공적으로 완료되어야 한다")
    void update_success() {
        // given
        InfoUpdateCommand command = new InfoUpdateCommand("testuser", "newNickname", "newBio");

        given(memberReader.getMember(anyString())).willReturn(member);
        given(member.getUserId()).willReturn(userId);
        given(userId.value()).willReturn("testuser");
        given(member.getNickname()).willReturn(nickname);
        given(member.getBio()).willReturn(bio);
        given(nickname.value()).willReturn("newNickname");
        given(bio.value()).willReturn("newBio");

        // when
        InfoUpdateResult result = updateInfo.update(command);

        // then
        verify(policyVerifier).checkInfoUpdatePolicy(command);
        verify(memberReader).getMember(command.userId());
        verify(member).updateNickname(any(Nickname.class));
        verify(member).updateBio(any(Bio.class));

        assertThat(result).isNotNull();
        assertThat(result.userId()).isEqualTo(command.userId());
        assertThat(result.nickname()).isEqualTo("newNickname");
        assertThat(result.bio()).isEqualTo("newBio");
    }
}
