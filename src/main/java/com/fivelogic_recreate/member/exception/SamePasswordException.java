package com.fivelogic_recreate.member.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class SamePasswordException extends BusinessException {
    public SamePasswordException() {
        super(ErrorCode.SAME_PASSWORD);
    }
}
