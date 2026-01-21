package com.fivelogic_recreate.member.interfaces.rest;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;
import com.fivelogic_recreate.common.rest.ApiResponse;
import com.fivelogic_recreate.member.application.command.*;
import com.fivelogic_recreate.member.application.command.dto.*;
import com.fivelogic_recreate.member.application.query.GetMemberDetailService;
import com.fivelogic_recreate.member.application.query.GetMemberDetailsByConditionService;
import com.fivelogic_recreate.member.application.query.MyInfoService;
import com.fivelogic_recreate.member.application.query.dto.GetAllMemberDetailsByConditionResult;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsByTypeCommand;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsResult;
import com.fivelogic_recreate.member.application.query.dto.MemberDetail;
import com.fivelogic_recreate.member.interfaces.rest.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @InjectMocks
    private MemberController memberController;

    @Mock
    private SignUp signUp;
    @Mock
    private GetMemberDetailService getMemberDetailService;
    @Mock
    private GetMemberDetailsByConditionService getMemberDetailsByConditionService;
    @Mock
    private UpdateEmail updateEmail;
    @Mock
    private UpdateInfo updateInfo;
    @Mock
    private Withdraw withdraw;
    @Mock
    private MyInfoService myInfoService;
    @Mock
    private UpdatePassword updatePassword;

    @Test
    @DisplayName("회원가입 요청(POST /api/members)이 성공해야 한다")
    void join_success() {
        // given
        JoinRequest request = new JoinRequest(
                "testuser", "password123", "test@example.com",
                "John", "Doe", "johnny", "Hello");
        SignUpResult result = new SignUpResult("testuser", "John Doe", "johnny", "GENERAL", true,
                "test@example.com", "Hello");

        given(signUp.register(any(SignUpCommand.class))).willReturn(result);

        // when
        ApiResponse<JoinResponse> response = memberController.join(request);

        // then
        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(response.getMessage()).isEqualTo("사용자 생성 완료");
        assertThat(response.getData().userId()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("회원 상세 조회(GET /api/members/{userId})가 성공해야 한다")
    void getMember_success() {
        // given
        GetMemberDetailsResult result = new GetMemberDetailsResult(
                1L, "testuser", "johnny", "GENERAL", "John Doe",
                "test@example.com", "Hello", true);

        given(getMemberDetailService.getDetails(any())).willReturn(result);

        // when
        ApiResponse<MemberResponse> response = memberController.getMemberDetail("testuser");

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().userId()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("전체 회원 조회(GET /api/members)가 성공해야 한다")
    void getMembers_success() {
        // given
        List<MemberDetail> details = List.of(
                new MemberDetail(1L, "testuser", "johnny", "GENERAL", "John Doe", "test@example.com",
                        "Hello", true));
        GetAllMemberDetailsByConditionResult result = new GetAllMemberDetailsByConditionResult(details);

        given(getMemberDetailsByConditionService.byMemberType(any(GetMemberDetailsByTypeCommand.class)))
                .willReturn(result);

        // when
        ApiResponse<MembersResponse> response = memberController.getMemberDetails();

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().memberList().get(0).userId()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("회원 정보 수정(PUT /api/members/{userId})이 성공해야 한다")
    void updateInfo_success() {
        // given
        InfoUpdateRequest request = new InfoUpdateRequest("newNick", "newBio");
        InfoUpdateResult result = new InfoUpdateResult("testuser", "newNick", "newBio");

        given(updateInfo.update(any(InfoUpdateCommand.class))).willReturn(result);

        // when
        ApiResponse<InfoUpdateResponse> response = memberController.updateInfo("testuser", request);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().nickname()).isEqualTo("newNick");
    }

    @Test
    @DisplayName("비밀번호 변경(PUT /api/members/{userId}/password)이 성공해야 한다")
    void changePassword_success() {
        // given
        ChangePasswordRequest request = new ChangePasswordRequest("oldPw", "newPw");
        PasswordUpdateResult result = new PasswordUpdateResult(1L, "testuser");

        given(updatePassword.update(any(PasswordUpdateCommand.class))).willReturn(result);

        // when
        ApiResponse<PasswordUpdateResponse> response = memberController.changePassword("testuser", request);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().userId()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("이메일 변경(PUT /api/members/{userId}/email)이 성공해야 한다")
    void changeEmail_success() {
        // given
        ChangeEmailRequest request = new ChangeEmailRequest("new@example.com");
        EmailUpdateResult result = new EmailUpdateResult("testuser", "new@example.com");

        given(updateEmail.update(any(EmailUpdateCommand.class))).willReturn(result);

        // when
        ApiResponse<EmailUpdateResponse> response = memberController.changeEmail("testuser", request);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().email()).isEqualTo("new@example.com");
    }

    @Test
    @DisplayName("회원 탈퇴(DELETE /api/members/{userId})가 성공해야 한다")
    void withdraw_success() {
        // given
        WithdrawResult result = new WithdrawResult("testuser", "reason");
        given(withdraw.withdraw(any(WithdrawCommand.class))).willReturn(result);

        // when
        ApiResponse<WithdrawResponse> response = memberController.withdraw("testuser");

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().userId()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("내 정보 조회(GET /api/members/me/{userId})가 성공해야 한다")
    void getMyInfo_success() {
        // given
        GetMyProfileResult result = new GetMyProfileResult("nick", "test@example.com", "bio");
        given(myInfoService.getProfile(any(GetMyProfileCommand.class))).willReturn(result);

        // when
        ApiResponse<MemberProfile> response = memberController.getMyInfo("testuser");

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().email()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("존재하지 않는 회원 조회 시 에러를 던져야 한다")
    void getMember_fail_notFound() {
        // given
        given(getMemberDetailService.getDetails(any()))
                .willThrow(new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        // when & then
        assertThatThrownBy(() -> memberController.getMemberDetail("unknown"))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.MEMBER_NOT_FOUND);
    }
}
