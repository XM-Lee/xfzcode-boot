package com.xfzcode.constants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * @Author: XMLee
 * @Date: 2022/10/25 18:37
 * @Description: 统一结果返回
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResult<T> {

    /**
     * 响应码
     */
    @NonNull
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    @JsonIgnore
    private Long requestTime;


    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg = code.message();
    }


    /**
     * 成功 不返回数据直接返回成功信息
     * @param <T>
     * @return
     */
    public static <T> HttpResult success() {
        HttpResult<T> result = new HttpResult();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 成功 并且加上返回数据
     * @param data
     * @param <T>
     * @return
     */
    public static <T> HttpResult success(T data) {
        HttpResult<T> result = new HttpResult();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 成功 自定义成功返回状态 加上数据
     * @param resultCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T> HttpResult success(ResultCode resultCode, T data) {
        HttpResult result = new HttpResult();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }


    /**
     * 单返回失败的状态码
     * @param resultCode
     * @param <T>
     * @return
     */
    public static <T> HttpResult failure(ResultCode resultCode) {
        HttpResult<T> result = new HttpResult();
        result.setResultCode(resultCode);
        return result;
    }

    /**
     * 返回失败的状态码 及 数据
     * @param resultCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T> HttpResult failure(ResultCode resultCode, T data) {
        HttpResult<T> result = new HttpResult();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    public static <T> HttpResult failure(Integer code,String url, T data) {
        HttpResult<T> result = new HttpResult();
        result.setCode(code);
        result.setMsg(url);
        result.setData(data);
        return result;
    }

    public static <T> HttpResult failure(String message) {
        HttpResult<T> result = new HttpResult();
        result.setResultCode(ResultCode.BADREQUEST);
        result.setData(null);
        result.setMsg(message);
        return result;
    }

}
