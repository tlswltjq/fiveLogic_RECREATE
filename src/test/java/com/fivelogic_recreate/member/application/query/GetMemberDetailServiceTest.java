package com.fivelogic_recreate.member.application.query;

import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsCommand;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsResult;
import com.fivelogic_recreate.member.application.query.dto.MemberDetail;
import com.fivelogic_recreate.member.domain.model.MemberType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetMemberDetailServiceTest {

    @InjectMocks
    private GetMemberDetailService getMemberDetailService;

    @Mock
    private MemberReader memberReader;

    @Test
    @DisplayName("회원 상세 정보를 조회한다.")
    void getDetails() {
        // given
        String userId = "user1";
        GetMemberDetailsCommand command = new GetMemberDetailsCommand(userId);
        MemberDetail memberDetail = new MemberDetail(
                1L,
                userId,
                "Nickname",
                "MENTEE",
                "User One",
                "test@test.com",
                "Bio",
                true);

        given(memberReader.getDetail(userId)).willReturn(memberDetail);

        // when
        GetMemberDetailsResult result = getMemberDetailService.getDetails(command);

        // then
        assertThat(result.userId()).isEqualTo(memberDetail.userId());
        assertThat(result.nickname()).isEqualTo(memberDetail.nickname());
    }
}
