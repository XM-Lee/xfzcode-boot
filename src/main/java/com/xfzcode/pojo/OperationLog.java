package com.xfzcode.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: XMLee
 * @Date: 2023/6/27 15:57
 * @Description:
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_operation_log")
public class OperationLog extends BasePojo {

    private String account;
    private String userName;

    private String ip;

    private Date time;

    private String action;

    private String entity;
    private String url;

    @JsonIgnore
    private String Detail;

    private String status;
    private String statusCode;

}
