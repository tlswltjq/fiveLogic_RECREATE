package com.fivelogic_recreate.news.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class NewsDeleteNotAllowedException extends BusinessException {
    public NewsDeleteNotAllowedException() {
        super(ErrorCode.NEWS_DELETE_NOT_ALLOWED);
    }
}
