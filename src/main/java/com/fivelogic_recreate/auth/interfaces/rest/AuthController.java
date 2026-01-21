package com.fivelogic_recreate.auth.interfaces.rest;

import com.fivelogic_recreate.auth.application.Login;
import com.fivelogic_recreate.auth.application.Logout;
import com.fivelogic_recreate.auth.application.Reissue;
import com.fivelogic_recreate.auth.application.dto.*;
import com.fivelogic_recreate.auth.interfaces.rest.dto.*;
import com.fivelogic_recreate.common.rest.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final Login login;
    private final Reissue reissue;
    private final Logout logout;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResult result = login.execute(new LoginCommand(request.userId(), request.password()));
        return ApiResponse.success(200, "로그인 성공", new LoginResponse(result.accessToken(), result.refreshToken()));
    }

    @PostMapping("/reissue")
    public ApiResponse<ReissueResponse> reissue(@Valid @RequestBody ReissueRequest request) {
        LoginResult result = reissue.execute(new ReissueCommand(request.refreshToken()));
        return ApiResponse.success(200, "토큰 재발급 성공", new ReissueResponse(result.accessToken(), result.refreshToken()));
    }

    @PostMapping("/logout")
    public ApiResponse<LogoutResponse> logout(@Valid @RequestBody LogoutRequest request) {
        LogoutResult result = logout.execute(request.refreshToken());
        return ApiResponse.success(200, "로그아웃 성공", new LogoutResponse(result.success()));
    }
}
