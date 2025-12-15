package com.fivelogic_recreate.news.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class NewsUnHideNotAllowedException extends BusinessException {
    public NewsUnHideNotAllowedException() {
        super(ErrorCode.NEWS_UN_HIDE_NOT_ALLOWED);
    }
}
