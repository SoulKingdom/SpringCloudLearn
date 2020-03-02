package com.springcloud.sczuul.log.dao;

import com.springcloud.sczuul.log.entity.OperaLogDO;

public interface OperaLogDao {
    int insert(OperaLogDO record);

    int insertSelective(OperaLogDO record);
}