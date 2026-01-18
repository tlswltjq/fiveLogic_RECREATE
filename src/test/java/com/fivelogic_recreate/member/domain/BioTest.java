package com.fivelogic_recreate.member.domain;

import com.fivelogic_recreate.member.domain.model.Bio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BioTest {

    @Test
    @DisplayName("정상적으로 Bio 가 생성된다.")
    void bioCreationTest() {
        String bioValue = "This is a valid bio.";
        Bio bio = new Bio(bioValue);
        assertThat(bio).isNotNull();
        assertThat(bio.value()).isEqualTo(bioValue);
    }

    @Test
    @DisplayName("null 로 Bio 를 생성할 수 있다.")
    void bioCreationWithNullTest() {
        Bio bio = new Bio(null);
        assertThat(bio).isNotNull();
        assertThat(bio.value()).isNull();
    }

}
