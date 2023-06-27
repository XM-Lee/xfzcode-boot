package com.xfzcode.vo.system;

import com.xfzcode.pojo.Role;
import com.xfzcode.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: XMLee
 * @Date: 2023/4/25 14:46
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultUserVo implements Serializable {
    private User user;
    private List<Role> roles;
}
