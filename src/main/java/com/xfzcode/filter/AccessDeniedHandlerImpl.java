package com.xfzcode.filter;

import com.alibaba.fastjson.JSON;
import com.xfzcode.constants.HttpResult;
import com.xfzcode.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: XMLee
 * @Date: 2023/2/15 16:28
 * @Description:
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String json = JSON.toJSONString(HttpResult.failure(HttpStatus.FORBIDDEN.value(), "权限不足", null));
        WebUtils.renderString(response,json);

    }

}
