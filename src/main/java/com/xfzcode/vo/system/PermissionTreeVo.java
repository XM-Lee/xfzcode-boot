package com.xfzcode.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: XMLee
 * @Date: 2023/4/24 11:39
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionTreeVo {
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

    private List<PermissionTreeVo> children;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<PermissionTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionTreeVo> children) {
        this.children = children;
    }
}
