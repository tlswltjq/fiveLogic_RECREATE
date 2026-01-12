package com.fivelogic_recreate.member.domain;

import com.fivelogic_recreate.member.domain.model.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {
    String validId = "userId";
    String validPassword = "userPassword";
    String validEmail = "test@test.com";
    String validFirstName = "user";
    String validLastName = "Name";
    String validNickname = "nickname";
    String validBio = "this is bio";

    @Test
    @DisplayName("Member 객체가 정상적으로 생성된다")
    void shouldJoinMember_whenRequiredFieldsProvided() {
        Member member = Member.join(validId, validPassword, validEmail, validFirstName, validLastName, validNickname,
                validBio);

        assertThat(member).isNotNull();
        assertThat(member.getUserId().value()).isEqualTo(validId);
        assertThat(member.getPassword().value()).isEqualTo(validPassword);
        assertThat(member.getEmail().value()).isEqualTo(validEmail);
        assertThat(member.getName().firstName() + " " + member.getName().lastName())
                .isEqualTo(validFirstName + " " + validLastName);
        assertThat(member.getNickname().value()).isEqualTo(validNickname);
        assertThat(member.getBio().value()).isEqualTo(validBio);
        assertThat(member.getMemberType()).isEqualTo(MemberType.MENTEE);
        assertThat(member.getIsActivated()).isTrue();
    }

    @Test
    @DisplayName("MemberType을 Member 객체 생성시 다르게 할 수 있다.")
    void shouldJoinMemberType_whenRequiredFieldsProvided() {

        Member adminMember = Member.join(validId, validPassword, validEmail, validFirstName, validLastName,
                validNickname, MemberType.ADMIN, validBio);
        Member mentorMember = Member.join(validId, validPassword, validEmail, validFirstName, validLastName,
                validNickname, MemberType.MENTOR, validBio);
        Member menteeMember = Member.join(validId, validPassword, validEmail, validFirstName, validLastName,
                validNickname, MemberType.MENTEE, validBio);

        assertThat(adminMember).isNotNull();
        assertThat(adminMember.getMemberType()).isEqualTo(MemberType.ADMIN);

        assertThat(mentorMember).isNotNull();
        assertThat(mentorMember.getMemberType()).isEqualTo(MemberType.MENTOR);

        assertThat(menteeMember).isNotNull();
        assertThat(menteeMember.getMemberType()).isEqualTo(MemberType.MENTEE);
    }

    @Test
    @DisplayName("Member를 삭제하면 isActivated필드가 false가 된다.")
    void shouldChangeStatus() {
        Member member = Member.join(validId, validPassword, validEmail, validFirstName, validLastName, validNickname,
                validBio);

        member.delete();
        assertThat(member).isNotNull();
        assertThat(member.getIsActivated()).isFalse();
    }

    @Test
    @DisplayName("잘못된 userId 로는 Member 객체를 생성할 수 없다")
    void shouldNotJoinMember_whenUserIdIsInvalid() {
        List<String> wrongIds = Arrays.asList(
                "id", // short
                "longlonglonglonglongUserId", // long
                "", // blank
                null // null
        );

        for (String id : wrongIds) {
            assertThatThrownBy(() -> Member.join(id, validPassword, validEmail, validFirstName, validLastName,
                    validNickname, validBio))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("잘못된 userPassword 로는 Member 객체를 생성할 수 없다")
    void shouldNotJoinMember_whenPasswordIsInvalid() {
        List<String> wrongPasswords = Arrays.asList(
                "1234", // short
                "12341234123412341234123412341234", // long
                "", // blank
                null // null
        );

        for (String pw : wrongPasswords) {
            assertThatThrownBy(
                    () -> Member.join(validId, pw, validEmail, validFirstName, validLastName, validNickname, validBio))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("잘못된 email 로는 Member 객체를 생성할 수 없다")
    void shouldNotJoinMember_whenEmailIsInvalid() {
        List<String> wrongEmails = Arrays.asList(
                "plainaddress",
                "#@%^%#$@#$@#.com",
                "@example.com",
                "Joe Smith <email@example.com>",
                "email.example.com",
                "email@example@example.com",
                ".email@example.com",
                "email.@example.com",
                "email..email@example.com",
                "あいうえお@example.com",
                "email@example.com (Joe Smith)",
                "email@example",
                "email@-example.com",
                "email@111.222.333.44444",
                "email@example..com",
                "Abc..123@example.com",
                "”(),:;<>[\\]@example.com",
                "just”not”right@example.com",
                "this\\ is\"really\\\"not\\allowed@example.com",
                "",
                null);

        for (String email : wrongEmails) {
            assertThatThrownBy(() -> Member.join(validId, validPassword, email, validFirstName, validLastName,
                    validNickname, validBio))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("잘못된 name 으로는 Member 객체를 생성할 수 없다")
    void shouldNotJoinMember_whenNameIsInvalid() {
        List<String> wrongFirstNames = Arrays.asList(
                "", // blank
                null // null
        );

        for (String first : wrongFirstNames) {
            assertThatThrownBy(() -> Member.join(validId, validPassword, validEmail, first, validLastName,
                    validNickname, validBio))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        List<String> wrongLastNames = Arrays.asList(
                "", // blank
                null // null
        );

        for (String last : wrongLastNames) {
            assertThatThrownBy(() -> Member.join(validId, validPassword, validEmail, validFirstName, last,
                    validNickname, validBio))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("잘못된 nickname 으로는 Member 객체를 생성할 수 없다")
    void shouldNotJoinMember_whenNicknameIsInvalid() {
        List<String> wrongNicknames = Arrays.asList(
                "", // blank
                null // null
        );

        for (String nick : wrongNicknames) {
            assertThatThrownBy(() -> Member.join(validId, validPassword, validEmail, validFirstName, validLastName,
                    nick, validBio))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("Member의 닉네임을 변경할 수 있다.")
    void shouldUpdateNickname_whenRequiredFieldsProvided() {
        String newNickname = "changed";
        Member member = Member.join(validId, validPassword, validEmail, validFirstName, validLastName, validNickname,
                validBio);

        member.updateNickname(new Nickname(newNickname));

        assertThat(member.getNickname().value()).isEqualTo(newNickname);
    }

    @Test
    @DisplayName("Member의 비밀번호를 변경할 수 있다.")
    void shouldUpdatePassword_whenRequiredFieldsProvided() {
        String newPassword = "changed";
        Member member = Member.join(validId, validPassword, validEmail, validFirstName, validLastName, validNickname,
                validBio);

        member.updatePassword(new UserPassword(newPassword));

        assertThat(member.getPassword().value()).isEqualTo(newPassword);
    }

    @Test
    @DisplayName("Member의 이메일을 변경할 수 있다.")
    void shouldUpdateEmail_whenRequiredFieldsProvided() {
        String newEmail = "changed@test.com";
        Member member = Member.join(validId, validPassword, validEmail, validFirstName, validLastName, validNickname,
                validBio);

        member.updateEmail(new Email(newEmail));

        assertThat(member.getEmail().value()).isEqualTo(newEmail);
    }

    @Test
    @DisplayName("Member의 자기소개를 변경할 수 있다.")
    void shouldUpdateBio_whenRequiredFieldsProvided() {
        String newBio = "this is new bio";
        Member member = Member.join(validId, validPassword, validEmail, validFirstName, validLastName, validNickname,
                validBio);

        member.updateBio(new Bio(newBio));

        assertThat(member.getBio().value()).isEqualTo(newBio);
    }

    @Test
    @DisplayName("Member의 MemberType을 변경할 수 있다.")
    void shouldUpdateMemberType_whenRequiredFieldsProvided() {
        Member member = Member.join(validId, validPassword, validEmail, validFirstName, validLastName, validNickname,
                validBio);

        member.updateMemberType(MemberType.ADMIN);
        assertThat(member.getMemberType()).isEqualTo(MemberType.ADMIN);
    }

    @Test
    @DisplayName("비밀번호 검증 성공/실패 테스트")
    void shouldCheckPasswordCorrectly() {
        Member member = Member.join(validId, validPassword, validEmail, validFirstName, validLastName, validNickname,
                validBio);

        assertThatThrownBy(() -> member.checkPassword(new UserPassword("wrong")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
