package com.fivelogic_recreate.member.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class UserIdDuplicationException extends BusinessException {
    public UserIdDuplicationException() {
        super(ErrorCode.USER_ID_DUPLICATION);
    }
}
