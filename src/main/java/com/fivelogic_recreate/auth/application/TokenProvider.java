package com.fivelogic_recreate.auth.application;

import com.fivelogic_recreate.auth.application.dto.AuthTokens;

public interface TokenProvider {
    AuthTokens issueTokens(String userId, String role);
}
