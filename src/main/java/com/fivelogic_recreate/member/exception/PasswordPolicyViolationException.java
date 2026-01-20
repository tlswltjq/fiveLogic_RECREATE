package com.fivelogic_recreate.member.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class PasswordPolicyViolationException extends BusinessException {
    public PasswordPolicyViolationException() {
        super(ErrorCode.PASSWORD_POLICY_VIOLATION);
    }
}
