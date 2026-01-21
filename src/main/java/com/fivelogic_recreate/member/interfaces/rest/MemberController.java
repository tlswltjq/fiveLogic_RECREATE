package com.fivelogic_recreate.member.interfaces.rest;

import com.fivelogic_recreate.common.rest.ApiResponse;
import com.fivelogic_recreate.member.application.command.*;
import com.fivelogic_recreate.member.application.command.dto.*;
import com.fivelogic_recreate.member.application.query.GetMemberDetailService;
import com.fivelogic_recreate.member.application.query.GetMemberDetailsByConditionService;
import com.fivelogic_recreate.member.application.query.MyInfoService;
import com.fivelogic_recreate.member.application.query.dto.GetAllMemberDetailsByConditionResult;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsByTypeCommand;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsCommand;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsResult;
import com.fivelogic_recreate.member.interfaces.rest.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
        private final SignUp signUp;
        private final GetMemberDetailService getMemberDetailService;
        private final GetMemberDetailsByConditionService getMemberDetailsByConditionService;
        private final UpdateEmail updateEmail;
        private final UpdateInfo updateInfo;
        private final Withdraw withdraw;
        private final MyInfoService myInfoService;
        private final UpdatePassword updatePassword;

        @PostMapping
        public ApiResponse<JoinResponse> join(@Valid @RequestBody JoinRequest request) {
                SignUpCommand signUpRequest = new SignUpCommand(request.userId(), request.password(), request.email(),
                                request.firstname(), request.lastname(), request.nickname(), request.bio());
                SignUpResult result = signUp.register(signUpRequest);
                JoinResponse response = new JoinResponse(result.userId(), result.name(), result.email());

                return ApiResponse.success(201, "사용자 생성 완료", response);
        }

        @GetMapping("/{userId}")
        public ApiResponse<MemberResponse> getMemberDetail(@PathVariable String userId) {
                GetMemberDetailsCommand getMemberDetailRequest = new GetMemberDetailsCommand(userId);
                GetMemberDetailsResult result = getMemberDetailService.getDetails(getMemberDetailRequest);
                MemberResponse response = new MemberResponse(result.memberId(), result.userId(), result.nickname(),
                                result.memberType(), result.name(), result.email(), result.bio(), result.isActive());

                return ApiResponse.success(200, "조회 완료", response);
        }

        @GetMapping
        public ApiResponse<MembersResponse> getMemberDetails() {
                GetAllMemberDetailsByConditionResult result = getMemberDetailsByConditionService
                                .byMemberType(new GetMemberDetailsByTypeCommand("GENERAL"));
                List<MemberResponse> list = result.memberDetails().stream()
                                .map((detail) -> new MemberResponse(detail.memberId(), detail.userId(),
                                                detail.nickname(),
                                                detail.memberType(), detail.name(), detail.email(), detail.bio(),
                                                detail.isActive()))
                                .toList();
                MembersResponse response = new MembersResponse(list);

                return ApiResponse.success(200, "모든 사용자 조회 완료", response);
        }

        @PutMapping("/{userId}")
        public ApiResponse<InfoUpdateResponse> updateInfo(@PathVariable String userId,
                        @AuthenticationPrincipal UserDetails user,
                        @Valid @RequestBody InfoUpdateRequest request) {
                validateUser(userId, user);
                InfoUpdateResult infoUpdateResult = updateInfo
                                .update(new InfoUpdateCommand(userId, request.nickname(), request.bio()));
                InfoUpdateResponse response = new InfoUpdateResponse(userId, infoUpdateResult.nickname(),
                                infoUpdateResult.bio());

                return ApiResponse.success(200, "수정완료", response);
        }

        @DeleteMapping("/{userId}")
        public ApiResponse<WithdrawResponse> withdraw(@PathVariable String userId,
                        @AuthenticationPrincipal UserDetails user) {
                validateUser(userId, user);
                WithdrawResult result = withdraw.withdraw(new WithdrawCommand(userId, "회원 탈퇴 요청"));
                WithdrawResponse response = new WithdrawResponse(result.userId());

                return ApiResponse.success(200, response.userId() + " 삭제완료", response);
        }

        @GetMapping("/me")
        public ApiResponse<MemberProfile> getMyInfo(@AuthenticationPrincipal UserDetails user) {
                GetMyProfileResult profile = myInfoService.getProfile(new GetMyProfileCommand(user.getUsername()));
                MemberProfile response = new MemberProfile(profile.nickname(), profile.email(), profile.bio());
                return ApiResponse.success(200, "내 정보 조회 완료", response);
        }

        @PutMapping("/{userId}/password")
        public ApiResponse<PasswordUpdateResponse> changePassword(@PathVariable String userId,
                        @AuthenticationPrincipal UserDetails user,
                        @Valid @RequestBody ChangePasswordRequest request) {
                validateUser(userId, user);
                PasswordUpdateResult result = updatePassword
                                .update(new PasswordUpdateCommand(userId, request.currentPassword(),
                                                request.newPassword()));
                PasswordUpdateResponse response = new PasswordUpdateResponse(result.userId());
                return ApiResponse.success(200, "비밀번호 변경 완료", response);
        }

        @PutMapping("/{userId}/email")
        public ApiResponse<EmailUpdateResponse> changeEmail(@PathVariable String userId,
                        @AuthenticationPrincipal UserDetails user,
                        @Valid @RequestBody ChangeEmailRequest request) {
                validateUser(userId, user);
                EmailUpdateResult result = updateEmail.update(new EmailUpdateCommand(userId, request.newEmail()));
                EmailUpdateResponse response = new EmailUpdateResponse(result.userId(), result.email());
                return ApiResponse.success(200, "이메일 변경 완료", response);
        }

        private void validateUser(String userId, UserDetails user) {
                if (!userId.equals(user.getUsername())) {
                        throw new com.fivelogic_recreate.common.error.BusinessException(
                                        com.fivelogic_recreate.common.error.ErrorCode.ACCESS_DENIED);
                }
        }
}