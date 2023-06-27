package com.xfzcode.constants;

/**
 * @Author: XMLee
 * @Date: 2022/10/25 18:40
 * @Description: 统一返回code
 */
public enum  ResultCode {
    /* 成功状态码 */
    /**
     * 请求成功
     */
    SUCCESS(200, "请求成功"),
    FAIL(4006, "创建任务失败"),
    /* 失败状态码 */
    /**
     * 请求失败
     */
    FAILURE(500, "服务端异常，请求失败"),

    UNAUTHORIZED(401,"登陆认证失败"),

    BADREQUEST(400,"请求错误"),

    FORBIDDEN(403,"请求拒绝"),

    /* 参数错误: 1001-1999 */
    /**
     * 参数无效
     */
    PARAM_IS_INVALID(1001,"参数无效"),
    /**
     * 参数类型错误
     */
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    /**
     * 参数缺失
     */
    PARAM_NOT_COMPLETE(1004,"参数缺失"),

    PAGE_PARAM_NOT_EXIT(1005,"分页参数不能为空"),


    /* 请求错误: 4001-4999 */
    /**
     * 请求类型不支持
     */
    REQUEST_NOT_SUPPORTED(4006,"请求类型不支持"),

//    /* 用户错误：2001-2999*/

//    /* 业务错误：3001-3999 */

//    /* 系统错误：4001-4999 */

    REDIS_CONNECT_FAILED( 4001, "Redis连接失败"),

    ES_CONNECT_FAILED( 4002, "ES连接失败"),

//    /* 数据错误：5001-5999 */

//    /* 接口错误：6001-6999 */

//    /* 权限错误：7001-7999 */

    ID_TOKEN_CHECK_FAILED( 7001, "Id-Token校验失败"),

    SET_CHECK_PARAMETER_ERROR( 7002, "SET权限校验,参数异常"),

    SET_CHECK_PERMISSION_DENIED( 7003, "SET权限校验失败，无目标id访问权限"),

    CHECK_PERMISSION_CODE_FAILED( 7004, "接口权限码校验失败"),

    CHECK_ROLE_CODE_FAILED( 7005, "角色权限码校验失败"),

    SA_TOKEN_ERROR( 7006, "Sa-Token权限校验异常"),
    VERIFY_CODE_FAIL(7007,"验证错误或已失效" );

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
