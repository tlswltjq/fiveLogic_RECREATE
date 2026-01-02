package com.fivelogic_recreate.member.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class PasswordMismatchException extends BusinessException {
    public PasswordMismatchException() {
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}
