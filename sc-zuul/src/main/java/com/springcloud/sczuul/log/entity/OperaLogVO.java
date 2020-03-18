package com.springcloud.sczuul.log.entity;

import java.util.List;

/**
 *  @dept 上海软件研发中心
 *  @description 日志前端视图
 *  @author HaoXin.Liu
 *  @date 2020/2/29 14:10
 **/
public class OperaLogVO {
    /**
     * 操作日志集合
     */
    private List<OperaLogDTO> list;
    /**
     * 页码
     */
    private Integer pageNum;
    /**
     * 页面大小
     */
    private Integer pageSize;
    /**
     * 页数
     */
    private Integer pages;
    /**
     * 总个数
     */
    private Integer total;

    public List<OperaLogDTO> getList() {
        return list;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setList(List<OperaLogDTO> list) {
        this.list = list;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OperaLogVO{" +
                "list=" + list +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", pages=" + pages +
                ", total=" + total +
                '}';
    }
}