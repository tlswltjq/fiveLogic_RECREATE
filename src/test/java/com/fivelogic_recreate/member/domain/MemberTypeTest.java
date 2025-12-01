package com.fivelogic_recreate.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("MemberType 테스트")
class MemberTypeTest {

    @ParameterizedTest
    @CsvSource({
            "MENTOR, MENTOR",
            "mentor, MENTOR",
            "MENTEE, MENTEE",
            "mentee, MENTEE",
            "ADMIN, ADMIN",
            "admin, ADMIN"
    })
    @DisplayName("문자열로부터 MemberType을 생성한다 (대소문자 무시)")
    void fromString(String input, MemberType expected) {
        MemberType result = MemberType.from(input);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("유효하지 않은 문자열로 MemberType을 생성하려 하면 오류가 발생한다.")
    void fromInvalidString() {
        String invalidType = "INVALID_TYPE";

        assertThatThrownBy(() -> MemberType.from(invalidType))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid member type: " + invalidType);
    }

    @Test
    @DisplayName("null로 MemberType을 생성하려 하면 오류가 발생한다.")
    void fromNull() {
        assertThatThrownBy(() -> MemberType.from(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid member type: null");
    }
}