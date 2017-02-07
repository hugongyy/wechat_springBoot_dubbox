package com.chetong.ctwechat.entity.model;

import java.math.BigDecimal;
import java.util.List;

import com.chetong.ctwechat.entity.mapping.FmOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class QueryOrderBaseBean extends BasesModel{
	private List<FmOrder> list;
	private Integer listSize;
	private Long orderCount; // 可工作的订单.

	private String buyerUserName; // 买家名称
	private String sendTime; // 发送时间,
	private BigDecimal alowMoney; // 授权金额

	private String signaturePath; // 签名图片的路径

	private String systemSource; //订单任务来源  1=永诚系统

	public String getSignaturePath() {
		return signaturePath;
	}

	public void setSignaturePath(String signaturePath) {
		this.signaturePath = signaturePath;
	}

	public Integer getListSize() {
		if (list != null) {
			listSize = list.size();
		} else {
			listSize = 0;
		}
		return listSize;
	}

	public void setListSize(Integer listSize) {
		this.listSize = listSize;
	}

	public List<FmOrder> getList() {
		return list;
	}

	public void setList(List<FmOrder> list) {
		this.list = list;
	}

	public Long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}

	public String getBuyerUserName() {
		return buyerUserName;
	}

	public void setBuyerUserName(String buyerUserName) {
		this.buyerUserName = buyerUserName;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public BigDecimal getAlowMoney() {
		return alowMoney;
	}

	public void setAlowMoney(BigDecimal alowMoney) {
		this.alowMoney = alowMoney;
	}

	public String getSystemSource() {
		return systemSource;
	}

	public void setSystemSource(String systemSource) {
		this.systemSource = systemSource;
	}

}
