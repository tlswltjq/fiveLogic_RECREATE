package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.application.command.MemberUpdateService;
import com.fivelogic_recreate.member.application.command.dto.MemberUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberUpdateResult;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.service.MemberDomainService;
import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberUpdateServiceTest {
    @Mock
    MemberDomainService memberDomainService;

    @InjectMocks
    MemberUpdateService memberUpdateService;

    private MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("존재하는 Member의 정보를 업데이트할 수 있다.")
    void shouldUpdateMemberWhenMemberExists() {
        Member member = memberFixture.withUserId("userId").build();
        MemberUpdateCommand updateCommand = new MemberUpdateCommand(
                "userId",
                "updatedMail@email.com",
                "updated",
                "name",
                "updatednickname",
                "updated bio",
                "mentor");

        Member updatedMember = memberFixture
                .withUserId("userId")
                .withEmail("updatedMail@email.com")
                .withFirstname("updated")
                .withLastname("name")
                .withNickname("updatednickname")
                .withBio("updated bio")
                .build();

        when(memberDomainService.updateMember("userId", any())).thenReturn(updatedMember);

        MemberUpdateResult result = memberUpdateService.update(updateCommand);

        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo("updatedMail@email.com");
        verify(memberDomainService).updateMember(eq("userId"), any());
    }

    @Test
    @DisplayName("존재하지 않는 Member의 정보를 업데이트하면 예외를 던진다.")
    void shouldThrowExceptionWhenMemberDoesNotExist() {
        MemberUpdateCommand updateCommand = new MemberUpdateCommand(
                "userId",
                "updatedMail@email.com",
                "updated",
                "name",
                "updatednickname",
                "updated bio",
                "mentor");

        when(memberDomainService.updateMember(any(), any())).thenThrow(MemberNotFoundException.class);

        assertThatThrownBy(() -> memberUpdateService.update(updateCommand))
                .isInstanceOf(MemberNotFoundException.class);
    }
}
