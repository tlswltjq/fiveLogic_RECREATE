package com.fivelogic_recreate.member.application;

import com.fivelogic_recreate.member.application.command.dto.*;
import com.fivelogic_recreate.member.application.query.dto.GetMemberDetailsByTypeCommand;
import com.fivelogic_recreate.member.domain.model.Email;
import com.fivelogic_recreate.member.domain.model.UserId;
import com.fivelogic_recreate.member.domain.port.MemberQueryRepositoryPort;
import com.fivelogic_recreate.member.exception.EmailDuplicationException;
import com.fivelogic_recreate.member.exception.UserIdDuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class MemberServicePolicyValidator {
    private static final Pattern PASSWORD_PATTERN = Pattern
            .compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");
    private static final String[] BANNED_NICKNAMES = {"admin", "administrator", "운영자", "관리자"};
    private final MemberQueryRepositoryPort queryRepository;

    private void validatePasswordPolicy(String password) {
        if (password == null || !PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.");
        }
    }

    private void validateNicknamePolicy(String nickname) {
        for (String banned : BANNED_NICKNAMES) {
            if (nickname.contains(banned)) {
                throw new IllegalArgumentException("사용할 수 없는 닉네임이 포함되어 있습니다.");
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
            throw new IllegalArgumentException("사용자 ID는 5자 이상 20자 이하이어야 합니다.");
        }
    }

    private void validateBioPolicy(String bio) {
        if (bio != null && (bio.isBlank() || bio.length() > 500)) {
            throw new IllegalArgumentException("소개는 500자를 넘거나 공백일 수 없습니다.");
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
            throw new IllegalArgumentException("유효하지 않은 검색 조건입니다: " + targetType);
        }
    }

    public void checkWithdrawPolicy(WithdrawCommand command) {
    }
}
