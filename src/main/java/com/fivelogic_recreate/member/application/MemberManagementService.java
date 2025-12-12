package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.member.application.command.MemberCreateService;
import com.fivelogic_recreate.member.application.command.MemberDeleteService;
import com.fivelogic_recreate.member.application.command.MemberUpdateService;
import com.fivelogic_recreate.member.application.command.dto.MemberDeleteCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberInfo;
import com.fivelogic_recreate.member.application.query.MemberQueryService;
import com.fivelogic_recreate.member.application.query.dto.MemberResponse;
import com.fivelogic_recreate.member.interfaces.rest.dto.CreateMemberRequest;
import com.fivelogic_recreate.member.interfaces.rest.dto.UpdateMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberManagementService {
    private final MemberCreateService memberCreateService;
    private final MemberQueryService memberQueryService;
    private final MemberUpdateService memberUpdateService;
    private final MemberDeleteService memberDeleteService;

    public MemberInfo createMember(CreateMemberRequest request) {
        return memberCreateService.create(request.toCommand());
    }

    public MemberResponse getByUserId(String userId) {
        return memberQueryService.getByUserId(userId);
    }

    public List<MemberResponse> getAll() {
        return memberQueryService.getAll();
    }

    public MemberInfo updateMember(String userId, UpdateMemberRequest request) {
        return memberUpdateService.update(request.toCommand(userId));
    }

    public MemberInfo deleteMember(String userId) {
        return memberDeleteService.delete(new MemberDeleteCommand(userId));
    }
}
