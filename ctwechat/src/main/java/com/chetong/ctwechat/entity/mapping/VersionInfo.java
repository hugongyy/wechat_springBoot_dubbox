package com.chetong.ctwechat.entity.mapping; 

import java.math.BigDecimal;
import java.util.Date;

public class VersionInfo implements java.io.Serializable {

	/**
	 * 属性定义.
	 */
	
	private Long id;	/*--  --*/
	private String versionType;	/*-- 版本类型ios，android --*/
	private String versionCode;	/*-- 版本号 --*/
	private String versionContent;	/*-- 版本更新说明 --*/
	private String versionUrl;	/*-- app下载url --*/
	private Date versionDate;	/*-- 版本更新日期 --*/

	/**
	 * 构造函数.
	 */
	public VersionInfo() {}
	
	
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
	 * getVersionType.
	 */
	public String getVersionType(){
		return versionType;
	}
	
	/**
   * setVersionType.
   */
  
	public void setVersionType(String versionType){
		this.versionType = versionType;
	}

		
	/**
	 * getVersionCode.
	 */
	public String getVersionCode(){
		return versionCode;
	}
	
	/**
   * setVersionCode.
   */
  
	public void setVersionCode(String versionCode){
		this.versionCode = versionCode;
	}

		
	/**
	 * getVersionContent.
	 */
	public String getVersionContent(){
		return versionContent;
	}
	
	/**
   * setVersionContent.
   */
  
	public void setVersionContent(String versionContent){
		this.versionContent = versionContent;
	}

		
	/**
	 * getVersionUrl.
	 */
	public String getVersionUrl(){
		return versionUrl;
	}
	
	/**
   * setVersionUrl.
   */
  
	public void setVersionUrl(String versionUrl){
		this.versionUrl = versionUrl;
	}

		
	/**
	 * getVersionDate.
	 */
	public Date getVersionDate(){
		return versionDate;
	}
	
	/**
   * setVersionDate.
   */
  
	public void setVersionDate(Date versionDate){
		this.versionDate = versionDate;
	}

}
