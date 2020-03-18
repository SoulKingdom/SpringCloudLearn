package com.springcloud.sczuul.constant;
/**
  * 操作基础常量
  *
  * @dept 上海软件研发中心
  * @author HaoXin.Liu
  * @date 2020/3/9 21:20
  **/
public interface OperaConstant {
    /**
     * 新增操作 关键字段数据
     */
    String[] ADD_OPEAR_FIELD = {"add", "save", "insert", "create"};

    /**
     * 删除操作 关键字段数据
     */
    String[] DEL_OPEAR_FIELD = {"del", "remove"};

    /**
     * 修改操作 关键字段数据
     */
    String[] ALTER_OPEAR_FIELD = {"mod", "update", "edit", "set"};
    /**
     * 上传操作 关键字段数据
     */
    String[] UPLOAD_OPEAR_FIELD = {"upload", "fdfs", "excelImp"};
    /**
     * 下载操作 关键字
     */
    String[] DOWN_OPEAR_FIELD = {"down"};
    /**
     * Controller层 新增操作
     */
    String CONTROLLER_OPEAR_METHOD_TYPE_ADD = "新增";
    /**
     * Controller层 删除操作
     */
    String CONTROLLER_OPEAR_METHOD_TYPE_DEL = "删除";
    /**
     * Controller层 下载操作
     */
    String CONTROLLER_OPEAR_METHOD_TYPE_UPLOAD = "上传";
    /**
     * Controller层 下载操作
     */
    String CONTROLLER_OPEAR_METHOD_TYPE_DOWNLOAD = "下载";
    /**
     * Controller层 新增操作
     */
    String CONTROLLER_OPEAR_METHOD_TYPE_MOD = "修改";
    /**
     * 用戶编码
     */
    String USER_CODE = "USER_CODE";
    /**
     * 用户名称
     */
    String USER_NAME = "USER_NAME";
    /**
     * jwt前台token对象
     */
    String ACCESS_TOKEN = "access_token";
    /**
     * 操作类型 新增，删除，上传，下载，修改
     */
    String OPERATION_TYPE = "operationType";

}
