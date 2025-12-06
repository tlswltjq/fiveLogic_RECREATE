package com.fivelogic_recreate.member.application.query;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.application.query.dto.MemberResponse;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.UserId;
import com.fivelogic_recreate.member.domain.port.MemberQueryRepositoryPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberQueryServiceTest {
    @Mock
    MemberQueryRepositoryPort memberQueryRepositoryPort;

    @InjectMocks
    MemberQueryService memberQueryService;

    private MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("UserId를 이용해 조회에 성공하면 MemberResponse가 반환된다.")
    void shouldReturnMemberResponse() {
        Member member = memberFixture.withUserId("userId").build();

        when(memberQueryRepositoryPort.findById(any(UserId.class)))
                .thenReturn(Optional.of(member));

        MemberResponse response = memberQueryService.getById("userId");

        assertThat(response).isNotNull();
        assertThat(response.userId()).isEqualTo(member.getUserId().userId());
        assertThat(response.email()).isEqualTo(member.getEmail().value());
        assertThat(response.name()).isEqualTo(member.getName().firstName() + " " + member.getName().lastName());
    }

    @Test
    @DisplayName("존재하지 않는 UserId를 이용해 조회하면 예외가 발생한다.")
    void shouldThrowExceptionWhenUserIdDoesNotExist() {
        String nonExistUserId = "XXXXXXXX";

        Assertions.assertThatThrownBy(() -> memberQueryService.getById(nonExistUserId))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("모든 사용자를 조회할 수 있다.")
    void shouldReturnAllMembers() {
        List<Member> memberList = IntStream.range(0, 3)
                .mapToObj(e -> memberFixture.withUserId("user" + e).build())
                .toList();

        when(memberQueryRepositoryPort.findAll()).thenReturn(memberList);

        List<MemberResponse> response = memberQueryService.getAll();

        assertThat(response).isNotNull();
        assertThat(response).hasSize(3);

        IntStream.range(0, 3).forEach(i ->
                assertThat(response.get(i).userId()).isEqualTo("user" + i)
        );
    }
}