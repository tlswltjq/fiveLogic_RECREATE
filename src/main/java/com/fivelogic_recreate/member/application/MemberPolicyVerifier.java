package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.member.application.query.MemberQueryService;
import com.fivelogic_recreate.member.exception.EmailDuplicationException;
import com.fivelogic_recreate.member.exception.UserIdDuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class MemberPolicyVerifier {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");
    private static final String[] BANNED_NICKNAMES = {"admin", "administrator", "운영자", "관리자"};
    private final MemberQueryService memberQueryService;

    public void validateSignUpPolicy(String email, String password, String nickname) {
        validatePasswordPolicy(password);
        validateNicknamePolicy(nickname);
        verifyEmailDuplication(email);
    }

    public void validatePasswordPolicy(String password) {
        if (password == null || !PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.");
        }
    }

    public void validateNicknamePolicy(String nickname) {
        for (String banned : BANNED_NICKNAMES) {
            if (nickname.contains(banned)) {
                throw new IllegalArgumentException("사용할 수 없는 닉네임이 포함되어 있습니다.");
            }
        }
    }

    public void verifyUserIdDuplication(String userId) {
        if (memberQueryService.existsByUserId(userId)) {
            throw new UserIdDuplicationException();
        }
    }

    public void verifyEmailDuplication(String email) {
        if (memberQueryService.existsByEmail(email)) {
            throw new EmailDuplicationException();
        }
    }
}
