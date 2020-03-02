package com.springcloud.sczuul.log.dao;

import com.springcloud.sczuul.log.entity.OperationLog;

import java.util.List;
import java.util.Map;

public interface OperationLogMapper {

    List<Map<String, Object>> selectByMap(Map<String, Object> param);

    int insertByMap(OperationLog param);

    int updateByMap(OperationLog param);
}