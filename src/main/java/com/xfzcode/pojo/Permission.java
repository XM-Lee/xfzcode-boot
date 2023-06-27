package com.xfzcode.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: XMLee
 * @Date: 2023/4/11 11:46
 * @Description:
 */
@TableName(value="sys_permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BasePojo {

    private String menuName;        // 菜单名
    private String path;            // 路由地址
    private String component;       // 组件路径
    private String visible;         // 菜单状态（0显示 1隐藏）
    private String status;          // 菜单状态（0正常 1停用）
    private String perms;           // 权限标识
    private Long createBy;          // 创建人
    private Long updateBy;          // 更新人
    private Integer delFlag;        // 是否删除（0未删除 1已删除）
    private String remark;          // 备注
    private Long parentId;
}
