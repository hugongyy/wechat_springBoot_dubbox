package com.chetong.ctwechat.entity.mapping;

import com.chetong.ctwechat.entity.model.BasesModel;

public class SysSms extends BasesModel implements java.io.Serializable {

	/**
	 * 属性定义.
	 */

	private Long id; /*--  --*/
	private String smsType; /*-- 短信类型:0:发短信, 1:收短信 --*/
	private String mobileNumber; /*-- 电话号码 --*/
	private String smsContent; /*-- 短信内容 --*/
	private String channelNumber; /*-- 通道号 --*/
	private String addSerialRav; /*-- 附加码 --*/
	private String addSerial; /*-- 附加码 --*/
	private String sentTime; /*-- 上行短信发送时间 --*/
	private String smsStatus; /*-- 短信状态 --*/
	private String dealStatus; /*-- 处理状态 --*/
	private String smsRemark; /*-- 短信备注 --*/
	private String remarkCreater; /*-- 备注创建者 --*/
	private String createTime; /*-- 记录创建时间,也是下行短信发送时间 --*/
	private String receiverId; /*-- 接收方 --*/
	private String senderId; /*-- 发送方 --*/
	private String replyPid; /*-- 回复短信的主id --*/
	private String ext1; /*--  --*/
	private String ext2; /*--  --*/
	private String ext3; /*--  --*/


	/**
	 * 构造函数.
	 */
	public SysSms() {
	}

	/**
	 * Getter/Setter方法.
	 */

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
	 * getSmsType.
	 */
	public String getSmsType() {
		return smsType;
	}

	/**
	 * setSmsType.
	 */

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	/**
	 * getMobileNumber.
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * setMobileNumber.
	 */

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * getSmsContent.
	 */
	public String getSmsContent() {
		return smsContent;
	}

	/**
	 * setSmsContent.
	 */

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	/**
	 * getChannelNumber.
	 */
	public String getChannelNumber() {
		return channelNumber;
	}

	/**
	 * setChannelNumber.
	 */

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	/**
	 * getAddSerialRav.
	 */
	public String getAddSerialRav() {
		return addSerialRav;
	}

	/**
	 * setAddSerialRav.
	 */

	public void setAddSerialRav(String addSerialRav) {
		this.addSerialRav = addSerialRav;
	}

	/**
	 * getAddSerial.
	 */
	public String getAddSerial() {
		return addSerial;
	}

	/**
	 * setAddSerial.
	 */

	public void setAddSerial(String addSerial) {
		this.addSerial = addSerial;
	}

	/**
	 * getSentTime.
	 */
	public String getSentTime() {
		return sentTime;
	}

	/**
	 * setSentTime.
	 */

	public void setSentTime(String sentTime) {
		this.sentTime = sentTime;
	}

	/**
	 * getSmsStatus.
	 */
	public String getSmsStatus() {
		return smsStatus;
	}

	/**
	 * setSmsStatus.
	 */

	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	/**
	 * getDealStatus.
	 */
	public String getDealStatus() {
		return dealStatus;
	}

	/**
	 * setDealStatus.
	 */

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	/**
	 * getSmsRemark.
	 */
	public String getSmsRemark() {
		return smsRemark;
	}

	/**
	 * setSmsRemark.
	 */

	public void setSmsRemark(String smsRemark) {
		this.smsRemark = smsRemark;
	}

	/**
	 * getRemarkCreater.
	 */
	public String getRemarkCreater() {
		return remarkCreater;
	}

	/**
	 * setRemarkCreater.
	 */

	public void setRemarkCreater(String remarkCreater) {
		this.remarkCreater = remarkCreater;
	}

	/**
	 * getCreateTime.
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * setCreateTime.
	 */

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * getReceiverId.
	 */
	public String getReceiverId() {
		return receiverId;
	}

	/**
	 * setReceiverId.
	 */

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	/**
	 * getSenderId.
	 */
	public String getSenderId() {
		return senderId;
	}

	/**
	 * setSenderId.
	 */

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	/**
	 * getReplyPid.
	 */
	public String getReplyPid() {
		return replyPid;
	}

	/**
	 * setReplyPid.
	 */

	public void setReplyPid(String replyPid) {
		this.replyPid = replyPid;
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

}
