package com.xfzcode.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xfzcode.pojo.LoginUser;
import com.xfzcode.pojo.User;
import com.xfzcode.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: XMLee
 * @Date: 2023/2/15 16:13
 * @Description:
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String , String> redisTemplate ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1、从请求头中获取token，如果请求头中不存在token，直接放行即可！由Spring Security的过滤器进行校验！
        String token = request.getHeader("token");
        if(token == null || "".equals(token)) {
            filterChain.doFilter(request , response);
            return ;
        }

        // 2、对token进行解析，取出其中的userId
        String userId = null ;
        try {
            DecodedJWT decodedJWT = JwtUtils.decodedRSA(token);
            String rsaDecodeStr = decodedJWT.getToken();
            DecodedJWT decode = JWT.decode(rsaDecodeStr);
            userId= decode.getClaim("userId").asString();
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法") ;
        }

        // 3、使用userId从redis中查询对应的LoginUser对象
        LoginUser loginUser = null;
        try {
            loginUser = new LoginUser();
            String loginUserJson = redisTemplate.boundValueOps("login_user:" + userId).get();
            JSONObject jsonObject = JSONObject.parseObject(loginUserJson);
            User user = jsonObject.getObject("user", User.class);
            loginUser.setUser(user);
            JSONArray array = jsonObject.getJSONArray("permissions");
            List<String> permissions = array.toJavaList(String.class);
            loginUser.setPermissions(permissions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(loginUser != null) {
            // 4、然后将查询到的LoginUser对象的相关信息封装到UsernamePasswordAuthenticationToken对象中，然后将该对象存储到Security的上下文对象中
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null , loginUser.getAuthorities()) ;
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // 5、放行
        filterChain.doFilter(request , response);
    }

}
