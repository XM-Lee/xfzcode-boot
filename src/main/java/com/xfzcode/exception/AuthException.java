package com.xfzcode.exception;

import com.xfzcode.constants.ResultMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
public class AuthException extends RuntimeException {

    private Integer status = UNAUTHORIZED.value();

    public AuthException(String msg) {
        super(msg);
    }

    public AuthException(HttpStatus status, String msg) {
        super(msg);
        this.status = status.value();
    }

    public static AuthException usernameOrPasswordInvalid() {
        return new AuthException(ResultMessage.LOGIN_ERROR);
    }

    public static AuthException invalidLoginType() {
        return new AuthException("登录方式不支持");
    }

    public static AuthException invalidToken() {
        return new AuthException(ResultMessage.ERROR_TOKEN);
    }

    public static AuthException accountLocked() {
        return new AuthException(ResultMessage.ACCOUNT_LOCKED);
    }

    public static AuthException accountNotEnabled() {
        return new AuthException(ResultMessage.ACCOUNT_NOT_ENABLE);
    }

    public static AuthException accountExpired() {
        return new AuthException("账号已过期");
    }

    public static AuthException passwordExpired() {
        return new AuthException("密码已过期");
    }
}
