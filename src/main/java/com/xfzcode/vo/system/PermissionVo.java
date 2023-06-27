package com.xfzcode.vo.system;

import com.xfzcode.pojo.Permission;
import lombok.Data;

/**
 * @Author: XMLee
 * @Date: 2023/4/11 11:48
 * @Description:
 */
@Data
public class PermissionVo extends Permission {
    private Long roleId;
}
