package com.fivelogic_recreate.auth.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivelogic_recreate.auth.application.Login;
import com.fivelogic_recreate.auth.application.Logout;
import com.fivelogic_recreate.auth.application.Reissue;
import com.fivelogic_recreate.auth.application.dto.LoginResult;
import com.fivelogic_recreate.auth.application.dto.LogoutResult;
import com.fivelogic_recreate.auth.application.dto.ReissueCommand;
import com.fivelogic_recreate.auth.interfaces.rest.dto.LogoutRequest;
import com.fivelogic_recreate.auth.interfaces.rest.dto.ReissueRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private Login login;

    @MockBean
    private Reissue reissue;

    @MockBean
    private Logout logout;

    // We excluded SecurityConfig, so we might need to disable security or mock it
    // manually?
    // @WebMvcTest by default includes Spring Security auto-configuration.
    // If we exclude our custom SecurityConfig, the default might kick in (login
    // form etc).
    // Usually it's better to Include config and Mock dependencies, OR use
    // @AutoConfigureMockMvc(addFilters = false)

    @Test
    @DisplayName("토큰 재발급 성공")
    @WithMockUser
    void reissue_success() throws Exception {
        // given
        ReissueRequest request = new ReissueRequest("valid_refresh_token");
        LoginResult result = new LoginResult("user1", "new_access", "new_refresh");

        given(reissue.execute(any(ReissueCommand.class))).willReturn(result);

        // when & then
        mockMvc.perform(post("/api/auth/reissue")
                .with(csrf()) // if csrf enabled
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").value("new_access"))
                .andExpect(jsonPath("$.data.refreshToken").value("new_refresh"));
    }

    @Test
    @DisplayName("로그아웃 성공")
    @WithMockUser
    void logout_success() throws Exception {
        // given
        LogoutRequest request = new LogoutRequest("valid_refresh_token");
        LogoutResult result = new LogoutResult(true);

        given(logout.execute(any(String.class))).willReturn(result);

        // when & then
        mockMvc.perform(post("/api/auth/logout")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.success").value(true));
    }
}
