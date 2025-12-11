package com.fivelogic_recreate.member.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class EmailDuplicationException extends BusinessException {
    public EmailDuplicationException() {
        super(ErrorCode.EMAIL_DUPLICATION);
    }
}
