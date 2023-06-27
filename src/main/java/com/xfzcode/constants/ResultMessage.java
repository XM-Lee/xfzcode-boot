package com.xfzcode.constants;

/**
 * @Author: XMLee
 * @Date: 2022/10/25 18:41
 * @Description: 系统返回常量
 */
public interface ResultMessage {

    String PERMISSION_DENIED = "权限不足！";

    String INSERT_SUCCESS = "新增成功！";
    String UPDATE_SUCCESS = "更新成功！";
    String INSERT_ERROR = "新增失败！";
    String UPDATE_ERROR = "更新失败！";
    String UNBIND_SUCCESS = "解绑成功";
    String BIND_SUCCESS = "绑定成功";
    String BIND_ERROR = "绑定失败";
    String MUTI_BIND_ERROR = "请勿重复绑定";
    String UNBIND_ERROR = "解绑失败";
    String DELETE_ERROR_USED = "所选项中有被使用的数据";
    String FAIL = "查询失败";
    String PARAM_ERROR = "参数错误";
    String PARAM_ERROR_OR_AUTHORITY_NOT_UNI = "参数错误";
    String DELETE_ERROR = "删除失败！";
    String ROLE_KEY_EXIST = "角色码已经存在";
    String CODE_ERROR = "验证码错误或已过期";

    String NOT_ERROR = "请先注册";
    String SEND_OK = "发送成功";
    String SEND_ERROR = "发送失败";
    String ADD_FAIL = "请勿重复添加";
    String REGISTER_FAIL = "注册失败";

    String AUTH_FAIL = "认证失败请重新登录";
    String ERROR_TOKEN = "非法Token";
    String LOGIN_ERROR = "用户名或密码错误";
    String ACCOUNT_LOCKED = "账号已锁定";

    String ACCOUNT_NOT_ENABLE = "账号未启用";
    String VERIFY_CODE_FAIL = "验证错误或已失效";

    String LOGOUT_ERROR = "退出失败";

    String LOGOUT_OK = "退出成功";
    String OPERATION_FAIL = "操作失败";
    String OPERATION_SUCCESS = "操作成功";


}


