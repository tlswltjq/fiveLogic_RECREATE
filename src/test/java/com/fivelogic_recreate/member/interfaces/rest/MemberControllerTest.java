package com.fivelogic_recreate.member.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivelogic_recreate.member.application.command.*;
import com.fivelogic_recreate.member.application.command.dto.*;
import com.fivelogic_recreate.member.application.query.GetMemberDetailService;
import com.fivelogic_recreate.member.application.query.GetMemberDetailsByConditionService;
import com.fivelogic_recreate.member.application.query.MyInfoService;
import com.fivelogic_recreate.member.application.query.dto.GetAllMemberDetailsByConditionResult;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsResult;
import com.fivelogic_recreate.member.application.query.dto.MemberDetail;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsByTypeCommand;
import com.fivelogic_recreate.member.interfaces.rest.dto.ChangeEmailRequest;
import com.fivelogic_recreate.member.interfaces.rest.dto.ChangePasswordRequest;
import com.fivelogic_recreate.member.interfaces.rest.dto.InfoUpdateRequest;
import com.fivelogic_recreate.member.interfaces.rest.dto.JoinRequest;
import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private SignUpService signUpService;
        @MockBean
        private GetMemberDetailService getMemberDetailService;
        @MockBean
        private GetMemberDetailsByConditionService getMemberDetailsByConditionService;
        @MockBean
        private EmailUpdateService emailUpdateService;
        @MockBean
        private InfoUpdateService infoUpdateService;
        @MockBean
        private WithdrawService withdrawService;
        @MockBean
        private MyInfoService myInfoService;
        @MockBean
        private PasswordUpdateService passwordUpdateService;

        @Test
        @DisplayName("회원가입 요청(POST /api/members)이 성공해야 한다")
        void join_success() throws Exception {
                JoinRequest request = new JoinRequest(
                                "testuser", "password123", "test@example.com",
                                "John", "Doe", "johnny", "Hello");
                SignUpResult result = new SignUpResult("testuser", "John Doe", "johnny", "GENERAL", true,
                                "test@example.com", "Hello");

                given(signUpService.register(any(SignUpCommand.class))).willReturn(result);

                mockMvc.perform(post("/api/members")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.status").value(201))
                                .andExpect(jsonPath("$.message").value("사용자 생성 완료"))
                                .andExpect(jsonPath("$.data.userId").value("testuser"));
        }

        @Test
        @DisplayName("회원 상세 조회(GET /api/members/{userId})가 성공해야 한다")
        void getMember_success() throws Exception {
                GetMemberDetailsResult result = new GetMemberDetailsResult(
                                1L, "testuser", "johnny", "GENERAL", "John Doe",
                                "test@example.com", "Hello", true);

                given(getMemberDetailService.getDetails(any())).willReturn(result);

                mockMvc.perform(get("/api/members/testuser"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(200))
                                .andExpect(jsonPath("$.data.userId").value("testuser"));
        }

        @Test
        @DisplayName("전체 회원 조회(GET /api/members)가 성공해야 한다")
        void getMembers_success() throws Exception {
                List<MemberDetail> details = List.of(
                                new MemberDetail(1L, "testuser", "johnny", "GENERAL", "John Doe", "test@example.com",
                                                "Hello", true));
                GetAllMemberDetailsByConditionResult result = new GetAllMemberDetailsByConditionResult(details);

                given(getMemberDetailsByConditionService.byMemberType(any(GetMemberDetailsByTypeCommand.class)))
                                .willReturn(result);

                mockMvc.perform(get("/api/members"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(200))
                                .andExpect(jsonPath("$.data.memberList[0].userId").value("testuser"));
        }

        @Test
        @DisplayName("회원 정보 수정(PUT /api/members/{userId})이 성공해야 한다")
        void updateInfo_success() throws Exception {
                InfoUpdateRequest request = new InfoUpdateRequest("newNick", "newBio");
                InfoUpdateResult result = new InfoUpdateResult("testuser", "newNick", "newBio");

                given(infoUpdateService.update(any(InfoUpdateCommand.class))).willReturn(result);

                mockMvc.perform(put("/api/members/testuser")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(200))
                                .andExpect(jsonPath("$.data.nickname").value("newNick"));
        }

        @Test
        @DisplayName("비밀번호 변경(PUT /api/members/{userId}/password)이 성공해야 한다")
        void changePassword_success() throws Exception {
                ChangePasswordRequest request = new ChangePasswordRequest("oldPw", "newPw");
                PasswordUpdateResult result = new PasswordUpdateResult(1L, "testuser");

                given(passwordUpdateService.update(any(PasswordUpdateCommand.class))).willReturn(result);

                mockMvc.perform(put("/api/members/testuser/password")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(200))
                                .andExpect(jsonPath("$.data.userId").value("testuser"));
        }

        @Test
        @DisplayName("이메일 변경(PUT /api/members/{userId}/email)이 성공해야 한다")
        void changeEmail_success() throws Exception {
                ChangeEmailRequest request = new ChangeEmailRequest("new@example.com");
                EmailUpdateResult result = new EmailUpdateResult("testuser", "new@example.com");

                given(emailUpdateService.update(any(EmailUpdateCommand.class))).willReturn(result);

                mockMvc.perform(put("/api/members/testuser/email")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(200))
                                .andExpect(jsonPath("$.data.email").value("new@example.com"));
        }

        @Test
        @DisplayName("회원 탈퇴(DELETE /api/members/{userId})가 성공해야 한다")
        void withdraw_success() throws Exception {
                WithdrawResult result = new WithdrawResult("testuser", "reason");
                given(withdrawService.withdraw(any(WithdrawCommand.class))).willReturn(result);

                mockMvc.perform(delete("/api/members/testuser"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(200))
                                .andExpect(jsonPath("$.data.userId").value("testuser"));
        }

        @Test
        @DisplayName("내 정보 조회(GET /api/members/me/{userId})가 성공해야 한다")
        void getMyInfo_success() throws Exception {
                GetMyProfileResult result = new GetMyProfileResult("nick", "test@example.com", "bio");
                given(myInfoService.getProfile(any())).willReturn(result);

                mockMvc.perform(get("/api/members/me/testuser"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(200))
                                .andExpect(jsonPath("$.data.email").value("test@example.com"));
        }

        @Test
        @DisplayName("회원가입 요청 시 유효성 검사에 실패하면 400 에러를 반환한다")
        void join_fail_invalid_input() throws Exception {
                JoinRequest request = new JoinRequest(
                                "", // Invalid userId (empty)
                                "weak",
                                "invalid-email",
                                "", "", "", "");

                mockMvc.perform(post("/api/members")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.status").value(400));
        }

        @Test
        @DisplayName("존재하지 않는 회원 조회 시 404 에러를 반환한다")
        void getMember_fail_notFound() throws Exception {
                given(getMemberDetailService.getDetails(any()))
                                .willThrow(new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

                mockMvc.perform(get("/api/members/unknown"))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.status").value(404))
                                .andExpect(jsonPath("$.errorCode").value("M001"));
        }
}
