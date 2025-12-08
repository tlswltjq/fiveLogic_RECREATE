package com.fivelogic_recreate.member.interfaces.rest;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.application.MemberCreateService;
import com.fivelogic_recreate.member.application.MemberDeleteService;
import com.fivelogic_recreate.member.application.MemberUpdateService;
import com.fivelogic_recreate.member.application.command.dto.MemberDeleteCommand;
import com.fivelogic_recreate.member.application.query.MemberQueryService;
import com.fivelogic_recreate.member.application.query.dto.MemberResponse;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.interfaces.rest.common.ApiResponse;
import com.fivelogic_recreate.member.interfaces.rest.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {
    @Mock
    private MemberCreateService memberCreateService;
    @Mock
    private MemberQueryService memberQueryService;
    @Mock
    private MemberUpdateService memberUpdateService;
    @Mock
    private MemberDeleteService memberDeleteService;

    @InjectMocks
    private MemberController controller;

    private MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("회원 생성 성공")
    void createMember_success() {
        CreateMemberRequest request = new CreateMemberRequest("user1", "password", "email@test.com", "first", "name", "nick", "bio");
        Member mockMember = memberFixture.withUserId("user1").build();
        given(memberCreateService.create(request.toCommand())).willReturn(mockMember);

        ApiResponse<CreateMemberResponse> response = controller.createMember(request);

        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(response.getMessage()).isEqualTo("사용자 생성 완료");
        assertThat(response.getData().userId()).isEqualTo("user1");
    }

    @Test
    @DisplayName("회원 단일 조회 성공")
    void getMember_success() {
        String userId = "user1";
        MemberResponse result = new MemberResponse(userId, "email@test.com", "name", "nickname", "ADMIN", "bio", true);
        given(memberQueryService.getByUserId(userId)).willReturn(result);

        ApiResponse<GetMemberResponse> response = controller.getMember(userId);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().userId()).isEqualTo("user1");
    }

    @Test
    @DisplayName("회원 전체 조회 성공")
    void getMembers_success() {
        List<MemberResponse> list = List.of(
                new MemberResponse("user1", "a@test.com", "name1", "nickname1", "MANTEE", "bio1", true),
                new MemberResponse("user2", "b@test.com", "name2", "nickname2", "MENTO", "bio2", true)
        );
        given(memberQueryService.getAll()).willReturn(list);

        ApiResponse<GetAllMembersResponse> response = controller.getMembers();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().memberList().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원 정보 업데이트 성공")
    void updateMemberInfo_success() {
        String userId = "user1";
        UpdateMemberRequest request = new UpdateMemberRequest("email@test.com", "first", "last", "newnickname", "bio", "mento");
        Member updated = memberFixture.withUserId(userId).build();
        given(memberUpdateService.update(request.toCommand(userId))).willReturn(updated);

        ApiResponse<UpdateMemberResponse> response = controller.updateMemberInfo(userId, request);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().userId()).isEqualTo("user1");
    }

    @Test
    @DisplayName("회원 삭제 성공")
    void deleteMember_success() {
        String userId = "user1";
        Member mockMember = memberFixture.withUserId(userId).build();
        given(memberDeleteService.delete(new MemberDeleteCommand(userId))).willReturn(mockMember);

        ApiResponse<DeleteMemberResponse> response = controller.deleteMember(userId);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().userId()).isEqualTo("user1");
        assertThat(response.getMessage()).contains("삭제완료");
    }
}
