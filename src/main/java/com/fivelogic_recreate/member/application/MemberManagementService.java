package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.member.application.command.MemberCreateService;
import com.fivelogic_recreate.member.application.command.MemberDeleteService;
import com.fivelogic_recreate.member.application.command.MemberUpdateService;
import com.fivelogic_recreate.member.application.command.dto.MemberCreateResult;
import com.fivelogic_recreate.member.application.command.dto.MemberDeleteCommand;
import com.fivelogic_recreate.member.application.command.dto.MemberDeleteResult;
import com.fivelogic_recreate.member.application.command.dto.MemberUpdateResult;
import com.fivelogic_recreate.member.application.query.MemberQueryService;
import com.fivelogic_recreate.member.application.query.dto.MemberQueryResponse;
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

    public MemberCreateResult createMember(CreateMemberRequest request) {
        return memberCreateService.create(request.toCommand());
    }

    public MemberQueryResponse getByUserId(String userId) {
        return memberQueryService.getByUserId(userId);
    }

    public List<MemberQueryResponse> getAll() {
        return memberQueryService.getAll();
    }

    public MemberUpdateResult updateMember(String userId, UpdateMemberRequest request) {
        return memberUpdateService.update(request.toCommand(userId));
    }

    public MemberDeleteResult deleteMember(String userId) {
        return memberDeleteService.delete(new MemberDeleteCommand(userId));
    }
}
