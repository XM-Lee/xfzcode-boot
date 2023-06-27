package com.xfzcode.config.security;

import com.xfzcode.constants.ApiVersion;
import com.xfzcode.filter.AccessDeniedHandlerImpl;
import com.xfzcode.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @Author: XMLee
 * @Date: 2023/6/27 15:51
 * @Description: Spring Security 相关配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder() ;
    }

    // 配置Spring Security的拦截规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        HttpSecurity security = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
        // 设置所有接口的权限控制，针对部分接口直接可以去 Api Version 进行相关的配置进行免权限校验
        ApiVersion.ANT_WHITE_LIST.forEach(httpMethodStringPair -> {
            try {
                security.authorizeRequests().antMatchers(httpMethodStringPair.getKey(), httpMethodStringPair.getValue()).permitAll();
            } catch (Exception exception) {
                exception.printStackTrace();
                assert false;
            }
        });
        ApiVersion.REG_WHITE_LIST.forEach(httpMethodStringPair -> {
            try {
                security.authorizeRequests().antMatchers(httpMethodStringPair.getKey(), httpMethodStringPair.getValue()).permitAll();
            } catch (Exception e) {
                e.printStackTrace();
                assert false;
            }
        });                              // 除了上面的请求以外所有的请求全部需要认证
        security.anonymous();
        security.authorizeRequests().anyRequest().authenticated().and().addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        security.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);
        security.cors();
        security.httpBasic().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
