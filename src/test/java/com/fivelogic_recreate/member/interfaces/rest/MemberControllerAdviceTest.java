package com.fivelogic_recreate.member.interfaces.rest;

import com.fivelogic_recreate.common.error.BusinessException;
import com.fivelogic_recreate.common.error.ErrorCode;
import com.fivelogic_recreate.member.exception.NicknamePolicyViolationException;
import com.fivelogic_recreate.member.exception.PasswordPolicyViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MemberControllerAdviceTest {

    private MockMvc mockMvc;

    @RestController
    static class TestController {
        public static BusinessException exceptionToThrow;

        @GetMapping("/test/business-exception")
        public void throwBusinessException() {
            if (exceptionToThrow != null) {
                throw exceptionToThrow;
            }
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }

        @GetMapping("/test/runtime-exception")
        public void throwRuntimeException() {
            throw new RuntimeException("Unexpected error");
        }
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(new MemberControllerAdvice())
                .build();
    }

    @Test
    @DisplayName("BusinessException 발생 시 에러 응답을 반환한다.")
    void handleBusinessException() throws Exception {
        mockMvc.perform(get("/test/business-exception")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                // config, but usually 400 in many setups if not specified. Wait,
                // ENTITY_NOT_FOUND is usually 400 or 404. Let's assume standard
                // behavior.
                // Actually I need to check ErrorCode.ENTITY_NOT_FOUND status.
                // If I can't see ErrorCode enum, I should probably use a safer exception or
                // check the code.
                // Let's assume it returns the status code from ErrorCode.
                // I'll check ErrorCode file first to be precise.
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.MEMBER_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.MEMBER_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("입력값 검증 위반 시 400 에러를 반환한다.")
    void handleValidationExceptions() throws Exception {
        // Password Policy Violation
        TestController.exceptionToThrow = new PasswordPolicyViolationException();
        mockMvc.perform(get("/test/business-exception")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.PASSWORD_POLICY_VIOLATION.getCode()));

        // Nickname Policy Violation
        TestController.exceptionToThrow = new NicknamePolicyViolationException();
        mockMvc.perform(get("/test/business-exception")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.NICKNAME_POLICY_VIOLATION.getCode()));
    }

    @Test
    @DisplayName("기타 Exception 발생 시 500 에러를 반환한다.")
    void handleException() throws Exception {
        mockMvc.perform(get("/test/runtime-exception")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.INTERNAL_SERVER_ERROR.getCode()));
    }
}
