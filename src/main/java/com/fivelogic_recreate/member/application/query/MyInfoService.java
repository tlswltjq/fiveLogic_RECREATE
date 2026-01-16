package com.fivelogic_recreate.member.application.query;

import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.application.command.dto.GetMyProfileResult;
import com.fivelogic_recreate.member.application.query.dto.MyProfile;
import com.fivelogic_recreate.member.application.command.dto.GetMyProfileCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyInfoService {
    private final MemberReader memberReader;

    public GetMyProfileResult getProfile(GetMyProfileCommand command) {
        MyProfile memberProfile = memberReader.getMemberProfile(command.userId());

        return GetMyProfileResult.from(memberProfile);
    }
}
