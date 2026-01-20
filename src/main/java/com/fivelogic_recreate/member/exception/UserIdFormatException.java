package com.fivelogic_recreate.member.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class UserIdFormatException extends BusinessException {
    public UserIdFormatException() {
        super(ErrorCode.USER_ID_FORMAT_INVALID);
    }
}
