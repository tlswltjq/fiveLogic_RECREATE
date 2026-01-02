package com.fivelogic_recreate.member.domain.service;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.domain.model.*;
import com.fivelogic_recreate.member.domain.port.MemberRepositoryPort;
import com.fivelogic_recreate.member.domain.service.dto.MemberUpdateInfo;
import com.fivelogic_recreate.member.exception.EmailDuplicationException;
import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import com.fivelogic_recreate.member.exception.SamePasswordException;
import com.fivelogic_recreate.member.exception.UserIdDuplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberDomainServiceTest {
    @Mock
    MemberRepositoryPort memberRepositoryPort;

    @InjectMocks
    MemberDomainService memberDomainService;

    private MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("회원 등록 성공")
    void shouldRegisterMemberSuccessfully() {
        Member member = memberFixture.withUserId("newUser").build();

        when(memberRepositoryPort.existsByUserId(any(UserId.class))).thenReturn(false);
        when(memberRepositoryPort.existsByEmail(any(Email.class))).thenReturn(false);
        when(memberRepositoryPort.save(any(Member.class))).thenReturn(member);

        Member registeredMember = memberDomainService.register(member);

        assertThat(registeredMember).isNotNull();
        verify(memberRepositoryPort).save(member);
    }

    @Test
    @DisplayName("중복된 UserId로 등록 시 예외 발생")
    void shouldThrowExceptionWhenUserIdExists() {
        Member member = memberFixture.withUserId("existingUser").build();

        when(memberRepositoryPort.existsByUserId(any(UserId.class))).thenReturn(true);

        assertThatThrownBy(() -> memberDomainService.register(member))
                .isInstanceOf(UserIdDuplicationException.class);

        verify(memberRepositoryPort, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("중복된 Email로 등록 시 예외 발생")
    void shouldThrowExceptionWhenEmailExists() {
        Member member = memberFixture.withEmail("existing@email.com").build();

        when(memberRepositoryPort.existsByUserId(any(UserId.class))).thenReturn(false);
        when(memberRepositoryPort.existsByEmail(any(Email.class))).thenReturn(true);

        assertThatThrownBy(() -> memberDomainService.register(member))
                .isInstanceOf(EmailDuplicationException.class);

        verify(memberRepositoryPort, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("회원 삭제 성공")
    void shouldDeleteMemberSuccessfully() {
        Member member = memberFixture.withUserId("userToDelete").build();

        when(memberRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.of(member));

        Member deletedMember = memberDomainService.delete("userToDelete");

        assertThat(deletedMember.getIsActivated()).isFalse();
    }

    @Test
    @DisplayName("존재하지 않는 회원 삭제 시 예외 발생")
    void shouldThrowExceptionWhenDeletingNonExistentMember() {
        when(memberRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberDomainService.delete("nonExistent"))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("비밀번호 변경 성공")
    void shouldUpdatePasswordSuccessfully() {
        Member member = memberFixture.withUserId("user1").withPassword("oldPass").build();

        when(memberRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.of(member));

        Member updatedMember = memberDomainService.updatePassword("user1", "newPass");

        assertThat(updatedMember.checkPassword(new UserPassword("newPass"))).isTrue();
    }

    @Test
    @DisplayName("동일한 비밀번호로 변경 시 예외 발생")
    void shouldThrowExceptionWhenUpdatingWithSamePassword() {
        Member member = memberFixture.withUserId("user1").withPassword("samePass").build();

        when(memberRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.of(member));

        assertThatThrownBy(() -> memberDomainService.updatePassword("user1", "samePass"))
                .isInstanceOf(SamePasswordException.class);
    }

    @Test
    @DisplayName("회원 정보 수정 성공")
    void shouldUpdateMemberInfoSuccessfully() {
        Member member = memberFixture.withUserId("user1").build();
        MemberUpdateInfo info = new MemberUpdateInfo(
                "newNick", "First", "Last", "new@email.com", "newBio", "MENTOR");

        when(memberRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.of(member));
        when(memberRepositoryPort.existsByEmail(any(Email.class))).thenReturn(false);

        Member updatedMember = memberDomainService.updateMember("user1", info);

        assertThat(updatedMember.getNickname().value()).isEqualTo("newNick");
        assertThat(updatedMember.getEmail().value()).isEqualTo("new@email.com");
        assertThat(updatedMember.getMemberType()).isEqualTo(MemberType.MENTOR);
    }

    @Test
    @DisplayName("이미 존재하는 이메일로 수정 시 예외 발생")
    void shouldThrowExceptionWhenUpdatingToExistingEmail() {
        Member member = memberFixture.withUserId("user1").withEmail("old@email.com").build();
        MemberUpdateInfo info = new MemberUpdateInfo(
                null, null, null, "existing@email.com", null, null);

        when(memberRepositoryPort.findByUserId(any(UserId.class))).thenReturn(Optional.of(member));
        when(memberRepositoryPort.existsByEmail(any(Email.class))).thenReturn(true);

        assertThatThrownBy(() -> memberDomainService.updateMember("user1", info))
                .isInstanceOf(EmailDuplicationException.class);
    }
}
