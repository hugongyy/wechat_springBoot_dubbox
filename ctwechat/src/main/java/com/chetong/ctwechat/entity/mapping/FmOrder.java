package com.chetong.ctwechat.entity.mapping;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.chetong.ctwechat.entity.model.BasesModel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FmOrder extends BasesModel implements java.io.Serializable {
	private static final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * 属性定义.
	 */
	private Long id; /*--  --*/
	private String dealStat; /*-- 订单状态 00派单中 01无响应 02注销 03撤单 04作业中 05待初审 06初审退回
								07待审核 08已退回 09审核通过10订单删除 11 提交平台处理 12 审核注销 --*/
	private String orderSource; /*-- 订单来源 0 - 独立任务 1 - 追加任务 --*/
	private String orderType; /*-- 订单类型（ 根据订单类型计算订单费用）  0-查勘（可授权定损） 1- 定损（标的） 2 - 定损（三者） 3 - 物损 --*/
	private String orderNo; /*-- 订单编号 --*/
	private Long caseId; /*-- 报案id --*/
	private String caseNo; /*--  --*/
	private String carNo; /*-- 定损车牌号 --*/
	private Long buyerUserId; /*-- 买方 --*/
	private String buyerUserType; /*-- 买方用户类型 0 - 个人 1 - 机构 --*/
	private Long payerUserId; /*-- 订单支付方id,默认为buyerUserId --*/
	private Long sellerUserId; /*-- 卖方，抢单成功者 --*/
	private String sellerUserType; /*-- 卖方用户类型 0 - 个人 1 - 机构 --*/
	private Long serviceId; /*--  --*/
	private String serviceName; /*-- 服务类别 --*/
	private Long subjectId; /*-- subjectId --*/
	private String subjectName; /*-- 服务名称  查勘（可授权定损） ， 定损，物损 三者之一 --*/
	private Integer responseTime; /*-- 单位 分钟 --*/
	private String isAlow; /*-- 是否授权现场定损 0 - 否 1 -是 --*/
	private BigDecimal alowMoney; /*-- 授权额度（每车） --*/
	private BigDecimal delegateMomey; /*-- 单位 元 --*/
	private String delegateDesc; /*-- 委托要求 --*/
	private String workAddress; /*-- 作业地点 --*/
	private BigDecimal longtitude; /*-- 作业地点经度 --*/
	private BigDecimal latitude; /*-- 作业地点纬度 --*/
	private String linkMan; /*-- 联系人 --*/
	private String linkTel; /*-- 联系电话 --*/
	private BigDecimal mileage; /*-- 作业里程 --*/
	private Date getTime; /*-- 抢单时间 --*/
	private Date finishTime; /*-- 完成时间 --*/
	private Date sendTime; /*-- 派单时间 --*/
	private Date preliminaryTime; /*-- 预审时间 --*/
	private String preliminaryDesc; /*-- 预审描述 --*/
	private Date finalTime; /*-- 终审时间 --*/
	private String finalDesc; /*-- 终审描述 --*/
	private Long sendId; /*-- 派单人,  若派单人 是前台网站使用者，则和买方相同 --*/
	private String sendIdType; /*-- 派单人类型 0 - 前台网站 1 - 后台操作员 --*/
	private String reviewClass; /*-- 1 -5 对应5颗星 --*/
	private Date reviewTime; /*-- 评价时间 --*/
	private String reviewName; /*-- 评价人 --*/
	private String reviewType; /*-- 评价人类型 0 - 前台网站 1 - 后台操作员 --*/
	private String ext1; /*-- 出险地点的省代码 --*/
	private String ext2; /*-- 出险地点的市代码 --*/
	private String ext3; /*-- 调用新东方报案接口是否成功 --*/
	private String ext4; /*-- 1 内部订单 2 外部订单 --*/
	private String ext5; /*-- 评价内容 --*/
	private String ext6; /*-- 1 自主派单 2 委托派单 --*/
	private String ext7; /*-- 买家委托人id(如果ext6为2的话，此字段必填，可能为子帐号) --*/
	private String ext8; /*-- 委托派单费用 --*/
	private Long groupUserId; /*-- 卖家所属机构id --*/
	private String buyerUserName; /*-- 买家用户名称 --*/
	private String sellerUserName; /*-- 卖家用户名称 --*/
	private String buyerMobile; /*-- 买家手机号码 --*/
	private String ctAddress; /*-- 车童抢单地址 --*/
	private BigDecimal ctLatitude; /*-- 车童抢单纬度 --*/
	private BigDecimal ctLongtitude; /*-- 车童抢单经度 --*/
	private String ctArriveInfo; /*-- 车童到达的经纬度和时间 --*/
	private BigDecimal arrivalHours; /*-- 几小时内到达 --*/
	private String ext9; /*--  --*/
	private String ext10; /*-- 主帐号id --*/
	private String ext11; /*--  --*/
	private String ext12; /*-- 订单删除备注 --*/
	private String ext13; /*--  --*/
	private Date arrivalTime; /*--  --*/
	private String isNego; /*-- 是否议价车童1-是 0-否 --*/
	private Long negoId; /*-- 议价信息id --*/
	private Long commiId; /*-- 团队车童佣金id --*/
	private String ext14; /*-- 工作地县代码 --*/
	private String ext15; /*-- ext15 --*/
	private String ext16; /*-- ext16 --*/
	private String ext17; /*-- ext17 --*/
	private String ext18; /*-- ext18 --*/
	private String importType;/* 导单类型（0.非导单 1.历史订单 2.当日导单） */
	private String priceType; /*--0、默认结算方式 1、机构间结算方式--*/
	private String isRemote; /*--是否异地单--*/
	private Long insuerUserId; /*--承保人--*/
	private String isSimple; /*--是否是简易流程订单：0-否，1-是--*/
	private String isFast; /*--是否快赔订单：0-否，1-是--*/

	private String allowEvaluate; // 可以评价 1-可以评价, -1-已有评价, 0-不可评价
	// ==================红包 wfj 2015-09-06==================
	String hasRedPacket = "0"; // 订单是否有红包
	String isMidnight = "0"; // 是否是夜间红包
	String rPTypeStr = ""; // 红包类型字段
	String rPAmount = "0"; // 红包金额
	String midTypeStr = ""; // 夜间红包字符
	String HolTypeStr = ""; // 节假日红包字符
	String rPType = ""; // 红包类型

	// ==================红包 wfj 2015-09-06==================
	
	
	// =====================货运险=============================
	private String accdientDesc;    //事故简述
	private String transportType;   //运输工具
	private String transportDesc; //运输工具描述
	private String cargoName; //货物名称
	private String limitTime; //要求车童到达时间
	private String supportLinkman; //技术联系人
	private String supportLinktel;  //技术支持联系人电话
	private String limitTimeDesc;  //限制时间描述
	private String entrustUserName;  //委托方
	//==========================货运险=========================

	/**
	 * 构造函数. 
	 */
	public FmOrder() {
	}

	private String dealStatLabel;
	private String orderTypeLabel;
	private String timeDiffrence;
	private String sendLeftTime;

	//
	private String getTimeLabel;
	private String finishTimeLabel;
	private String sendTimeLabel;
	private String preliminaryTimeLabel;
	private String finalTimeLabel;
	private String reviewTimeLabel;
	private String arrivalTimeLabel;
	// 派单费用,基础费+差旅费
	private String reapMoney;
	
	private String withdrawReason;//撤单原因
	private String withdrawTime;//撤单时间
	private boolean showAllReason;
	

	/**
	 * Getter/Setter方法.
	 */

	// 订单类型(中文)
	public String getOrderTypeLabel() {
		if (StringUtils.isEmpty(orderType)) {
			if (serviceId == 1l) {
				if (subjectId == 1l) {
					orderTypeLabel = "查勘";
				} else if (subjectId == 2l) {
					orderTypeLabel = "定损";
				} else if (subjectId == 3l) {
					orderTypeLabel = "其他";
				}
			} else if (serviceId == 5l) {
				orderTypeLabel = "货运险";
			}

		} else {
			if ("0".equals(orderType)) {
				if ("1".equals(isAlow)) {
					orderTypeLabel = "查勘(授权现场定损)";
				} else {
					orderTypeLabel = "查勘";
				}
			} else if ("1".equals(getOrderType())) {
				orderTypeLabel = "定损(标的)";
			} else if ("2".equals(getOrderType())) {
				orderTypeLabel = "定损(三者)";
			} else if ("3".equals(getOrderType())) {
				orderTypeLabel = "其他";
			} else if ("71".equals(getOrderType())) {
				orderTypeLabel = "医院探视";
			} else if ("72".equals(getOrderType())) {
				orderTypeLabel = "一次性调解";
			}
		}

		return orderTypeLabel;
	}

	public String getSendLeftTime() {
		if(null != serviceId){
			if(5 == serviceId){
				if (sendTime != null) {
					sendLeftTime = (sendTime.getTime() - (new Date()).getTime() + 5 * 60 * 1000) / 1000 + "";
				}
			}else{
				if (sendTime != null) {
					sendLeftTime = (sendTime.getTime() - (new Date()).getTime() + 3 * 60 * 1000) / 1000 + "";
				}
			}
		}
		return sendLeftTime;
	}

	public void setSendLeftTime(String sendLeftTime) {
		this.sendLeftTime = sendLeftTime;
	}

	public String getGetTimeLabel() {
		if (getTime != null) {
			getTimeLabel = f.format(getTime);
		}
		return getTimeLabel;
	}

	public void setGetTimeLabel(String getTimeLabel) {
		this.getTimeLabel = getTimeLabel;
	}

	public String getFinishTimeLabel() {
		if (finishTime != null) {
			finishTimeLabel = f.format(finishTime);
		}
		return finishTimeLabel;
	}

	public void setFinishTimeLabel(String finishTimeLabel) {
		this.finishTimeLabel = finishTimeLabel;
	}

	public String getSendTimeLabel() {
		if (sendTime != null) {
			sendTimeLabel = f.format(sendTime);
		}
		return sendTimeLabel;
	}

	public void setSendTimeLabel(String sendTimeLabel) {
		this.sendTimeLabel = sendTimeLabel;
	}

	public String getPreliminaryTimeLabel() {
		if (preliminaryTime != null) {
			preliminaryTimeLabel = f.format(preliminaryTime);
		}
		return preliminaryTimeLabel;
	}

	public void setPreliminaryTimeLabel(String preliminaryTimeLabel) {
		this.preliminaryTimeLabel = preliminaryTimeLabel;
	}

	public String getFinalTimeLabel() {
		if (finalTime != null) {
			finalTimeLabel = f.format(finalTime);
		}
		return finalTimeLabel;
	}

	public void setFinalTimeLabel(String finalTimeLabel) {
		this.finalTimeLabel = finalTimeLabel;
	}

	public String getReviewTimeLabel() {
		if (reviewTime != null) {
			reviewTimeLabel = f.format(reviewTime);
		}
		return reviewTimeLabel;
	}

	public void setReviewTimeLabel(String reviewTimeLabel) {
		this.reviewTimeLabel = reviewTimeLabel;
	}

	public String getArrivalTimeLabel() {
		if (arrivalTime != null) {
			arrivalTimeLabel = f.format(arrivalTime);
		}
		return arrivalTimeLabel;
	}

	public void setArrivalTimeLabel(String arrivalTimeLabel) {
		this.arrivalTimeLabel = arrivalTimeLabel;
	}

	// 订单状态(中文)
	public String getDealStatLabel() {
		if (getDealStat() != null) {
			if ("00".equals(getDealStat())) {
				dealStatLabel = "派单中";
			} else if ("01".equals(getDealStat())) {
				dealStatLabel = "无响应";
			} else if ("02".equals(getDealStat())) {
				dealStatLabel = "注销";
			} else if ("03".equals(getDealStat())) {
				dealStatLabel = "撤单";
			} else if ("04".equals(getDealStat())) {
				dealStatLabel = "作业中";
			} else if ("05".equals(getDealStat())) {
				dealStatLabel = "待初审";
			} else if ("06".equals(getDealStat())) {
				dealStatLabel = "初审退回";
			} else if ("07".equals(getDealStat())) {
				dealStatLabel = "待审核";
			} else if ("08".equals(getDealStat())) {
				dealStatLabel = "已退回";
			} else if ("09".equals(getDealStat())) {
				dealStatLabel = "审核通过";
			} else if ("10".equals(getDealStat())) {
				dealStatLabel = "订单删除";
			} else {
				dealStatLabel = "未知";
			}
		}
		return dealStatLabel;
	}

	// 超时时间(单位:分钟)
	public String getTimeDiffrence() {
		if (getSendTime() != null) {
			int overTime = 12;
			if ("0".equals(getOrderType())) {
				// 查勘或救援
				overTime = 12;
			} else {
				// 定损,物损.
				overTime = 24;
			}
			// 派单时间+超时小时-当前时间.
			// double a = (getSendTime().getTime() + overTime * 60 * 60 * 1000 -
			// (new Date()).getTime()) / 1000 / 60;
			double k = (getSendTime().getTime() + overTime * 60 * 60 * 1000 - (new Date()).getTime()) / 1000;
			double a = Math.abs(k);
			int hour = (int) a / 60 / 60;
			int min = (int) (a % (60 * 60)) / 60;
			int sec = (int) (a % 60);

			String hourLabel = hour < 10 ? "0" + hour : "" + hour;
			String minLabel = min < 10 ? "0" + min : "" + min;
			String secLabel = sec < 10 ? "0" + sec : "" + sec;

			timeDiffrence = hourLabel + ":" + minLabel + ":" + secLabel;
			if (k < 0) {
				timeDiffrence = "-" + timeDiffrence;
			}
		}
		return timeDiffrence;
	}

	public void setDealStatLabel(String dealStatLabel) {
		this.dealStatLabel = dealStatLabel;
	}

	public void setOrderTypeLabel(String orderTypeLabel) {
		this.orderTypeLabel = orderTypeLabel;
	}

	public void setTimeDiffrence(String timeDiffrence) {
		this.timeDiffrence = timeDiffrence;
	}

	/**
	 * getId.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * setId.
	 */

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * getDealStat.
	 */
	public String getDealStat() {
		return dealStat;
	}

	/**
	 * setDealStat.
	 */

	public void setDealStat(String dealStat) {
		this.dealStat = dealStat;
	}

	/**
	 * getOrderSource.
	 */
	public String getOrderSource() {
		return orderSource;
	}

	/**
	 * setOrderSource.
	 */

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	/**
	 * getOrderType.
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * setOrderType.
	 */

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * getOrderNo.
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * setOrderNo.
	 */

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * getCaseId.
	 */
	public Long getCaseId() {
		return caseId;
	}

	/**
	 * setCaseId.
	 */

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	/**
	 * getCaseNo.
	 */
	public String getCaseNo() {
		return caseNo;
	}

	/**
	 * setCaseNo.
	 */

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	/**
	 * getCarNo.
	 */
	public String getCarNo() {
		return carNo;
	}

	/**
	 * setCarNo.
	 */

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	/**
	 * getBuyerUserId.
	 */
	public Long getBuyerUserId() {
		return buyerUserId;
	}

	/**
	 * setBuyerUserId.
	 */

	public void setBuyerUserId(Long buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	/**
	 * getBuyerUserType.
	 */
	public String getBuyerUserType() {
		return buyerUserType;
	}

	/**
	 * setBuyerUserType.
	 */

	public void setBuyerUserType(String buyerUserType) {
		this.buyerUserType = buyerUserType;
	}

	/**
	 * getPayerUserId.
	 */
	public Long getPayerUserId() {
		return payerUserId;
	}

	/**
	 * setPayerUserId.
	 */

	public void setPayerUserId(Long payerUserId) {
		this.payerUserId = payerUserId;
	}

	/**
	 * getSellerUserId.
	 */
	public Long getSellerUserId() {
		return sellerUserId;
	}

	/**
	 * setSellerUserId.
	 */

	public void setSellerUserId(Long sellerUserId) {
		this.sellerUserId = sellerUserId;
	}

	/**
	 * getSellerUserType.
	 */
	public String getSellerUserType() {
		return sellerUserType;
	}

	/**
	 * setSellerUserType.
	 */

	public void setSellerUserType(String sellerUserType) {
		this.sellerUserType = sellerUserType;
	}

	/**
	 * getServiceId.
	 */
	public Long getServiceId() {
		return serviceId;
	}

	/**
	 * setServiceId.
	 */

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * getServiceName.
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * setServiceName.
	 */

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * getSubjectId.
	 */
	public Long getSubjectId() {
		return subjectId;
	}

	/**
	 * setSubjectId.
	 */

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * getSubjectName.
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * setSubjectName.
	 */

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * getResponseTime.
	 */
	public Integer getResponseTime() {
		return responseTime;
	}

	/**
	 * setResponseTime.
	 */

	public void setResponseTime(Integer responseTime) {
		this.responseTime = responseTime;
	}

	/**
	 * getIsAlow.
	 */
	public String getIsAlow() {
		return isAlow;
	}

	/**
	 * setIsAlow.
	 */

	public void setIsAlow(String isAlow) {
		this.isAlow = isAlow;
	}

	/**
	 * getAlowMoney.
	 */
	public BigDecimal getAlowMoney() {
		return alowMoney;
	}

	/**
	 * setAlowMoney.
	 */

	public void setAlowMoney(BigDecimal alowMoney) {
		this.alowMoney = alowMoney;
	}

	/**
	 * getDelegateMomey.
	 */
	public BigDecimal getDelegateMomey() {
		return delegateMomey;
	}

	/**
	 * setDelegateMomey.
	 */

	public void setDelegateMomey(BigDecimal delegateMomey) {
		this.delegateMomey = delegateMomey;
	}

	/**
	 * getDelegateDesc.
	 */
	public String getDelegateDesc() {
		return delegateDesc;
	}

	/**
	 * setDelegateDesc.
	 */

	public void setDelegateDesc(String delegateDesc) {
		this.delegateDesc = delegateDesc;
	}

	/**
	 * getWorkAddress.
	 */
	public String getWorkAddress() {
		return workAddress;
	}

	/**
	 * setWorkAddress.
	 */

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	/**
	 * getLongtitude.
	 */
	public BigDecimal getLongtitude() {
		return longtitude;
	}

	/**
	 * setLongtitude.
	 */

	public void setLongtitude(BigDecimal longtitude) {
		this.longtitude = longtitude;
	}

	/**
	 * getLatitude.
	 */
	public BigDecimal getLatitude() {
		return latitude;
	}

	/**
	 * setLatitude.
	 */

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	/**
	 * getLinkMan.
	 */
	public String getLinkMan() {
		return linkMan;
	}

	/**
	 * setLinkMan.
	 */

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	/**
	 * getLinkTel.
	 */
	public String getLinkTel() {
		return linkTel;
	}

	/**
	 * setLinkTel.
	 */

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	/**
	 * getMileage.
	 */
	public BigDecimal getMileage() {
		return mileage;
	}

	/**
	 * setMileage.
	 */

	public void setMileage(BigDecimal mileage) {
		this.mileage = mileage;
	}

	/**
	 * getGetTime.
	 */
	public Date getGetTime() {
		return getTime;
	}

	/**
	 * setGetTime.
	 */

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}

	/**
	 * getFinishTime.
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * setFinishTime.
	 */

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * getSendTime.
	 */

	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * setSendTime.
	 */

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * getPreliminaryTime.
	 */
	public Date getPreliminaryTime() {
		return preliminaryTime;
	}

	/**
	 * setPreliminaryTime.
	 */

	public void setPreliminaryTime(Date preliminaryTime) {
		this.preliminaryTime = preliminaryTime;
	}

	/**
	 * getPreliminaryDesc.
	 */
	public String getPreliminaryDesc() {
		return preliminaryDesc;
	}

	/**
	 * setPreliminaryDesc.
	 */

	public void setPreliminaryDesc(String preliminaryDesc) {
		this.preliminaryDesc = preliminaryDesc;
	}

	/**
	 * getFinalTime.
	 */
	public Date getFinalTime() {
		return finalTime;
	}

	/**
	 * setFinalTime.
	 */

	public void setFinalTime(Date finalTime) {
		this.finalTime = finalTime;
	}

	/**
	 * getFinalDesc.
	 */
	public String getFinalDesc() {
		return finalDesc;
	}

	/**
	 * setFinalDesc.
	 */

	public void setFinalDesc(String finalDesc) {
		this.finalDesc = finalDesc;
	}

	/**
	 * getSendId.
	 */
	public Long getSendId() {
		return sendId;
	}

	/**
	 * setSendId.
	 */

	public void setSendId(Long sendId) {
		this.sendId = sendId;
	}

	/**
	 * getSendIdType.
	 */
	public String getSendIdType() {
		return sendIdType;
	}

	/**
	 * setSendIdType.
	 */

	public void setSendIdType(String sendIdType) {
		this.sendIdType = sendIdType;
	}

	/**
	 * getReviewClass.
	 */
	public String getReviewClass() {
		return reviewClass;
	}

	/**
	 * setReviewClass.
	 */

	public void setReviewClass(String reviewClass) {
		this.reviewClass = reviewClass;
	}

	/**
	 * getReviewTime.
	 */
	public Date getReviewTime() {
		return reviewTime;
	}

	/**
	 * setReviewTime.
	 */

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	/**
	 * getReviewName.
	 */
	public String getReviewName() {
		return reviewName;
	}

	/**
	 * setReviewName.
	 */

	public void setReviewName(String reviewName) {
		this.reviewName = reviewName;
	}

	/**
	 * getReviewType.
	 */
	public String getReviewType() {
		return reviewType;
	}

	/**
	 * setReviewType.
	 */

	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}

	/**
	 * getExt1.
	 */
	public String getExt1() {
		return ext1;
	}

	/**
	 * setExt1.
	 */

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	/**
	 * getExt2.
	 */
	public String getExt2() {
		return ext2;
	}

	/**
	 * setExt2.
	 */

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	/**
	 * getExt3.
	 */
	public String getExt3() {
		return ext3;
	}

	/**
	 * setExt3.
	 */

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	/**
	 * getExt4.
	 */
	public String getExt4() {
		return ext4;
	}

	/**
	 * setExt4.
	 */

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	/**
	 * getExt5.
	 */
	public String getExt5() {
		return ext5;
	}

	/**
	 * setExt5.
	 */

	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

	/**
	 * getExt6.
	 */
	public String getExt6() {
		return ext6;
	}

	/**
	 * setExt6.
	 */

	public void setExt6(String ext6) {
		this.ext6 = ext6;
	}

	/**
	 * getExt7.
	 */
	public String getExt7() {
		return ext7;
	}

	/**
	 * setExt7.
	 */

	public void setExt7(String ext7) {
		this.ext7 = ext7;
	}

	/**
	 * getExt8.
	 */
	public String getExt8() {
		return ext8;
	}

	/**
	 * setExt8.
	 */

	public void setExt8(String ext8) {
		this.ext8 = ext8;
	}

	/**
	 * getGroupUserId.
	 */
	public Long getGroupUserId() {
		return groupUserId;
	}

	/**
	 * setGroupUserId.
	 */

	public void setGroupUserId(Long groupUserId) {
		this.groupUserId = groupUserId;
	}

	/**
	 * getBuyerUserName.
	 */
	public String getBuyerUserName() {
		return buyerUserName;
	}

	/**
	 * setBuyerUserName.
	 */

	public void setBuyerUserName(String buyerUserName) {
		this.buyerUserName = buyerUserName;
	}

	/**
	 * getSellerUserName.
	 */
	public String getSellerUserName() {
		return sellerUserName;
	}

	/**
	 * setSellerUserName.
	 */

	public void setSellerUserName(String sellerUserName) {
		this.sellerUserName = sellerUserName;
	}

	/**
	 * getBuyerMobile.
	 */
	public String getBuyerMobile() {
		return buyerMobile;
	}

	/**
	 * setBuyerMobile.
	 */

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	/**
	 * getCtAddress.
	 */
	public String getCtAddress() {
		return ctAddress;
	}

	/**
	 * setCtAddress.
	 */

	public void setCtAddress(String ctAddress) {
		this.ctAddress = ctAddress;
	}

	/**
	 * getCtLatitude.
	 */
	public BigDecimal getCtLatitude() {
		return ctLatitude;
	}

	/**
	 * setCtLatitude.
	 */

	public void setCtLatitude(BigDecimal ctLatitude) {
		this.ctLatitude = ctLatitude;
	}

	/**
	 * getCtLongtitude.
	 */
	public BigDecimal getCtLongtitude() {
		return ctLongtitude;
	}

	/**
	 * setCtLongtitude.
	 */

	public void setCtLongtitude(BigDecimal ctLongtitude) {
		this.ctLongtitude = ctLongtitude;
	}

	/**
	 * getArrivalHours.
	 */
	public BigDecimal getArrivalHours() {
		return arrivalHours;
	}

	/**
	 * setArrivalHours.
	 */

	public void setArrivalHours(BigDecimal arrivalHours) {
		this.arrivalHours = arrivalHours;
	}

	/**
	 * getExt9.
	 */
	public String getExt9() {
		return ext9;
	}

	/**
	 * setExt9.
	 */

	public void setExt9(String ext9) {
		this.ext9 = ext9;
	}

	/**
	 * getExt10.
	 */
	public String getExt10() {
		return ext10;
	}

	/**
	 * setExt10.
	 */

	public void setExt10(String ext10) {
		this.ext10 = ext10;
	}

	/**
	 * getExt11.
	 */
	public String getExt11() {
		return ext11;
	}

	/**
	 * setExt11.
	 */

	public void setExt11(String ext11) {
		this.ext11 = ext11;
	}

	/**
	 * getExt12.
	 */
	public String getExt12() {
		return ext12;
	}

	/**
	 * setExt12.
	 */

	public void setExt12(String ext12) {
		this.ext12 = ext12;
	}

	/**
	 * getExt13.
	 */
	public String getExt13() {
		return ext13;
	}

	/**
	 * setExt13.
	 */

	public void setExt13(String ext13) {
		this.ext13 = ext13;
	}

	/**
	 * getArrivalTime.
	 */
	public Date getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * setArrivalTime.
	 */

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * getIsNego.
	 */
	public String getIsNego() {
		return isNego;
	}

	/**
	 * setIsNego.
	 */

	public void setIsNego(String isNego) {
		this.isNego = isNego;
	}

	/**
	 * getNegoId.
	 */
	public Long getNegoId() {
		return negoId;
	}

	/**
	 * setNegoId.
	 */

	public void setNegoId(Long negoId) {
		this.negoId = negoId;
	}

	/**
	 * getCommiId.
	 */
	public Long getCommiId() {
		return commiId;
	}

	/**
	 * setCommiId.
	 */

	public void setCommiId(Long commiId) {
		this.commiId = commiId;
	}

	/**
	 * getExt14.
	 */
	public String getExt14() {
		return ext14;
	}

	/**
	 * setExt14.
	 */

	public void setExt14(String ext14) {
		this.ext14 = ext14;
	}

	/**
	 * getExt15.
	 */
	public String getExt15() {
		return ext15;
	}

	/**
	 * setExt15.
	 */

	public void setExt15(String ext15) {
		this.ext15 = ext15;
	}

	/**
	 * getExt16.
	 */
	public String getExt16() {
		return ext16;
	}

	/**
	 * setExt16.
	 */

	public void setExt16(String ext16) {
		this.ext16 = ext16;
	}

	/**
	 * getExt17.
	 */
	public String getExt17() {
		return ext17;
	}

	/**
	 * setExt17.
	 */

	public void setExt17(String ext17) {
		this.ext17 = ext17;
	}

	/**
	 * getExt18.
	 */
	public String getExt18() {
		return ext18;
	}

	/**
	 * setExt18.
	 */

	public void setExt18(String ext18) {
		this.ext18 = ext18;
	}

	public String getImportType() {
		return importType;
	}

	public void setImportType(String importType) {
		this.importType = importType;
	}

	public String getCtArriveInfo() {
		return ctArriveInfo;
	}

	public void setCtArriveInfo(String ctArriveInfo) {
		this.ctArriveInfo = ctArriveInfo;
	}

	public String getReapMoney() {
		return reapMoney;
	}

	public void setReapMoney(String reapMoney) {
		this.reapMoney = reapMoney;
	}

	// ==================红包 wfj 2015-09-06==================
	public String getHasRedPacket() {
		if (StringUtils.isNotBlank(midTypeStr) || StringUtils.isNotBlank(HolTypeStr)) {
			hasRedPacket = "1";
		}
		return hasRedPacket;
	}

	public void setHasRedPacket(String hasRedPacket) {
		this.hasRedPacket = hasRedPacket;
	}

	public String getIsMidnight() {
		if (StringUtils.isNotBlank(midTypeStr)) {
			isMidnight = "1";
		}
		return isMidnight;
	}

	public void setIsMidnight(String isMidnight) {
		this.isMidnight = isMidnight;
	}

	public String getrPTypeStr() {
		if (StringUtils.isNotBlank(midTypeStr)) {
			rPTypeStr = midTypeStr;
		}
		if (StringUtils.isNotBlank(HolTypeStr)) {
			rPTypeStr = HolTypeStr;
		}
		if (StringUtils.isNotBlank(midTypeStr) && StringUtils.isNotBlank(HolTypeStr)) {
			rPTypeStr = midTypeStr + "+" + HolTypeStr;
		}
		return rPTypeStr;
	}

	public void setrPTypeStr(String rPTypeStr) {
		this.rPTypeStr = rPTypeStr;
	}

	public String getrPAmount() {
		return rPAmount;
	}

	public void setrPAmount(String rPAmount) {
		this.rPAmount = rPAmount;
	}

	public void setMidTypeStr(String midTypeStr) {
		this.midTypeStr = midTypeStr;
	}

	public void setHolTypeStr(String holTypeStr) {
		HolTypeStr = holTypeStr;
	}

	public void setrPType(String rPType) {
		this.rPType = rPType;
	}

	public String getrPType() {
		if (StringUtils.isNotBlank(midTypeStr)) {
			rPType = "1";
		}
		if (StringUtils.isNotBlank(HolTypeStr)) {
			rPType = "2";
		}
		if (StringUtils.isNotBlank(midTypeStr) && StringUtils.isNotBlank(HolTypeStr)) {
			rPType = "3";
		}
		return rPType;
	}
	// ==================红包 wfj 2015-09-06==================

	public String getAccdientDesc() {
		return accdientDesc;
	}

	public void setAccdientDesc(String accdientDesc) {
		this.accdientDesc = accdientDesc;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	public String getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(String limitTime) {
		if(null!=limitTime&& !limitTime.isEmpty()&&21==limitTime.length()){
			limitTime = limitTime.substring(0,19);
		}
		this.limitTime = limitTime;
	}

	public String getSupportLinktel() {
		return supportLinktel;
	}

	public void setSupportLinktel(String supportLinktel) {
		this.supportLinktel = supportLinktel;
	}

	public String getSupportLinkman() {
		return supportLinkman;
	}

	public void setSupportLinkman(String supportLinkman) {
		this.supportLinkman = supportLinkman;
	}

	public String getTransportDesc() {
		return transportDesc;
	}

	public void setTransportDesc(String transportDesc) {
		this.transportDesc = transportDesc;
	}

	public String getLimitTimeDesc() {
		return limitTimeDesc;
	}

	public void setLimitTimeDesc(String limitTimeDesc) {
		this.limitTimeDesc = limitTimeDesc;
	}

	public String getEntrustUserName() {
		return entrustUserName;
	}

	public void setEntrustUserName(String entrustUserName) {
		this.entrustUserName = entrustUserName;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getIsRemote() {
		return isRemote;
	}

	public void setIsRemote(String isRemote) {
		this.isRemote = isRemote;
	}

	public Long getInsuerUserId() {
		return insuerUserId;
	}

	public void setInsuerUserId(Long insuerUserId) {
		this.insuerUserId = insuerUserId;
	}	

	public String getAllowEvaluate() {
		return allowEvaluate;
	}

	public void setAllowEvaluate(String allowEvaluate) {
		this.allowEvaluate = allowEvaluate;
	}
	
		public String getIsSimple() {
		return isSimple;
	}

	public void setIsSimple(String isSimple) {
		this.isSimple = isSimple;
	}

	public String getIsFast() {
		return isFast;
	}

	public void setIsFast(String isFast) {
		this.isFast = isFast;
	}

	public String getWithdrawReason() {
		return withdrawReason;
	}

	public void setWithdrawReason(String withdrawReason) {
		this.withdrawReason = withdrawReason;
	}

	public String getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(String withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

	public boolean isShowAllReason() {
		return showAllReason;
	}

	public void setShowAllReason(boolean showAllReason) {
		this.showAllReason = showAllReason;
	}
}
