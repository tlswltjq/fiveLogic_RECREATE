package com.fivelogic_recreate.member.interfaces.rest;

import com.fivelogic_recreate.member.application.MemberCreateService;
import com.fivelogic_recreate.member.application.MemberDeleteService;
import com.fivelogic_recreate.member.application.MemberUpdateService;
import com.fivelogic_recreate.member.application.command.dto.MemberDeleteCommand;
import com.fivelogic_recreate.member.application.query.MemberQueryService;
import com.fivelogic_recreate.member.application.query.dto.MemberResponse;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.interfaces.rest.common.ApiResponse;
import com.fivelogic_recreate.member.interfaces.rest.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberCreateService memberCreateService;
    private final MemberQueryService memberQueryService;
    private final MemberUpdateService memberUpdateService;
    private final MemberDeleteService memberDeleteService;

    @PostMapping
    public ApiResponse<CreateMemberResponse> createMember(@Valid @RequestBody CreateMemberRequest request) {
        Member member = memberCreateService.create(request.toCommand());
        CreateMemberResponse response = new CreateMemberResponse(member);
        return ApiResponse.success(201, "사용자 생성 완료", response);
    }

    @GetMapping("/{userId}")
    public ApiResponse<GetMemberResponse> getMember(@PathVariable String userId) {
        MemberResponse result = memberQueryService.getByUserId(userId);
        GetMemberResponse response = new GetMemberResponse(result);
        return ApiResponse.success(200, "조회 완료", response);
    }

    @GetMapping
    public ApiResponse<GetAllMembersResponse> getMembers() {
        List<MemberResponse> result = memberQueryService.getAll();
        GetAllMembersResponse response = GetAllMembersResponse.from(result);
        return ApiResponse.success(200, "모든 사용자 조회 완료", response);
    }

    @PutMapping("/{userId}")
    public ApiResponse<UpdateMemberResponse> updateMemberInfo(@PathVariable String userId, @Valid @RequestBody UpdateMemberRequest request) {
        Member result = memberUpdateService.update(request.toCommand(userId));
        UpdateMemberResponse response = new UpdateMemberResponse(result);
        return ApiResponse.success(200, "수정완료", response);
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<DeleteMemberResponse> deleteMember(@PathVariable String userId) {
        Member deleted = memberDeleteService.delete(new MemberDeleteCommand(userId));
        DeleteMemberResponse response = new DeleteMemberResponse(deleted.getUserId().value());
        return ApiResponse.success(200, response.userId() + " 삭제완료", response);
    }
}