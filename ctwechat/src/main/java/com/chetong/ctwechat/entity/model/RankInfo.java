package com.chetong.ctwechat.entity.model;

import java.math.BigDecimal;
import java.util.List;

public class RankInfo {

	private String seedFlag; // 种子标识 1-种子,其他-不是种子
	private Long userId; // 用户ID
	private Long parentId; // 父节点的userId.
	private String userName; // 用户名称
	private Long groupUserId; // 团队或机构的userId
	private String orgName; // 团队或机构的名称
	private String areaCode; // 地区code
	private String areaDesc; // 地区名称
	private BigDecimal orderNum; // 订单量或作业量或委托量
	private BigDecimal point; // 品质,(平均)评分
	private Integer ranking; // 排名

	public String getSeedFlag() {
		return seedFlag;
	}

	public void setSeedFlag(String seedFlag) {
		this.seedFlag = seedFlag;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getGroupUserId() {
		return groupUserId;
	}

	public void setGroupUserId(Long groupUserId) {
		this.groupUserId = groupUserId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaDesc() {
		return areaDesc;
	}

	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}

	public BigDecimal getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(BigDecimal orderNum) {
		this.orderNum = orderNum;
	}

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
