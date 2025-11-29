package com.fivelogic_recreate.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {
    @Test
    @DisplayName("Member 객체가 정상적으로 생성된다")
    void shouldCreateMember_whenRequiredFieldsProvided() {
        String userId = "userId";
        String userPassword = "userPassword";
        String firstName = "firstName";
        String lastName = "lastName";
        String nickname = "nickname";

        Member member = Member.create(userId, userPassword, firstName, lastName, nickname);

        assertThat(member).isNotNull();
        assertThat(member.getUserId().userId()).isEqualTo(userId);
        assertThat(member.getPassword().password()).isEqualTo(userPassword);
        assertThat(member.getName().firstName() + " " + member.getName().lastName()).isEqualTo(firstName + " " + lastName);
        assertThat(member.getNickname().nickname()).isEqualTo(nickname);
        assertThat(member.getMemberType()).isEqualTo(MemberType.MENTEE);
        assertThat(member.getIsActivated()).isTrue();
    }

    @Test
    @DisplayName("MemberType을 Member 객체 생성시 다르게 할 수 있다.")
    void shouldCreateMemberType_whenRequiredFieldsProvided() {
        String userId = "userId";
        String userPassword = "userPassword";
        String firstName = "firstName";
        String lastName = "lastName";

        String nickname = "nickname";

        Member adminMember = Member.create(userId, userPassword, firstName, lastName, nickname, MemberType.ADMIN);
        Member mentorMember = Member.create(userId, userPassword, firstName, lastName, nickname, MemberType.MENTOR);
        Member menteeMember = Member.create(userId, userPassword, firstName, lastName, nickname, MemberType.MENTEE);

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
        String userId = "userId";
        String userPassword = "userPassword";
        String firstName = "firstName";
        String lastName = "lastName";
        String nickname = "nickname";

        Member member = Member.create(userId, userPassword, firstName, lastName, nickname);

        member.delete();
        assertThat(member).isNotNull();
        assertThat(member.getIsActivated()).isFalse();
    }

    String validId = "userId";
    String validPassword = "userPassword";
    String validFirstName = "user";
    String validLastName = "Name";
    String validNickname = "nickname";

    @Test
    @DisplayName("잘못된 userId 로는 Member 객체를 생성할 수 없다")
    void shouldNotCreateMember_whenUserIdIsInvalid() {
        List<String> wrongIds = Arrays.asList(
                "id",                           // short
                "longlonglonglonglongUserId",   // long
                "",                             // blank
                null                            // null
        );

        for (String id : wrongIds) {
            assertThatThrownBy(() -> Member.create(id, validPassword, validFirstName, validLastName, validNickname))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("잘못된 userPassword 로는 Member 객체를 생성할 수 없다")
    void shouldNotCreateMember_whenPasswordIsInvalid() {
        List<String> wrongPasswords = Arrays.asList(
                "1234",                             // short
                "12341234123412341234123412341234", // long
                "",                                 // blank
                null                                // null
        );

        for (String pw : wrongPasswords) {
            assertThatThrownBy(() -> Member.create(validId, pw, validFirstName, validLastName, validNickname))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("잘못된 name 으로는 Member 객체를 생성할 수 없다")
    void shouldNotCreateMember_whenNameIsInvalid() {
        List<String> wrongFirstNames = Arrays.asList(
                "",     // blank
                null    // null
        );

        for (String first : wrongFirstNames) {
            assertThatThrownBy(() -> Member.create(validId, validPassword, first, validLastName, validNickname))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        List<String> wrongLastNames = Arrays.asList(
                "",     // blank
                null    // null
        );

        for (String last : wrongLastNames) {
            assertThatThrownBy(() -> Member.create(validId, validPassword, validFirstName, last, validNickname))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("잘못된 nickname 으로는 Member 객체를 생성할 수 없다")
    void shouldNotCreateMember_whenNicknameIsInvalid() {
        List<String> wrongNicknames = Arrays.asList(
                "",     // blank
                null    // null
        );

        for (String nick : wrongNicknames) {
            assertThatThrownBy(() -> Member.create(validId, validPassword, validFirstName, validLastName, nick))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }


    @Test
    @DisplayName("Member의 닉네임을 변경할 수 있다.")
    void shouldUpdateNickname_whenRequiredFieldsProvided() {
        String userId = "userId";
        String userPassword = "userPassword";
        String firstName = "firstName";
        String lastName = "lastName";
        String nickname = "nickname";
        String newNickname = "changed";
        Member member = Member.create(userId, userPassword, firstName, lastName, nickname);

        member.updateNickname(newNickname);

        assertThat(member.getNickname().nickname()).isEqualTo(newNickname);
    }

    @Test
    @DisplayName("Member의 비밀번호를 변경할 수 있다.")
    void shouldUpdatePassword_whenRequiredFieldsProvided() {
        String userId = "userId";
        String userPassword = "userPassword";
        String firstName = "firstName";
        String lastName = "lastName";
        String nickname = "nickname";
        String newPassword = "changed";
        Member member = Member.create(userId, userPassword, firstName, lastName, nickname);

        member.updatePassword(newPassword);

        assertThat(member.getPassword().password()).isEqualTo(newPassword);
    }

    @Test
    @DisplayName("Member의 MemberType을 변경할 수 있다.")
    void shouldUpdateMemberType_whenRequiredFieldsProvided() {
        String userId = "userId";
        String userPassword = "userPassword";
        String firstName = "firstName";
        String lastName = "lastName";
        String nickname = "nickname";
        Member member = Member.create(userId, userPassword, firstName, lastName, nickname);

        member.updateMemberType(MemberType.ADMIN);
        assertThat(member.getMemberType()).isEqualTo(MemberType.ADMIN);
    }
}
