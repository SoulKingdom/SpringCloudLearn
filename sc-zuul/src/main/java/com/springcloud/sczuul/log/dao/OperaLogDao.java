package com.springcloud.sczuul.log.dao;

import com.neusoft.gateway.log.entity.LogCond;
import com.springcloud.sczuul.log.entity.OperaLogDO;
import com.springcloud.sczuul.log.entity.OperaLogDTO;

import java.util.List;

/**
  * 操作日志DAO
  *
  * @dept 上海软件研发中心
  * @author HaoXin.Liu
  * @date 2020/3/9 20:51
  **/
public interface OperaLogDao {

    /**
     * 操作日志信息插入
     *
     * @dept 上海软件研发中心
     * @param record 操作日志记录
     * @return 插入数量
     * @author HaoXin.Liu
     * @date 2020/2/29 13:21
     **/
    int insert(OperaLogDO record);

    /**
     * 按条件查询操作日志集合
     *
     * @dept 上海软件研发中心
     * @param logCond record 操作日志记录
     * @return List<OperaLogDTO> 操作集合
     * @author HaoXin.Liu
     * @date 2020/2/29 15:02
     **/
    List<OperaLogDTO> selectByPage(LogCond logCond);

    /**
      * 根据用户id获取用户名称
      *
      * @dept 上海软件研发中心
      * @param userId 用户id
      * @return String 用户名称
      * @author HaoXin.Liu
      * @date 2020/3/9 20:50
      **/
    String getUserName(String userId);
}