package com.fivelogic_recreate.member.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class InvalidSearchConditionException extends BusinessException {
    public InvalidSearchConditionException() {
        super(ErrorCode.INVALID_SEARCH_CONDITION);
    }
}
