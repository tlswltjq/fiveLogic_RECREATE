package com.fivelogic_recreate.member.application.query;

import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.application.MemberServicePolicyValidator;
import com.fivelogic_recreate.member.application.query.dto.GetAllMemberDetailsByConditionResult;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsByTypeCommand;
import com.fivelogic_recreate.member.application.query.dto.MemberDetail;
import com.fivelogic_recreate.member.domain.model.MemberType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetMemberDetailsByConditionServiceTest {

    @InjectMocks
    private GetMemberDetailsByConditionService getMemberDetailsByConditionService;

    @Mock
    private MemberReader memberReader;

    @Mock
    private MemberServicePolicyValidator policyVerifier;

    @Test
    @DisplayName("GENERAL 타입(전체 조회)으로 회원 목록을 조회한다.")
    void byMemberType_General() {
        // given
        GetMemberDetailsByTypeCommand command = new GetMemberDetailsByTypeCommand("GENERAL");
        MemberDetail memberDetail = new MemberDetail(1L, "user1", "nick", "MENTEE", "name", "email", "bio", true);
        given(memberReader.getDetailsExceptAdmin()).willReturn(List.of(memberDetail));

        // when
        GetAllMemberDetailsByConditionResult result = getMemberDetailsByConditionService.byMemberType(command);

        // then
        verify(policyVerifier).checkRetrieveDetailsPolicy(command);
        assertThat(result.memberDetails()).hasSize(1);
        assertThat(result.memberDetails().get(0)).isEqualTo(memberDetail);
    }

    @Test
    @DisplayName("특정 타입(MENTOR)으로 회원 목록을 조회한다.")
    void byMemberType_Specific() {
        // given
        String type = "MENTOR";
        GetMemberDetailsByTypeCommand command = new GetMemberDetailsByTypeCommand(type);
        MemberDetail memberDetail = new MemberDetail(1L, "user1", "nick", "MENTOR", "name", "email", "bio", true);
        given(memberReader.getDetailsByType(type)).willReturn(List.of(memberDetail));

        // when
        GetAllMemberDetailsByConditionResult result = getMemberDetailsByConditionService.byMemberType(command);

        // then
        verify(policyVerifier).checkRetrieveDetailsPolicy(command);
        assertThat(result.memberDetails()).hasSize(1);
        assertThat(result.memberDetails().get(0)).isEqualTo(memberDetail);
    }
}
