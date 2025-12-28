package com.fivelogic_recreate.news.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class NewsAccessDeniedException extends BusinessException {
    public NewsAccessDeniedException() {
        super(ErrorCode.ACCESS_DENIED);
    }
}
