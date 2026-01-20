package com.fivelogic_recreate.member.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class BioPolicyViolationException extends BusinessException {
    public BioPolicyViolationException() {
        super(ErrorCode.BIO_POLICY_VIOLATION);
    }
}
