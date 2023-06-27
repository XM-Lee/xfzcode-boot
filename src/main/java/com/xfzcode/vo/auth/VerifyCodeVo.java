package com.xfzcode.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: XMLee
 * @Date: 2023/4/12 10:53
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyCodeVo {
    private String imageBase64;
    private String key;
}
