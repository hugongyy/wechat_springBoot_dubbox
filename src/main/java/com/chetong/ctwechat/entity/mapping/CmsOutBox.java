package com.chetong.ctwechat.entity.mapping;  

import java.math.BigDecimal;
import java.util.Date;

public class CmsOutBox implements java.io.Serializable {

	/**
	 * 属性定义.
	 */
	
	private Long id;	/*--  --*/
	private Long userId;	/*-- 用户id --*/
	private String msgType;	/*-- 消息类型:1-微信推送,2-短信 --*/
	private String msgTo;	/*-- 消息发送给(微信:openid,短信:手机号码) --*/
	private String msgFrom;	/*-- 消息来源 --*/
	private String orderNo;	/*-- 订单号 --*/
	private String orderType;	/*-- 订单类型 --*/
	private String msgContent;	/*-- 消息内容 --*/
	private String sendFlag;	/*-- 发送标志:0-未发送,1-发送成功,其他-发送失败 --*/
	private Date sendTime;	/*-- 发送时间 --*/
	private Integer sendNum;	/*-- 发送次数 --*/
	private String createBy;	/*-- 创建人 --*/
	private Date createTime;	/*-- 创建时间 --*/

	/**
	 * 构造函数.
	 */
	public CmsOutBox() {}
	
	
	/**
	 * Getter/Setter方法.
	 */
		
	/**
	 * getId.
	 */
	public Long getId(){
		return id;
	}
	
	/**
   * setId.
   */
  
	public void setId(Long id){
		this.id = id;
	}

		
	/**
	 * getUserId.
	 */
	public Long getUserId(){
		return userId;
	}
	
	/**
   * setUserId.
   */
  
	public void setUserId(Long userId){
		this.userId = userId;
	}

		
	/**
	 * getMsgType.
	 */
	public String getMsgType(){
		return msgType;
	}
	
	/**
   * setMsgType.
   */
  
	public void setMsgType(String msgType){
		this.msgType = msgType;
	}

		
	/**
	 * getMsgTo.
	 */
	public String getMsgTo(){
		return msgTo;
	}
	
	/**
   * setMsgTo.
   */
  
	public void setMsgTo(String msgTo){
		this.msgTo = msgTo;
	}

		
	/**
	 * getMsgFrom.
	 */
	public String getMsgFrom(){
		return msgFrom;
	}
	
	/**
   * setMsgFrom.
   */
  
	public void setMsgFrom(String msgFrom){
		this.msgFrom = msgFrom;
	}

		
	/**
	 * getOrderNo.
	 */
	public String getOrderNo(){
		return orderNo;
	}
	
	/**
   * setOrderNo.
   */
  
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}

		
	/**
	 * getOrderType.
	 */
	public String getOrderType(){
		return orderType;
	}
	
	/**
   * setOrderType.
   */
  
	public void setOrderType(String orderType){
		this.orderType = orderType;
	}

		
	/**
	 * getMsgContent.
	 */
	public String getMsgContent(){
		return msgContent;
	}
	
	/**
   * setMsgContent.
   */
  
	public void setMsgContent(String msgContent){
		this.msgContent = msgContent;
	}

		
	/**
	 * getSendFlag.
	 */
	public String getSendFlag(){
		return sendFlag;
	}
	
	/**
   * setSendFlag.
   */
  
	public void setSendFlag(String sendFlag){
		this.sendFlag = sendFlag;
	}

		
	/**
	 * getSendTime.
	 */
	public Date getSendTime(){
		return sendTime;
	}
	
	/**
   * setSendTime.
   */
  
	public void setSendTime(Date sendTime){
		this.sendTime = sendTime;
	}

		
	/**
	 * getSendNum.
	 */
	public Integer getSendNum(){
		return sendNum;
	}
	
	/**
   * setSendNum.
   */
  
	public void setSendNum(Integer sendNum){
		this.sendNum = sendNum;
	}

		
	/**
	 * getCreateBy.
	 */
	public String getCreateBy(){
		return createBy;
	}
	
	/**
   * setCreateBy.
   */
  
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}

		
	/**
	 * getCreateTime.
	 */
	public Date getCreateTime(){
		return createTime;
	}
	
	/**
   * setCreateTime.
   */
  
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

}
