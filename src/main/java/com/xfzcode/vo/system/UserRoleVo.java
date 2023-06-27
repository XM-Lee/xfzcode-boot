package com.xfzcode.vo.system;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: XMLee
 * @Date: 2023/4/25 14:50
 * @Description:
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVo {
    private Long userId;
    //private List<Permission> permissions;
    private Long id;
    /**
     * 描述
     */
    private String description;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色标识
     */
    private String authority;
    private Date createTimestamp;
    private Date updateTimestamp;
}
