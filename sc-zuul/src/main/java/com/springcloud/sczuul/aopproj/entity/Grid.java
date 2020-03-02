package com.springcloud.sczuul.aopproj.entity;

import java.io.Serializable;
import java.util.List;

public class Grid<T> implements Serializable{

	private static final long serialVersionUID = 2585477714351644862L;
	
	private int code = 0;// 0 成功；1失败
    private String msg;// 失败消息，成功时为空值
    private int count;// 总条数
    private List<T> data;// 数据集合
    private Object attribute;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

	public Object getAttribute() {
		return attribute;
	}

	public void setAttribute(Object attribute) {
		this.attribute = attribute;
	}

}
