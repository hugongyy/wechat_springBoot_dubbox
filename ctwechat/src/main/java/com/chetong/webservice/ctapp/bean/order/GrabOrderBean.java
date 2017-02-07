package com.chetong.webservice.ctapp.bean.order;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

@XmlRootElement
@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrabOrderBean {
	private String userId; // 用户ID
	private String orderId; // 订单ID
	private String orderFlag; // 抢/撤单 04-抢单,03-撤单
	private String buyerUserId; // 买家ID
	private String cancelType; // 撤单类型：1-私了销案或取消委托 2-需改派他人处理 3-订单派错 4-其他
	private String reason; // 撤单原因
	private String address; // 地址
	private String longitude; // 经度
	private String latitude; // 纬度
	private String serviceId; // 服务类型id 1、车险 5、货运险

	private String caseNo; // 案件号
	private String orderNo;// 订单号

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public String getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	public String getCancelType() {
		return cancelType;
	}

	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GrabOrderBean [userId=").append(userId).append(", orderId=").append(orderId)
				.append(", orderFlag=").append(orderFlag).append(", buyerUserId=").append(buyerUserId)
				.append(", reason=").append(reason).append(", address=").append(address).append(", longitude=")
				.append(longitude).append(", latitude=").append(latitude).append(", serviceId=").append(serviceId)
				.append(", caseNo=").append(caseNo).append(", orderNo=").append(orderNo).append("]");
		return builder.toString();
	}

}
