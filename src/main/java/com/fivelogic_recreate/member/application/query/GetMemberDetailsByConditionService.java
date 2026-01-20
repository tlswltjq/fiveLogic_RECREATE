package com.fivelogic_recreate.member.application.query;

import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.application.MemberServicePolicyValidator;
import com.fivelogic_recreate.member.application.query.dto.GetAllMemberDetailsByConditionResult;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsByTypeCommand;
import com.fivelogic_recreate.member.application.query.dto.MemberDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetMemberDetailsByConditionService {
    private final MemberReader memberReader;
    private final MemberServicePolicyValidator policyVerifier;

    public GetAllMemberDetailsByConditionResult byMemberType(GetMemberDetailsByTypeCommand command) {
        policyVerifier.checkRetrieveDetailsPolicy(command);

        List<MemberDetail> details;
        if ("GENERAL".equals(command.targetType())) {
            details = memberReader.getDetailsExceptAdmin();
        } else {
            details = memberReader.getDetailsByType(command.targetType());
        }

        return GetAllMemberDetailsByConditionResult.from(details);
    }

    @Deprecated
    public void bySomeThing() {
    }
}
