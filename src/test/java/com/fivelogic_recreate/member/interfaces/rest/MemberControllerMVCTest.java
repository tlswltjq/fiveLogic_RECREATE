package com.fivelogic_recreate.member.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivelogic_recreate.member.application.MemberManagementService;
import com.fivelogic_recreate.member.application.command.dto.MemberInfo;
import com.fivelogic_recreate.member.application.query.dto.MemberResponse;
import com.fivelogic_recreate.member.exception.EmailDuplicationException;
import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import com.fivelogic_recreate.member.interfaces.rest.dto.CreateMemberRequest;
import com.fivelogic_recreate.member.interfaces.rest.dto.UpdateMemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
//@WebMvcTest({MemberController.class, MemberControllerAdvice.class})
@DisplayName("MemberController MVC 테스트")
class MemberControllerMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberManagementService memberManagementService;

    @Test
    @DisplayName("회원 생성 API 호출 성공")
    void createMember_success() throws Exception {
        CreateMemberRequest request = new CreateMemberRequest("testuser", "password123", "test@example.com", "길동", "홍", "테스트닉", "안녕하세요");
        MemberInfo mockMemberInfo = new MemberInfo(
                1L, "testuser", "password123", "홍 길동", "테스트닉",
                "MENTEE", true, "test@example.com", "안녕하세요"
        );

        when(memberManagementService.createMember(any())).thenReturn(mockMemberInfo);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("201"))
                .andExpect(jsonPath("$.message").value("사용자 생성 완료"))
                .andExpect(jsonPath("$.data.userId").value("testuser"));
    }

    @Test
    @DisplayName("회원 단일 조회 API 호출 성공")
    void getMember_success() throws Exception {
        String userId = "testuser";
        MemberResponse mockMemberResponse = new MemberResponse(
                userId, "test@example.com", "홍 길동", "테스트닉",
                "MENTEE", "안녕하세요", true
        );

        when(memberManagementService.getByUserId(userId)).thenReturn(mockMemberResponse);

        mockMvc.perform(get("/api/members/{userId}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.data.userId").value(userId));
    }

    @Test
    @DisplayName("회원 전체 조회 API 호출 성공")
    void getAllMembers_success() throws Exception {
        List<MemberResponse> mockMemberList = List.of(
                new MemberResponse("user1", "user1@example.com", "김길동", "김닉", "MENTEE", "안녕하세요1", true),
                new MemberResponse("user2", "user2@example.com", "이길동", "이닉", "MENTO", "안녕하세요2", true)
        );

        when(memberManagementService.getAll()).thenReturn(mockMemberList);

        mockMvc.perform(get("/api/members")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.data.memberList.size()").value(2))
                .andExpect(jsonPath("$.data.memberList[0].userId").value("user1"));
    }

    @Test
    @DisplayName("회원 정보 수정 API 호출 성공")
    void updateMemberInfo_success() throws Exception {
        String userId = "testuser";
        UpdateMemberRequest request = new UpdateMemberRequest("new@example.com", "새", "이름", "새닉", "새로운 바이오", "MENTO");
        MemberInfo mockUpdatedMemberInfo = new MemberInfo(
                1L, userId, "password123", "새 이름", "새닉",
                "MENTO", true, "new@example.com", "새로운 바이오"
        );

        when(memberManagementService.updateMember(any(String.class), any(UpdateMemberRequest.class))).thenReturn(mockUpdatedMemberInfo);

        mockMvc.perform(put("/api/members/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.message").value("수정완료"))
                .andExpect(jsonPath("$.data.userId").value(userId))
                .andExpect(jsonPath("$.data.email").value("new@example.com"));
    }

    @Test
    @DisplayName("회원 삭제 API 호출 성공")
    void deleteMember_success() throws Exception {
        String userId = "testuser";
        MemberInfo mockDeletedMemberInfo = new MemberInfo(
                1L, userId, "password123", "홍 길동", "테스트닉",
                "MENTEE", false, "test@example.com", "안녕하세요"
        );

        when(memberManagementService.deleteMember(any(String.class))).thenReturn(mockDeletedMemberInfo);

        mockMvc.perform(delete("/api/members/{userId}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.message").value(userId + " 삭제완료"));
    }

    @Test
    @DisplayName("회원 생성 API 호출 실패 - 이메일 중복")
    void createMember_emailDuplication_fail() throws Exception {
        CreateMemberRequest request = new CreateMemberRequest(
                "testuser", "password123", "dup@example.com",
                "길동", "홍", "테스트닉", "안녕하세요"
        );

        when(memberManagementService.createMember(any()))
                .thenThrow(new EmailDuplicationException());

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value("409"))
                .andExpect(jsonPath("$.message").value("이미 사용중인 Email 입니다"));
    }

    @Test
    @DisplayName("회원 단일 조회 API 호출 실패 - 존재하지 않는 사용자")
    void getMember_notFound_fail() throws Exception {
        String userId = "unknownUser";

        when(memberManagementService.getByUserId(userId))
                .thenThrow(new MemberNotFoundException());

        mockMvc.perform(get("/api/members/{userId}", userId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("404"))
                .andExpect(jsonPath("$.message").value("사용자를 찾을 수 없습니다"));
    }

    @Test
    @DisplayName("회원 정보 수정 API 호출 실패 - 잘못된 요청 데이터")
    void updateMember_invalidRequest_fail() throws Exception {
        String userId = "testuser";

        UpdateMemberRequest badRequest = new UpdateMemberRequest(
                "", "새", "이름", "새닉", "바이오", "MENTO"
        );

        mockMvc.perform(put("/api/members/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.message").exists());
    }

}