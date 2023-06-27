package com.xfzcode.vo.system;

import lombok.*;

import java.util.Date;

/**
 * @Author: XMLee
 * @Date: 2023/4/24 18:17
 * @Description:
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionsRoleVo {
    private Long roleId;
    private Long id;
    private String menuName;        // 菜单名
    private String path;            // 路由地址
    private String component;       // 组件路径
    private String visible;         // 菜单状态（0显示 1隐藏）
    private String status;          // 菜单状态（0正常 1停用）
    private String perms;           // 权限标识
    private Long createBy;          // 创建人
    private Date createTime;        // 创建时间
    private Long updateBy;          // 更新人
    private Date updateTime;        // 更新时间
    private Integer delFlag;        // 是否删除（0未删除 1已删除）
    private String remark;          // 备注
    private Long parentId;
}
