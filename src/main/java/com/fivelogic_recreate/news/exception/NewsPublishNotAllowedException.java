package com.fivelogic_recreate.news.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class NewsPublishNotAllowedException extends BusinessException {
    public NewsPublishNotAllowedException() {
        super(ErrorCode.NEWS_PUBLISH_NOT_ALLOWED);
    }
}
