package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.member.application.command.dto.*;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsByTypeCommand;
import com.fivelogic_recreate.member.domain.model.Email;
import com.fivelogic_recreate.member.domain.model.UserId;
import com.fivelogic_recreate.member.domain.port.MemberQueryRepositoryPort;
import com.fivelogic_recreate.member.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class MemberServicePolicyValidator {
    private static final Pattern PASSWORD_PATTERN = Pattern
            .compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");
    private static final String[] BANNED_NICKNAMES = { "admin", "administrator", "운영자", "관리자" };
    private final MemberQueryRepositoryPort queryRepository;

    private void validatePasswordPolicy(String password) {
        if (password == null || !PASSWORD_PATTERN.matcher(password).matches()) {
            throw new PasswordPolicyViolationException();
        }
    }

    private void validateNicknamePolicy(String nickname) {
        for (String banned : BANNED_NICKNAMES) {
            if (nickname.contains(banned)) {
                throw new NicknamePolicyViolationException();
            }
        }
    }

    private void verifyUserIdDuplication(String userId) {
        if (queryRepository.existsByUserId(new UserId(userId))) {
            throw new UserIdDuplicationException();
        }
    }

    private void verifyEmailDuplication(String email) {
        if (queryRepository.existsByEmail(new Email(email))) {
            throw new EmailDuplicationException();
        }
    }

    private void validateUserIdFormat(String userId) {
        if (userId == null || userId.length() < 5 || userId.length() > 20) {
            throw new UserIdFormatException();
        }
    }

    private void validateBioPolicy(String bio) {
        if (bio != null && (bio.isBlank() || bio.length() > 500)) {
            throw new BioPolicyViolationException();
        }
    }

    public void checkInfoUpdatePolicy(InfoUpdateCommand command) {
        validateNicknamePolicy(command.nickname());
        validateBioPolicy(command.bio());
    }

    public void checkSignUpPolicy(SignUpCommand command) {
        validateUserIdFormat(command.userId());
        verifyUserIdDuplication(command.userId());
        verifyEmailDuplication(command.email());
        validatePasswordPolicy(command.password());
        validateNicknamePolicy(command.nickname());
        validateBioPolicy(command.bio());
    }

    public void checkPasswordUpdatePolicy(PasswordUpdateCommand command) {
        validatePasswordPolicy(command.newPassword());
    }

    public void checkEmailUpdatePolicy(EmailUpdateCommand command) {
        verifyEmailDuplication(command.email());
    }

    public void checkRetrieveDetailsPolicy(GetMemberDetailsByTypeCommand command) {
        String targetType = command.targetType();
        if (targetType == null ||
                (!targetType.equals("MENTOR") &&
                        !targetType.equals("MENTEE") &&
                        !targetType.equals("ADMIN") &&
                        !targetType.equals("GENERAL"))) {
            throw new InvalidSearchConditionException();
        }
    }

    public void checkWithdrawPolicy(WithdrawCommand command) {
    }
}
