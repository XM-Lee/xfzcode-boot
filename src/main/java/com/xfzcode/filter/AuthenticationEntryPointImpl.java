package com.xfzcode.filter;

import com.alibaba.fastjson.JSON;
import com.xfzcode.constants.HttpResult;
import com.xfzcode.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: XMLee
 * @Date: 2023/2/15 16:27
 * @Description:
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //ResponseResult result = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "认证失败请重新登录", null);
        String json = JSON.toJSONString(HttpResult.failure(HttpStatus.UNAUTHORIZED.value(), "认证失败请重新登录", null)) ;
        WebUtils.renderString(response,json);
    }

}
