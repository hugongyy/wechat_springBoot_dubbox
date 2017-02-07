package com.chetong.ctwechat.entity.mapping;  

import java.math.BigDecimal;

import com.chetong.ctwechat.entity.model.BasesModel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CtGroup extends BasesModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 属性定义.
	 */
	
	private Long id;	/*--  --*/
	private Long userId;	/*--  --*/
	private Integer serviceCount;	/*-- 加盟服务数 --*/
	private String orgName;	/*-- 机构名称 --*/
	private String orgShortName;	/*--  --*/
	private String orgCode;	/*-- 组织机构代码 --*/
	private String businessCode;	/*-- 营业执照 --*/
	private String faxCode;	/*-- 税务登记证 --*/
	private String postCode;	/*-- 邮编 --*/
	private String connName;	/*-- 联系人 --*/
	private String connDept;	/*-- 联系人部门 --*/
	private String connTel1;	/*-- 联系电话1 --*/
	private String connTel2;	/*-- 联系电话2 --*/
	private String provCode;	/*-- 省份 --*/
	private String cityCode;	/*-- 地市 --*/
	private String areaCode;	/*-- 区县 --*/
	private String provDesc;	/*-- 省份 --*/
	private String cityDesc;	/*-- 地市 --*/
	private String areaDesc;	/*-- 区县 --*/
	private String address;	/*-- 地址 --*/
	private String website;	/*-- 网址 --*/
	private BigDecimal staff;	/*-- 人数 --*/
	private String nature;	/*-- 机构性质 --*/
	private Integer memberCount;	/*-- 团队总人数 --*/
	private String headCompany;	/*-- 总公司 --*/
	private String branchCompany;	/*-- 分公司 --*/
	private String orgType;	/*-- 1:泛华机构 2 保险机构 3 其他机构 --*/
	private String workRequire;	/*-- 作业要求 --*/
	
	private String serviceIdLabel;

	/**
	 * 构造函数.
	 */
	public CtGroup() {}
	
	
	/**
	 * Getter/Setter方法.
	 */
		
	
	/**
	 * getId.
	 */
	public Long getId(){
		return id;
	}
	
	public String getServiceIdLabel() {
		if (nature != null) {
			if ("1".equals(nature)) {
				serviceIdLabel = "查勘,定损,物损,人伤";
			} else if ("4".equals(nature)) {
				serviceIdLabel = "拖车,快修,送油,搭电,换胎,送水";
			} else if ("5".equals(nature)) {
				serviceIdLabel = "病历调取,户籍查询,警鉴";
			} else if ("6".equals(nature)) {
				serviceIdLabel = "货运险,非车险";
			}
		}
		return serviceIdLabel;
	}


	public void setServiceIdLabel(String serviceIdLabel) {
		this.serviceIdLabel = serviceIdLabel;
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
	 * getServiceCount.
	 */
	public Integer getServiceCount(){
		return serviceCount;
	}
	
	/**
   * setServiceCount.
   */
  
	public void setServiceCount(Integer serviceCount){
		this.serviceCount = serviceCount;
	}

		
	/**
	 * getOrgName.
	 */
	public String getOrgName(){
		return orgName;
	}
	
	/**
   * setOrgName.
   */
  
	public void setOrgName(String orgName){
		this.orgName = orgName;
	}

		
	/**
	 * getOrgShortName.
	 */
	public String getOrgShortName(){
		return orgShortName;
	}
	
	/**
   * setOrgShortName.
   */
  
	public void setOrgShortName(String orgShortName){
		this.orgShortName = orgShortName;
	}

		
	/**
	 * getOrgCode.
	 */
	public String getOrgCode(){
		return orgCode;
	}
	
	/**
   * setOrgCode.
   */
  
	public void setOrgCode(String orgCode){
		this.orgCode = orgCode;
	}

		
	/**
	 * getBusinessCode.
	 */
	public String getBusinessCode(){
		return businessCode;
	}
	
	/**
   * setBusinessCode.
   */
  
	public void setBusinessCode(String businessCode){
		this.businessCode = businessCode;
	}

		
	/**
	 * getFaxCode.
	 */
	public String getFaxCode(){
		return faxCode;
	}
	
	/**
   * setFaxCode.
   */
  
	public void setFaxCode(String faxCode){
		this.faxCode = faxCode;
	}

		
	/**
	 * getPostCode.
	 */
	public String getPostCode(){
		return postCode;
	}
	
	/**
   * setPostCode.
   */
  
	public void setPostCode(String postCode){
		this.postCode = postCode;
	}

		
	/**
	 * getConnName.
	 */
	public String getConnName(){
		return connName;
	}
	
	/**
   * setConnName.
   */
  
	public void setConnName(String connName){
		this.connName = connName;
	}

		
	/**
	 * getConnDept.
	 */
	public String getConnDept(){
		return connDept;
	}
	
	/**
   * setConnDept.
   */
  
	public void setConnDept(String connDept){
		this.connDept = connDept;
	}

		
	/**
	 * getConnTel1.
	 */
	public String getConnTel1(){
		return connTel1;
	}
	
	/**
   * setConnTel1.
   */
  
	public void setConnTel1(String connTel1){
		this.connTel1 = connTel1;
	}

		
	/**
	 * getConnTel2.
	 */
	public String getConnTel2(){
		return connTel2;
	}
	
	/**
   * setConnTel2.
   */
  
	public void setConnTel2(String connTel2){
		this.connTel2 = connTel2;
	}

		
	/**
	 * getProvCode.
	 */
	public String getProvCode(){
		return provCode;
	}
	
	/**
   * setProvCode.
   */
  
	public void setProvCode(String provCode){
		this.provCode = provCode;
	}

		
	/**
	 * getCityCode.
	 */
	public String getCityCode(){
		return cityCode;
	}
	
	/**
   * setCityCode.
   */
  
	public void setCityCode(String cityCode){
		this.cityCode = cityCode;
	}

		
	/**
	 * getAreaCode.
	 */
	public String getAreaCode(){
		return areaCode;
	}
	
	/**
   * setAreaCode.
   */
  
	public void setAreaCode(String areaCode){
		this.areaCode = areaCode;
	}

		
	/**
	 * getProvDesc.
	 */
	public String getProvDesc(){
		return provDesc;
	}
	
	/**
   * setProvDesc.
   */
  
	public void setProvDesc(String provDesc){
		this.provDesc = provDesc;
	}

		
	/**
	 * getCityDesc.
	 */
	public String getCityDesc(){
		return cityDesc;
	}
	
	/**
   * setCityDesc.
   */
  
	public void setCityDesc(String cityDesc){
		this.cityDesc = cityDesc;
	}

		
	/**
	 * getAreaDesc.
	 */
	public String getAreaDesc(){
		return areaDesc;
	}
	
	/**
   * setAreaDesc.
   */
  
	public void setAreaDesc(String areaDesc){
		this.areaDesc = areaDesc;
	}

		
	/**
	 * getAddress.
	 */
	public String getAddress(){
		return address;
	}
	
	/**
   * setAddress.
   */
  
	public void setAddress(String address){
		this.address = address;
	}

		
	/**
	 * getWebsite.
	 */
	public String getWebsite(){
		return website;
	}
	
	/**
   * setWebsite.
   */
  
	public void setWebsite(String website){
		this.website = website;
	}

		
	/**
	 * getStaff.
	 */
	public BigDecimal getStaff(){
		return staff;
	}
	
	/**
   * setStaff.
   */
  
	public void setStaff(BigDecimal staff){
		this.staff = staff;
	}

		
	/**
	 * getNature.
	 */
	public String getNature(){
		return nature;
	}
	
	/**
   * setNature.
   */
  
	public void setNature(String nature){
		this.nature = nature;
	}

		
	/**
	 * getMemberCount.
	 */
	public Integer getMemberCount(){
		return memberCount;
	}
	
	/**
   * setMemberCount.
   */
  
	public void setMemberCount(Integer memberCount){
		this.memberCount = memberCount;
	}

		
	/**
	 * getHeadCompany.
	 */
	public String getHeadCompany(){
		return headCompany;
	}
	
	/**
   * setHeadCompany.
   */
  
	public void setHeadCompany(String headCompany){
		this.headCompany = headCompany;
	}

		
	/**
	 * getBranchCompany.
	 */
	public String getBranchCompany(){
		return branchCompany;
	}
	
	/**
   * setBranchCompany.
   */
  
	public void setBranchCompany(String branchCompany){
		this.branchCompany = branchCompany;
	}

		
	/**
	 * getOrgType.
	 */
	public String getOrgType(){
		return orgType;
	}
	
	/**
   * setOrgType.
   */
  
	public void setOrgType(String orgType){
		this.orgType = orgType;
	}

		
	/**
	 * getWorkRequire.
	 */
	public String getWorkRequire(){
		return workRequire;
	}
	
	/**
   * setWorkRequire.
   */
  
	public void setWorkRequire(String workRequire){
		this.workRequire = workRequire;
	}

}
