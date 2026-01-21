package com.fivelogic_recreate.member.exception;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;

public class MemberNotActivatedException extends BusinessException {

    public MemberNotActivatedException() {
        super(ErrorCode.MEMBER_NOT_ACTIVATED);
    }
}
