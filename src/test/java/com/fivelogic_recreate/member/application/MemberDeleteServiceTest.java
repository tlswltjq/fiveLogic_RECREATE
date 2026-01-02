package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.application.command.MemberDeleteService;
import com.fivelogic_recreate.member.application.command.dto.MemberDeleteCommand;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.service.MemberDomainService;
import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberDeleteServiceTest {
    @Mock
    MemberDomainService memberDomainService;

    @InjectMocks
    MemberDeleteService memberDeleteService;

    private MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("UserId가 주어지면 사용자를 삭제할 수 있다.")
    void shouldDeleteMember() {
        MemberDeleteCommand deleteCommand = new MemberDeleteCommand("userId");
        Member member = memberFixture.withUserId("userId").build();

        when(memberDomainService.delete("userId")).thenReturn(member);

        memberDeleteService.delete(deleteCommand);

        verify(memberDomainService).delete("userId");
    }

    @Test
    @DisplayName("존재하지 않는 UserId를 이용해 삭제하려하면 에러를 반환한다.")
    void shouldThrowExceptionWhenDeleteNonExistingMember() {
        MemberDeleteCommand deleteCommand = new MemberDeleteCommand("nonExistingUserId");

        when(memberDomainService.delete("nonExistingUserId"))
                .thenThrow(MemberNotFoundException.class);

        assertThatThrownBy(() -> memberDeleteService.delete(deleteCommand))
                .isInstanceOf(MemberNotFoundException.class);
    }
}