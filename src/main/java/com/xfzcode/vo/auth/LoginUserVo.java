package com.xfzcode.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wildfly.common.annotation.NotNull;

import java.io.Serializable;
/**
 * @Author: XMLee
 * @Date: 2023/4/11 15:17
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVo implements Serializable {
    private String username;
    @NotNull
    private String password;

    @NotNull
    private String verCode;

    @NotNull
    private String verKey;
}
