package com.chetong.ctwechat.entity.mapping;  

import java.math.BigDecimal;
import java.util.Date;

public class CmsTemplateInfo implements java.io.Serializable {

	/**
	 * 属性定义.
	 */
	
	private Long id;	/*--  --*/
	private String templateType;	/*-- 类型,1-短信,2-邮件,3-微信推送 --*/
	private String templateNo;	/*-- 编号 --*/
	private String templateInfo;	/*-- 消息模板,关键字用#orderno#. --*/
	private Long createId;	/*-- 创建者 --*/
	private Date createTime;	/*-- 创建时间 --*/
	private Long updateId;	/*-- 修改者 --*/
	private Date updateTime;	/*-- 修改时间 --*/

	/**
	 * 构造函数.
	 */
	public CmsTemplateInfo() {}
	
	
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
	 * getTemplateType.
	 */
	public String getTemplateType(){
		return templateType;
	}
	
	/**
   * setTemplateType.
   */
  
	public void setTemplateType(String templateType){
		this.templateType = templateType;
	}

		
	/**
	 * getTemplateNo.
	 */
	public String getTemplateNo(){
		return templateNo;
	}
	
	/**
   * setTemplateNo.
   */
  
	public void setTemplateNo(String templateNo){
		this.templateNo = templateNo;
	}

		
	/**
	 * getTemplateInfo.
	 */
	public String getTemplateInfo(){
		return templateInfo;
	}
	
	/**
   * setTemplateInfo.
   */
  
	public void setTemplateInfo(String templateInfo){
		this.templateInfo = templateInfo;
	}

		
	/**
	 * getCreateId.
	 */
	public Long getCreateId(){
		return createId;
	}
	
	/**
   * setCreateId.
   */
  
	public void setCreateId(Long createId){
		this.createId = createId;
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

		
	/**
	 * getUpdateId.
	 */
	public Long getUpdateId(){
		return updateId;
	}
	
	/**
   * setUpdateId.
   */
  
	public void setUpdateId(Long updateId){
		this.updateId = updateId;
	}

		
	/**
	 * getUpdateTime.
	 */
	public Date getUpdateTime(){
		return updateTime;
	}
	
	/**
   * setUpdateTime.
   */
  
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

}
