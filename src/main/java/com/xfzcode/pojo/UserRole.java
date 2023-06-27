package com.xfzcode.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("sys_users_roles")
public class UserRole extends BasePojo implements Serializable {

    @TableField("user_id")
    private Long uid;
    @TableField("role_id")
    private Long rid;
}
