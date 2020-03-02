package com.springcloud.sczuul.aopproj.entity;

import java.io.Serializable;
import java.util.Date;

public class SysLogOper implements Serializable {

	private static final long serialVersionUID = -6094245807465582411L;

	private String id;

	private String operaCode;

	private String appid;

	private String method;

	private Date operationTime;

	private String station;

	private String stationCode;

	private String province;

	private String city;

	private String userId;

	private String userName;

	private String userAcnt;

	private String sourceType;

	private String sourceApplication;

	private String sourcePublicIp;

	private String sourcePrivateIp;

	private String sourceVpnIp;

	private String sourceMac;

	private String targetApplication;

	private String targetPublicIp;

	private String targetPrivateIp;

	private String operationType;

	private String result;

	private String reason;

	private String content;

	private String mailNo;

	private String orderId;

	private String associationNumber;

	private String email;

	private String feature;

	private Integer delFlag = 0;

	private Integer sortNo = 0;

	private Date gmtCreate;

	private String createBy;

	private Date gmtModified;

	private String lastModifiedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getOperaCode() {
		return operaCode;
	}

	public void setOperaCode(String operaCode) {
		this.operaCode = operaCode;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method == null ? null : method.trim();
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station == null ? null : station.trim();
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode == null ? null : stationCode.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getUserAcnt() {
		return userAcnt;
	}

	public void setUserAcnt(String userAcnt) {
		this.userAcnt = userAcnt == null ? null : userAcnt.trim();
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType == null ? null : sourceType.trim();
	}

	public String getSourceApplication() {
		return sourceApplication;
	}

	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication == null ? null
				: sourceApplication.trim();
	}

	public String getSourcePublicIp() {
		return sourcePublicIp;
	}

	public void setSourcePublicIp(String sourcePublicIp) {
		this.sourcePublicIp = sourcePublicIp == null ? null : sourcePublicIp
				.trim();
	}

	public String getSourcePrivateIp() {
		return sourcePrivateIp;
	}

	public void setSourcePrivateIp(String sourcePrivateIp) {
		this.sourcePrivateIp = sourcePrivateIp == null ? null : sourcePrivateIp
				.trim();
	}

	public String getSourceVpnIp() {
		return sourceVpnIp;
	}

	public void setSourceVpnIp(String sourceVpnIp) {
		this.sourceVpnIp = sourceVpnIp == null ? null : sourceVpnIp.trim();
	}

	public String getSourceMac() {
		return sourceMac;
	}

	public void setSourceMac(String sourceMac) {
		this.sourceMac = sourceMac == null ? null : sourceMac.trim();
	}

	public String getTargetApplication() {
		return targetApplication;
	}

	public void setTargetApplication(String targetApplication) {
		this.targetApplication = targetApplication == null ? null
				: targetApplication.trim();
	}

	public String getTargetPublicIp() {
		return targetPublicIp;
	}

	public void setTargetPublicIp(String targetPublicIp) {
		this.targetPublicIp = targetPublicIp == null ? null : targetPublicIp
				.trim();
	}

	public String getTargetPrivateIp() {
		return targetPrivateIp;
	}

	public void setTargetPrivateIp(String targetPrivateIp) {
		this.targetPrivateIp = targetPrivateIp == null ? null : targetPrivateIp
				.trim();
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType == null ? null : operationType
				.trim();
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result == null ? null : result.trim();
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason == null ? null : reason.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo == null ? null : mailNo.trim();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getAssociationNumber() {
		return associationNumber;
	}

	public void setAssociationNumber(String associationNumber) {
		this.associationNumber = associationNumber == null ? null
				: associationNumber.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature == null ? null : feature.trim();
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy == null ? null : lastModifiedBy
				.trim();
	}
}