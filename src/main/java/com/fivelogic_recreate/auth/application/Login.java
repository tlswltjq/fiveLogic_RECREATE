package com.fivelogic_recreate.auth.application;

import com.fivelogic_recreate.auth.application.dto.AuthTokens;
import com.fivelogic_recreate.auth.application.dto.LoginCommand;
import com.fivelogic_recreate.auth.application.dto.LoginResult;
import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.UserPassword;
import com.fivelogic_recreate.member.exception.MemberNotActivatedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class Login {
    private final MemberReader memberReader;
    private final TokenProvider tokenProvider;

    public LoginResult execute(LoginCommand command) {
        String userId = command.userId();
        String password = command.password();
        Member member = memberReader.getMember(userId);

        if (!member.isActivate()) {
            throw new MemberNotActivatedException();
        }
        member.checkPassword(new UserPassword(password));

        AuthTokens authTokens = tokenProvider.issueTokens(member.getUserId().value(), member.getMemberType().name());

        return new LoginResult(member.getUserId().value(), authTokens.accessToken(), authTokens.refreshToken());
    }
}
