package com.xfzcode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfzcode.mapper.OperationLogMapper;
import com.xfzcode.pojo.OperationLog;
import com.xfzcode.service.OperationLogService;
import org.springframework.stereotype.Service;

/**
 * @Author: XMLee
 * @Date: 2023/6/27 16:01
 * @Description:
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {
}
