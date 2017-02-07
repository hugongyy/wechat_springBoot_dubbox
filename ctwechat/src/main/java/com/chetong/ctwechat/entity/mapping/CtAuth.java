package com.chetong.ctwechat.entity.mapping;  
import java.io.Serializable;

import com.chetong.aic.entity.base.BaseModel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * 
 * 权限表
 * 
 **/
@SuppressWarnings("serial")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CtAuth extends BaseModel implements Serializable {
	
	/****/
	private Long id;

	/**父ID**/
	private Long pid;

	/**项目ID:0-ALL,1-PC,2-APP,3-WeChat**/
	private Long appId;

	/**根节点为0，其他依次类推**/
	private Integer nodeLevel;

	/**权限名称**/
	private String authName;

	/**权限描述**/
	private String authDesc;

	/**资源类型 1- 项目名称 2 - 菜单名称 3 - 一般资源**/
	private String authType;

	/**url地址,或按钮的标识**/
	private String authKey;

	/**显示排序**/
	private Integer sortOrder;

	/**对应的接口路径**/
	private String authInterface;

	/**是否可以 0 - 可用 1 - 不可用**/
	private String enable;

	/**创建者ID**/
	private Long createId;

	/**创建时间**/
	private String createTime;

	/**扩展字段1**/
	private String ext1;

	/**扩展字段2**/
	private String ext2;

	/**扩展字段3**/
	private String ext3;



	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setPid(Long pid){
		this.pid = pid;
	}

	public Long getPid(){
		return this.pid;
	}

	public void setAppId(Long appId){
		this.appId = appId;
	}

	public Long getAppId(){
		return this.appId;
	}

	public void setNodeLevel(Integer nodeLevel){
		this.nodeLevel = nodeLevel;
	}

	public Integer getNodeLevel(){
		return this.nodeLevel;
	}

	public void setAuthName(String authName){
		this.authName = authName;
	}

	public String getAuthName(){
		return this.authName;
	}

	public void setAuthDesc(String authDesc){
		this.authDesc = authDesc;
	}

	public String getAuthDesc(){
		return this.authDesc;
	}

	public void setAuthType(String authType){
		this.authType = authType;
	}

	public String getAuthType(){
		return this.authType;
	}

	public void setAuthKey(String authKey){
		this.authKey = authKey;
	}

	public String getAuthKey(){
		return this.authKey;
	}

	public void setSortOrder(Integer sortOrder){
		this.sortOrder = sortOrder;
	}

	public Integer getSortOrder(){
		return this.sortOrder;
	}

	public void setAuthInterface(String authInterface){
		this.authInterface = authInterface;
	}

	public String getAuthInterface(){
		return this.authInterface;
	}

	public void setEnable(String enable){
		this.enable = enable;
	}

	public String getEnable(){
		return this.enable;
	}

	public void setCreateId(Long createId){
		this.createId = createId;
	}

	public Long getCreateId(){
		return this.createId;
	}

	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}

	public String getCreateTime(){
		return this.createTime;
	}

	public void setExt1(String ext1){
		this.ext1 = ext1;
	}

	public String getExt1(){
		return this.ext1;
	}

	public void setExt2(String ext2){
		this.ext2 = ext2;
	}

	public String getExt2(){
		return this.ext2;
	}

	public void setExt3(String ext3){
		this.ext3 = ext3;
	}

	public String getExt3(){
		return this.ext3;
	}

}
