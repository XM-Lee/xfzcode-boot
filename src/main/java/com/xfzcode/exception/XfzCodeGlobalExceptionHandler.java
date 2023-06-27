package com.xfzcode.exception;

import com.xfzcode.constants.HttpResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: XMLee
 * @Date: 2022/10/25 18:35
 * @Description:
 */
@RestControllerAdvice
public class XfzCodeGlobalExceptionHandler {
    @ExceptionHandler({XfzCodeException.class,})
    public HttpResult handleHttpMessageNotReadableException(HttpServletRequest request, HttpServletResponse response, Exception e) {

        return HttpResult.failure(response.getStatus(),request.getRequestURI(),e.getMessage());

    }
}

