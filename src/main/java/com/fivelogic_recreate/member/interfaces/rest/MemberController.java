package com.fivelogic_recreate.member.interfaces.rest;

import com.fivelogic_recreate.common.rest.ApiResponse;
import com.fivelogic_recreate.member.application.MemberManagementService;
import com.fivelogic_recreate.member.application.command.dto.MemberCreateResult;
import com.fivelogic_recreate.member.application.command.dto.MemberDeleteResult;
import com.fivelogic_recreate.member.application.command.dto.MemberUpdateResult;
import com.fivelogic_recreate.member.application.query.dto.MemberQueryResponse;
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
    private final MemberManagementService memberManagementService;

    @PostMapping
    public ApiResponse<CreateMemberResponse> createMember(@Valid @RequestBody CreateMemberRequest request) {
        MemberCreateResult createResult = memberManagementService.createMember(request);
        CreateMemberResponse response = new CreateMemberResponse(createResult.userId(), createResult.name(), createResult.email());

        return ApiResponse.success(201, "사용자 생성 완료", response);
    }

    @GetMapping("/{userId}")
    public ApiResponse<GetMemberResponse> getMember(@PathVariable String userId) {
        MemberQueryResponse result = memberManagementService.getByUserId(userId);
        GetMemberResponse response = new GetMemberResponse(result);
        return ApiResponse.success(200, "조회 완료", response);
    }


    @GetMapping
    public ApiResponse<GetAllMembersResponse> getMembers() {
        List<MemberQueryResponse> result = memberManagementService.getAll();
        GetAllMembersResponse response = GetAllMembersResponse.from(result);
        return ApiResponse.success(200, "모든 사용자 조회 완료", response);
    }

    @PutMapping("/{userId}")
    public ApiResponse<UpdateMemberResponse> updateMemberInfo(@PathVariable String userId, @Valid @RequestBody UpdateMemberRequest request) {
        MemberUpdateResult updateResult = memberManagementService.updateMember(userId, request);

        UpdateMemberResponse response = new UpdateMemberResponse(updateResult.userId(), updateResult.email(), updateResult.name(), updateResult.nickname(), updateResult.bio(), updateResult.memberType());
        return ApiResponse.success(200, "수정완료", response);
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<DeleteMemberResponse> deleteMember(@PathVariable String userId) {
        MemberDeleteResult deleteResult = memberManagementService.deleteMember(userId);

        DeleteMemberResponse response = new DeleteMemberResponse(deleteResult.userId());
        return ApiResponse.success(200, response.userId() + " 삭제완료", response);
    }
}