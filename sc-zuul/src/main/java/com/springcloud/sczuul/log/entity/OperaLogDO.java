package com.springcloud.sczuul.log.entity;
/**
  * 操作日志DO
  *
  * @dept 上海软件研发中心
  * @author HaoXin.Liu
  * @date 2020/3/9 20:51
  **/
public class OperaLogDO {
    private String id;

    private String operaCode;

    private String method;

    private String userCode;

    private String userName;

    private String url;

    private String appId;

    private String sourceType;

    private String sourceApplication;

    private String operationTime;

    private String sourcePublicIp;

    private String sourcePrivateIp;

    private String operationType;

    private String result;

    private String reason;

    private String content;

    private Boolean isDeleted;

    private Integer sortNo;

    private String gmtCreate;

    private String createBy;

    private String gmtModified;

    private String lastModifiedBy;

    private Integer version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperaCode() {
        return operaCode;
    }

    public void setOperaCode(String operaCode) {
        this.operaCode = operaCode;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceApplication() {
        return sourceApplication;
    }

    public void setSourceApplication(String sourceApplication) {
        this.sourceApplication = sourceApplication;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }


    public String getSourcePublicIp() {
        return sourcePublicIp;
    }

    public void setSourcePublicIp(String sourcePublicIp) {
        this.sourcePublicIp = sourcePublicIp;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getSourcePrivateIp() {
        return sourcePrivateIp;
    }

    public void setSourcePrivateIp(String sourcePrivateIp) {
        this.sourcePrivateIp = sourcePrivateIp;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy == null ? null : lastModifiedBy.trim();
    }

    @Override
    public String toString() {
        return "OperaLogDO{" +
                "id='" + id + '\'' +
                ", operaCode='" + operaCode + '\'' +
                ", method='" + method + '\'' +
                ", userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", url='" + url + '\'' +
                ", appId='" + appId + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", sourceApplication='" + sourceApplication + '\'' +
                ", operationTime='" + operationTime + '\'' +
                ", sourcePublicIp='" + sourcePublicIp + '\'' +
                ", sourcePrivateIp='" + sourcePrivateIp + '\'' +
                ", operationType='" + operationType + '\'' +
                ", result='" + result + '\'' +
                ", reason='" + reason + '\'' +
                ", content='" + content + '\'' +
                ", isDeleted=" + isDeleted +
                ", sortNo=" + sortNo +
                ", gmtCreate='" + gmtCreate + '\'' +
                ", createBy='" + createBy + '\'' +
                ", gmtModified='" + gmtModified + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", version=" + version +
                '}';
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}