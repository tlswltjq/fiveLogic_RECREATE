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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final SignUpService signUpService;
    private final GetMemberDetailService getMemberDetailService;
    private final GetMemberDetailsByConditionService getMemberDetailsByConditionService;
    private final EmailUpdateService emailUpdateService;
    private final InfoUpdateService infoUpdateService;
    private final WithdrawService withdrawService;
    private final MyInfoService myInfoService;
    private final PasswordUpdateService passwordUpdateService;


    //메서드명들 이후 변경 ex)createMember -> join

    @PostMapping
    public ApiResponse<CreateMemberResponse> createMember(@Valid @RequestBody CreateMemberRequest request) {
        SignUpCommand signUpRequest = new SignUpCommand(request.userId(), request.password(), request.email(), request.firstname(), request.lastname(), request.nickname(), request.bio());
        SignUpResult result = signUpService.register(signUpRequest);
        CreateMemberResponse response = new CreateMemberResponse(result.userId(), result.name(), result.email());

        return ApiResponse.success(201, "사용자 생성 완료", response);
    }

    @GetMapping("/{userId}")
    public ApiResponse<GetMemberResponse> getMember(@PathVariable String userId) {
        GetMemberDetailsCommand getMemberDetailRequest = new GetMemberDetailsCommand(userId);
        GetMemberDetailsResult result = getMemberDetailService.getDetails(getMemberDetailRequest);
        GetMemberResponse response = new GetMemberResponse(result.memberId(), result.userId(), result.nickname(), result.memberType(), result.name(), result.email(), result.bio(), result.isActive());

        return ApiResponse.success(200, "조회 완료", response);
    }

    @GetMapping
    public ApiResponse<GetAllMembersResponse> getMembers() {
        GetAllMemberDetailsByConditionResult result = getMemberDetailsByConditionService.byMemberType(new GetMemberDetailsByTypeCommand("GENERAL"));
        List<GetMemberResponse> list = result.memberDetails().stream()
                .map((detail) ->
                        new GetMemberResponse(detail.memberId(), detail.userId(), detail.nickname(), detail.memberType(), detail.name(), detail.email(), detail.bio(), detail.isActive())
                ).toList();
        GetAllMembersResponse response = new GetAllMembersResponse(list);

        return ApiResponse.success(200, "모든 사용자 조회 완료", response);
    }

    @PutMapping("/{userId}")
    public ApiResponse<UpdateMemberResponse> updateMemberInfo(@PathVariable String userId, @Valid @RequestBody UpdateMemberRequest request) {
        //이메일 업데이트 제거 필요
        EmailUpdateResult emailUpdateResult = emailUpdateService.update(new EmailUpdateCommand(userId, request.email()));
        InfoUpdateResult infoUpdateResult = infoUpdateService.update(new InfoUpdateCommand(userId, request.nickname(), request.bio()));
        UpdateMemberResponse response = new UpdateMemberResponse(userId, emailUpdateResult.email(), infoUpdateResult.nickname(), infoUpdateResult.bio());

        return ApiResponse.success(200, "수정완료", response);
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<DeleteMemberResponse> deleteMember(@PathVariable String userId) {
        WithdrawResult result = withdrawService.withdraw(new WithdrawCommand(userId, "컨트롤러 메서드 수정하면서 바꾸기"));
        DeleteMemberResponse response = new DeleteMemberResponse(result.userId());

        return ApiResponse.success(200, response.userId() + " 삭제완료", response);
    }

    @GetMapping("/me/{userId}")//시큐리티 적용이후 /me 로 변경
    public ApiResponse<MemberProfile> getMyInfo(@PathVariable String userId) {
        GetMyProfileResult profile = myInfoService.getProfile(new GetMyProfileCommand(userId));
        MemberProfile response = new MemberProfile(profile.nickname(), profile.email(), profile.bio());
        return ApiResponse.success(200, "내 정보 조회 완료", response);
    }

    @PutMapping("/{userId}/password")
    public ApiResponse<Void> changePassword(@PathVariable String userId, @Valid @RequestBody ChangePasswordRequest request) {
        passwordUpdateService.update(new PasswordUpdateCommand(userId, request.currentPassword(), request.newPassword()));
        return ApiResponse.success(200, "비밀번호 변경 완료", null);
    }

    @PutMapping("/{userId}/email")
    public ApiResponse<Void> changeEmail(@PathVariable String userId, @Valid @RequestBody ChangeEmailRequest request) {
        emailUpdateService.update(new EmailUpdateCommand(userId, request.newEmail()));
        return ApiResponse.success(200, "이메일 변경 완료", null);
    }
}