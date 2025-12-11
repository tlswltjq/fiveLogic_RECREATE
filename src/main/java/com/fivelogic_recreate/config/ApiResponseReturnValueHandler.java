package com.fivelogic_recreate.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivelogic_recreate.common.rest.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ApiResponseReturnValueHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return ApiResponse.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(
            Object returnValue,
            MethodParameter returnType,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest) throws Exception {

        mavContainer.setRequestHandled(true);

        ApiResponse<?> response = (ApiResponse<?>) returnValue;

        HttpServletResponse servletResponse = webRequest.getNativeResponse(HttpServletResponse.class);

        HttpStatus httpStatus = HttpStatus.resolve(response.getStatus());
        if (httpStatus == null) httpStatus = HttpStatus.OK;

        servletResponse.setStatus(httpStatus.value());
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(servletResponse.getWriter(), response);
    }

}
