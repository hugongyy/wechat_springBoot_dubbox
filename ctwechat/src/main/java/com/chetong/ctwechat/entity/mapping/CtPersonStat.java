package com.chetong.ctwechat.entity.mapping; 

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CtPersonStat implements java.io.Serializable {

	/**
	 * 属性定义.
	 */
	
	private Long id;	/*--  --*/
	private Long userId;	/*--  --*/
	private Long serviceId;	/*--  --*/
	private String serviceName;	/*-- 服务类别 --*/
	private String isBusy;	/*-- 是否置忙 1-忙 0 - 闲 --*/
	private Integer currOrderCount;	/*-- 当前接单量 --*/
	private BigDecimal longitude;	/*-- 经度 --*/
	private BigDecimal dimension;	/*-- 维度 --*/
	private Date lastNotifyTime;	/*-- 上次通讯时间 --*/
	private String ext1;	/*-- 1开启推送服务，0未开启推送服务 --*/
	private String ext2;	/*-- 扩展字段2 --*/
	private String ext3;	/*-- 扩展字段3 --*/

	/**
	 * 构造函数.
	 */
	public CtPersonStat() {}
	
	
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
	 * getServiceId.
	 */
	public Long getServiceId(){
		return serviceId;
	}
	
	/**
   * setServiceId.
   */
  
	public void setServiceId(Long serviceId){
		this.serviceId = serviceId;
	}

		
	/**
	 * getServiceName.
	 */
	public String getServiceName(){
		return serviceName;
	}
	
	/**
   * setServiceName.
   */
  
	public void setServiceName(String serviceName){
		this.serviceName = serviceName;
	}

		
	/**
	 * getIsBusy.
	 */
	public String getIsBusy(){
		return isBusy;
	}
	
	/**
   * setIsBusy.
   */
  
	public void setIsBusy(String isBusy){
		this.isBusy = isBusy;
	}

		
	/**
	 * getCurrOrderCount.
	 */
	public Integer getCurrOrderCount(){
		return currOrderCount;
	}
	
	/**
   * setCurrOrderCount.
   */
  
	public void setCurrOrderCount(Integer currOrderCount){
		this.currOrderCount = currOrderCount;
	}

		
	/**
	 * getLongitude.
	 */
	public BigDecimal getLongitude(){
		return longitude;
	}
	
	/**
   * setLongitude.
   */
  
	public void setLongitude(BigDecimal longitude){
		this.longitude = longitude;
	}

		
	/**
	 * getDimension.
	 */
	public BigDecimal getDimension(){
		return dimension;
	}
	
	/**
   * setDimension.
   */
  
	public void setDimension(BigDecimal dimension){
		this.dimension = dimension;
	}

		
	/**
	 * getLastNotifyTime.
	 */
	public Date getLastNotifyTime(){
		return lastNotifyTime;
	}
	
	/**
   * setLastNotifyTime.
   */
  
	public void setLastNotifyTime(Date lastNotifyTime){
		this.lastNotifyTime = lastNotifyTime;
	}

		
	/**
	 * getExt1.
	 */
	public String getExt1(){
		return ext1;
	}
	
	/**
   * setExt1.
   */
  
	public void setExt1(String ext1){
		this.ext1 = ext1;
	}

		
	/**
	 * getExt2.
	 */
	public String getExt2(){
		return ext2;
	}
	
	/**
   * setExt2.
   */
  
	public void setExt2(String ext2){
		this.ext2 = ext2;
	}

		
	/**
	 * getExt3.
	 */
	public String getExt3(){
		return ext3;
	}
	
	/**
   * setExt3.
   */
  
	public void setExt3(String ext3){
		this.ext3 = ext3;
	}

}
