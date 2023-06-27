package com.xfzcode.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_roles")
public class Role extends BasePojo implements Serializable {
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
}
