package com.fivelogic_recreate.member.interfaces.rest;

import com.fivelogic_recreate.common.rest.ApiResponse;
import com.fivelogic_recreate.member.application.MemberManagementService;
import com.fivelogic_recreate.member.application.command.dto.MemberInfo;
import com.fivelogic_recreate.member.application.query.dto.MemberResponse;
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
    private MemberManagementService memberManagementService;

    @InjectMocks
    private MemberController controller;

    @Test
    @DisplayName("회원 생성 성공")
    void createMember_success() {
        CreateMemberRequest request = new CreateMemberRequest("user1", "password", "email@test.com", "first", "name", "nick", "bio");
        MemberInfo mockMemberInfo = new MemberInfo(1L, "user1", "password", "first name", "nick", "MANTEE", true, "email@test.com", "bio");
        given(memberManagementService.createMember(request)).willReturn(mockMemberInfo);

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
        given(memberManagementService.getByUserId(userId)).willReturn(result);

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
        given(memberManagementService.getAll()).willReturn(list);

        ApiResponse<GetAllMembersResponse> response = controller.getMembers();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().memberList().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원 정보 업데이트 성공")
    void updateMemberInfo_success() {
        String userId = "user1";
        UpdateMemberRequest request = new UpdateMemberRequest("email@test.com", "first", "last", "newnickname", "bio", "mento");
        MemberInfo updatedMemberInfo = new MemberInfo(1L, userId, "password", "first last", "newnickname", "MENTO", true, "email@test.com", "bio");
        given(memberManagementService.updateMember(userId, request)).willReturn(updatedMemberInfo);

        ApiResponse<UpdateMemberResponse> response = controller.updateMemberInfo(userId, request);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().userId()).isEqualTo("user1");
    }

    @Test
    @DisplayName("회원 삭제 성공")
    void deleteMember_success() {
        String userId = "user1";
        MemberInfo deletedMemberInfo = new MemberInfo(1L, userId, "password", "name", "nick", "MANTEE", false, "email@test.com", "bio");
        given(memberManagementService.deleteMember(userId)).willReturn(deletedMemberInfo);

        ApiResponse<DeleteMemberResponse> response = controller.deleteMember(userId);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().userId()).isEqualTo("user1");
        assertThat(response.getMessage()).contains("삭제완료");
    }
}
