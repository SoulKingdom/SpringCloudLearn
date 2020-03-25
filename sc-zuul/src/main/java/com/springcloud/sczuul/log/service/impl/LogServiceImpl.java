package com.springcloud.sczuul.log.service.impl;

import com.github.pagehelper.PageInfo;
import com.neusoft.gateway.log.entity.LogCond;
import com.springcloud.core.page.PageUtils;
import com.springcloud.sczuul.log.dao.OperaLogDao;
import com.springcloud.sczuul.log.entity.OperaLogDTO;
import com.springcloud.sczuul.log.entity.OperaLogVO;
import com.springcloud.sczuul.log.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *  @dept 上海软件研发中心
 *  @description 日志分页服务
 *  @author HaoXin.Liu
 *  @date 2020/2/29 14:41
 **/
@Service
public class LogServiceImpl implements LogService {

    @Resource
    private OperaLogDao operaLogDao;

    /**
     * 日志分页查询
     *
     * @dept 上海软件研发中心
     * @param  logCond 前端日志条件
     * @return 日志视图
     * @author HaoXin.Liu
     * @date 2020/2/29 14:39
     **/
    @Override
    public OperaLogVO listOperLog(LogCond logCond) {
        OperaLogVO operaLogVo = new OperaLogVO();
        //按条件查询日志
        List<OperaLogDTO> operaLogDtoList = operaLogDao.selectByPage(logCond);
        //日志为空，返回空数据空指针校验
        if (null == operaLogDtoList && 0 == operaLogDtoList.size()) {
            return operaLogVo;
        }
        PageInfo<OperaLogDTO> pageInfo = (PageInfo<OperaLogDTO>) PageUtils.getPageInfo(operaLogDtoList);
        //设置查询记录
        operaLogVo.setList(pageInfo.getList());
        //设置分页参数
        operaLogVo.setPageNum(pageInfo.getPageNum());
        operaLogVo.setPageSize(pageInfo.getSize());
        operaLogVo.setTotal(Integer.valueOf(String.valueOf(pageInfo.getTotal())));
        return operaLogVo;
    }
}