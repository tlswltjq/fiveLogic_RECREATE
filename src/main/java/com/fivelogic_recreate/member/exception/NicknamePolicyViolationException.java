package com.fivelogic_recreate.member.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class NicknamePolicyViolationException extends BusinessException {
    public NicknamePolicyViolationException() {
        super(ErrorCode.NICKNAME_POLICY_VIOLATION);
    }
}
