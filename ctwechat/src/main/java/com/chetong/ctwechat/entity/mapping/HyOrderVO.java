package com.chetong.ctwechat.entity.mapping;

import java.io.Serializable;

import com.chetong.ctwechat.entity.model.BasesModel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class HyOrderVO extends BasesModel implements Serializable {
    private Long id;
    
    private Long taskId;

	private String orderNo;

    private String dealStat;

    private Long sellerUserId;

    private Long buyerUserId;

    private Long entrustUserId;

    private Long payerUserId;

    private String payerUserName;

    private String sellerUserName;

    private String buyerUserName;

    private String entrustUserName;

    private String caseNo;

    private Long serviceId;

    private Long subjectId;

    private String sendAddress;

    private Float longtitude;

    private Float latitude;

    private String getTime;

    private String finishTime;

    private String finalTime;

    private String sendTime;

    private String provCode;

    private String cityCode;

    private String areaCode;

    private String grabAddress;

    private Float grabLongtitude;

    private Float grabLatitude;

    private Long groupUserId;

    private String groupUserName;

    private String buyerTel;

    private String sellerTel;

    private String entrustTel;

    private Long sendUserId;

    private String sendUserName;

    private String preliminaryTime;

    private String isConfirm;

    private String createdBy;

    private String updatedBy;

    private String createTime;

    private String updateTime;
    
    private Long workId;
    
    private Float mileage;
    
    public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Float getMileage() {
		return mileage;
	}

	public void setMileage(Float mileage) {
		this.mileage = mileage;
	}

	public Long getId() {
        return id;
    }

    public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDealStat() {
        return dealStat;
    }

    public void setDealStat(String dealStat) {
        this.dealStat = dealStat;
    }

    public Long getSellerUserId() {
        return sellerUserId;
    }

    public void setSellerUserId(Long sellerUserId) {
        this.sellerUserId = sellerUserId;
    }

    public Long getBuyerUserId() {
        return buyerUserId;
    }

    public void setBuyerUserId(Long buyerUserId) {
        this.buyerUserId = buyerUserId;
    }

    public Long getEntrustUserId() {
        return entrustUserId;
    }

    public void setEntrustUserId(Long entrustUserId) {
        this.entrustUserId = entrustUserId;
    }

    public Long getPayerUserId() {
        return payerUserId;
    }

    public void setPayerUserId(Long payerUserId) {
        this.payerUserId = payerUserId;
    }

    public String getPayerUserName() {
        return payerUserName;
    }

    public void setPayerUserName(String payerUserName) {
        this.payerUserName = payerUserName;
    }

    public String getSellerUserName() {
        return sellerUserName;
    }

    public void setSellerUserName(String sellerUserName) {
        this.sellerUserName = sellerUserName;
    }

    public String getBuyerUserName() {
        return buyerUserName;
    }

    public void setBuyerUserName(String buyerUserName) {
        this.buyerUserName = buyerUserName;
    }

    public String getEntrustUserName() {
        return entrustUserName;
    }

    public void setEntrustUserName(String entrustUserName) {
        this.entrustUserName = entrustUserName;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public Float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Float longtitude) {
        this.longtitude = longtitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(String finalTime) {
        this.finalTime = finalTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getProvCode() {
        return provCode;
    }

    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getGrabAddress() {
        return grabAddress;
    }

    public void setGrabAddress(String grabAddress) {
        this.grabAddress = grabAddress;
    }

    public Float getGrabLongtitude() {
        return grabLongtitude;
    }

    public void setGrabLongtitude(Float grabLongtitude) {
        this.grabLongtitude = grabLongtitude;
    }

    public Float getGrabLatitude() {
        return grabLatitude;
    }

    public void setGrabLatitude(Float grabLatitude) {
        this.grabLatitude = grabLatitude;
    }

    public Long getGroupUserId() {
        return groupUserId;
    }

    public void setGroupUserId(Long groupUserId) {
        this.groupUserId = groupUserId;
    }

    public String getGroupUserName() {
        return groupUserName;
    }

    public void setGroupUserName(String groupUserName) {
        this.groupUserName = groupUserName;
    }

    public String getBuyerTel() {
        return buyerTel;
    }

    public void setBuyerTel(String buyerTel) {
        this.buyerTel = buyerTel;
    }

    public String getSellerTel() {
        return sellerTel;
    }

    public void setSellerTel(String sellerTel) {
        this.sellerTel = sellerTel;
    }

    public String getEntrustTel() {
        return entrustTel;
    }

    public void setEntrustTel(String entrustTel) {
        this.entrustTel = entrustTel;
    }

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getPreliminaryTime() {
        return preliminaryTime;
    }

    public void setPreliminaryTime(String preliminaryTime) {
        this.preliminaryTime = preliminaryTime;
    }

    public String getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(String isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}