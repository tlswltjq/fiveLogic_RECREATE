package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.member.application.command.dto.EmailUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.InfoUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.PasswordUpdateCommand;
import com.fivelogic_recreate.member.application.command.dto.SignUpCommand;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsByTypeCommand;
import com.fivelogic_recreate.member.domain.model.Email;
import com.fivelogic_recreate.member.domain.model.MemberType;
import com.fivelogic_recreate.member.domain.model.UserId;
import com.fivelogic_recreate.member.domain.port.MemberQueryRepositoryPort;
import com.fivelogic_recreate.member.exception.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServicePolicyValidatorTest {

    @InjectMocks
    private MemberServicePolicyValidator validator;

    @Mock
    private MemberQueryRepositoryPort queryRepository;

    @Nested
    @DisplayName("회원가입 정책 검증")
    class SignUpPolicy {
        @Test
        @DisplayName("모든 정책을 준수하면 통과한다.")
        void success() {
            SignUpCommand command = new SignUpCommand(
                    "validUser",
                    "Valid123!",
                    "test@test.com",
                    "First",
                    "Last",
                    "validNick",
                    "Valid Bio");
            given(queryRepository.existsByUserId(any(UserId.class))).willReturn(false);
            given(queryRepository.existsByEmail(any(Email.class))).willReturn(false);

            assertThatCode(() -> validator.checkSignUpPolicy(command))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("사용자 ID 중복 시 예외 발생")
        void userIdDuplication() {
            SignUpCommand command = new SignUpCommand(
                    "duplicated", "Valid123!", "email@test.com", "First", "Last", "Nick", "Bio");
            given(queryRepository.existsByUserId(any(UserId.class))).willReturn(true);

            assertThatThrownBy(() -> validator.checkSignUpPolicy(command))
                    .isInstanceOf(UserIdDuplicationException.class);
        }

        @Test
        @DisplayName("이메일 중복 시 예외 발생")
        void emailDuplication() {
            SignUpCommand command = new SignUpCommand(
                    "validUser", "Valid123!", "duplicated@test.com", "First", "Last", "Nick", "Bio");
            given(queryRepository.existsByUserId(any(UserId.class))).willReturn(false);
            given(queryRepository.existsByEmail(any(Email.class))).willReturn(true);

            assertThatThrownBy(() -> validator.checkSignUpPolicy(command))
                    .isInstanceOf(EmailDuplicationException.class);
        }

        @Test
        @DisplayName("비밀번호 정책 위반 시 예외 발생")
        void invalidPassword() {
            // too short, no special char
            SignUpCommand command = new SignUpCommand(
                    "validUser", "weak", "test@test.com", "First", "Last", "Nick", "Bio");

            // Assuming checks are ordered, ID check comes first.
            // But here unit test isolates logic. The validator calls validatePasswordPolicy
            // explicitly.
            // Mocking prevent duplication check failure if it was called before.
            given(queryRepository.existsByUserId(any(UserId.class))).willReturn(false);
            given(queryRepository.existsByEmail(any(Email.class))).willReturn(false);

            assertThatThrownBy(() -> validator.checkSignUpPolicy(command))
                    .isInstanceOf(PasswordPolicyViolationException.class);
        }

        @Test
        @DisplayName("금지된 닉네임 사용 시 예외 발생")
        void bannedNickname() {
            SignUpCommand command = new SignUpCommand(
                    "validUser", "Valid123!", "test@test.com", "First", "Last", "admin", "Bio");

            given(queryRepository.existsByUserId(any(UserId.class))).willReturn(false);
            given(queryRepository.existsByEmail(any(Email.class))).willReturn(false);

            assertThatThrownBy(() -> validator.checkSignUpPolicy(command))
                    .isInstanceOf(NicknamePolicyViolationException.class);
        }
    }

    @Nested
    @DisplayName("정보 수정 정책 검증")
    class InfoUpdatePolicy {
        @Test
        @DisplayName("유효한 정보 수정 요청은 통과한다.")
        void success() {
            InfoUpdateCommand command = new InfoUpdateCommand("user", "validNick", "validBio");
            assertThatCode(() -> validator.checkInfoUpdatePolicy(command))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("금지된 닉네임 포함 시 예외 발생")
        void bannedNickname() {
            InfoUpdateCommand command = new InfoUpdateCommand("user", "관리자", "validBio");
            assertThatThrownBy(() -> validator.checkInfoUpdatePolicy(command))
                    .isInstanceOf(NicknamePolicyViolationException.class);
        }

        @Test
        @DisplayName("소개가 너무 길면 예외 발생")
        void bioTooLong() {
            String longBio = "a".repeat(501);
            InfoUpdateCommand command = new InfoUpdateCommand("user", "validNick", longBio);
            assertThatThrownBy(() -> validator.checkInfoUpdatePolicy(command))
                    .isInstanceOf(BioPolicyViolationException.class);
        }
    }

    @Nested
    @DisplayName("비밀번호 수정 정책 검증")
    class PasswordUpdatePolicy {
        @Test
        @DisplayName("유효한 새 비밀번호는 통과한다.")
        void success() {
            PasswordUpdateCommand command = new PasswordUpdateCommand("userId", "oldPass", "NewValid1!");
            assertThatCode(() -> validator.checkPasswordUpdatePolicy(command))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("유효하지 않은 새 비밀번호는 예외 발생")
        void invalidPassword() {
            PasswordUpdateCommand command = new PasswordUpdateCommand("userId", "oldPass", "weak");
            assertThatThrownBy(() -> validator.checkPasswordUpdatePolicy(command))
                    .isInstanceOf(PasswordPolicyViolationException.class);
        }
    }

    @Nested
    @DisplayName("이메일 수정 정책 검증")
    class EmailUpdatePolicy {
        @Test
        @DisplayName("중복되지 않은 이메일은 통과한다.")
        void success() {
            EmailUpdateCommand command = new EmailUpdateCommand("userId", "new@test.com");
            given(queryRepository.existsByEmail(any(Email.class))).willReturn(false);

            assertThatCode(() -> validator.checkEmailUpdatePolicy(command))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("중복된 이메일은 예외 발생")
        void duplicateEmail() {
            EmailUpdateCommand command = new EmailUpdateCommand("userId", "dup@test.com");
            given(queryRepository.existsByEmail(any(Email.class))).willReturn(true);

            assertThatThrownBy(() -> validator.checkEmailUpdatePolicy(command))
                    .isInstanceOf(EmailDuplicationException.class);
        }
    }

    @Nested
    @DisplayName("회원 조회 조건 검증")
    class RetrieveDetailsPolicy {
        @Test
        @DisplayName("유효한 타입 조건은 통과한다.")
        void success() {
            GetMemberDetailsByTypeCommand command = new GetMemberDetailsByTypeCommand("MENTOR");
            assertThatCode(() -> validator.checkRetrieveDetailsPolicy(command))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("유효하지 않은 타입 조건은 예외 발생")
        void invalidType() {
            GetMemberDetailsByTypeCommand command = new GetMemberDetailsByTypeCommand("INVALID");
            assertThatThrownBy(() -> validator.checkRetrieveDetailsPolicy(command))
                    .isInstanceOf(InvalidSearchConditionException.class);
        }
    }
}
