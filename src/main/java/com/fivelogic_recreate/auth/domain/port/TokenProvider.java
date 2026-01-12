package com.fivelogic_recreate.auth.domain.port;

import com.fivelogic_recreate.auth.domain.model.AuthUserId;
import com.fivelogic_recreate.auth.domain.model.Expiration;

public interface TokenProvider {
    Expiration getExpiration();
}
