package com.fivelogic_recreate.news.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class NewsHideNotAllowedException extends BusinessException {
    public NewsHideNotAllowedException() {
        super(ErrorCode.NEWS_HIDE_NOT_ALLOWED);
    }
}
