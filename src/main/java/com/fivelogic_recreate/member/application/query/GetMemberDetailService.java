package com.fivelogic_recreate.member.application.query;

import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsCommand;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsResult;
import com.fivelogic_recreate.member.application.query.dto.MemberDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetMemberDetailService {
    private final MemberReader memberReader;

    public GetMemberDetailsResult getDetails(GetMemberDetailsCommand command) {
        MemberDetail memberDetail = memberReader.getDetail(command.userId());

        return GetMemberDetailsResult.from(memberDetail);
    }
}
