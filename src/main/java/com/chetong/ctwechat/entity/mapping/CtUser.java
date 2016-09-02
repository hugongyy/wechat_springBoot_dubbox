/*****************************************************
 *
 * Copyright (c) 2015 , 邦泰联合(北京)科技有限公司
 * All rights reserved
 *
 * 文 件 名 : CtUser.java
 * 摘    要 :  
 * 版    本 : 1.0
 * 作    者 : CodeGen
 * 创建日期 : 2015-04-23 11:28:20
 * 备    注 : 本文件由工具自动生成，请勿手动修改。
 *
 *****************************************************/
package com.chetong.ctwechat.entity.mapping;

import java.math.BigDecimal;
import java.util.Date;

import com.chetong.ctwechat.entity.model.BasesModel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CtUser extends BasesModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 属性定义.
	 */

	private Long id; /*--  --*/
	private Long pid; /*--  --*/
	private String userType; /*-- 用户类型 0 - 个人 1 - 机构 2-团队 --*/
	private String isSub; /*-- 是否子账户 0 - 否 1 - 是 --*/
	private String chkStat; /*-- 认证状态 0 - 未认证 1 - 审核中 2 - 已认证（审核通过）3 - 未通过  --*/
	private String stat; /*-- 用户状态 0 - 正常1 - 停用 2 - 黑名单 --*/
	private String acStat; /*-- 账户状态 0 - 正常1 - 冻结 --*/
	private String bankChkStat; /*-- 银行卡审核状态 0 - 等待审核 1 -审核通过 2 - 审核退回  --*/
	private String serviceStat; /*-- 加盟服务状态 0未加盟 1认证中 2未通过 3已加盟 4加盟退出 --*/
	private String origin; /*-- 来源 0 - 用户自注册 1 - 代理注册 --*/
	private String loginName; /*-- 登录名 个人用户为手机号 机构用户为用户名 --*/
	private String loginPwd; /*-- 登录密码 --*/
	private String payPwd; /*-- 支付密码 --*/
	private String email; /*-- 电子邮箱 --*/
	private String lastname; /*-- 姓 --*/
	private String firstname; /*-- 名 --*/
	private String department; /*-- 所在部门 --*/
	private String pin; /*-- 身份证号码 --*/
	private Date birthday; /*-- 生日 --*/
	private String sex; /*-- 性别 0 - 男 1 - 女 --*/
	private String bank; /*-- 开户行 --*/
	private String branch; /*-- 支行 --*/
	private String bankNo; /*-- 账号 --*/
	private String linkBank; /*-- 联行号 --*/
	private String mobile; /*-- 手机 --*/
	private String tel; /*-- 电话 --*/
	private Date regTime; /*-- 注册时间 --*/
	private Date lastTime; /*-- 上次登录时间 --*/
	private String lastIp; /*-- 上次登录ip --*/
	private Integer visitCount; /*-- 访问次数 --*/
	private String welcome; /*-- 用户自设欢迎词 --*/
	private BigDecimal payableBondMoney; /*-- 应缴保证金总额 --*/
	private BigDecimal userMoney; /*-- 账户总额 --*/
	private BigDecimal bondMoney; /*-- 保证金 --*/
	private BigDecimal frozenMoney; /*-- 冻结金额 --*/
	private BigDecimal availableMoney; /*-- 可用余额 可以为负数 ，但该值必须大于 负信用额度 即 availableMoney > - creditMoney --*/
	private BigDecimal creditMoney; /*-- 信用额度   该额度为用户的总信用额度，定值。 --*/
	private BigDecimal totalMoney; /*-- 可用额度 --*/
	private Long operId; /*-- 代理人id --*/
	private Date chkAppTime; /*-- 认证申请时间 --*/
	private Date chkAuditTime; /*-- 认证审核时间 --*/
	private Long chkOperAuditId; /*-- 审核人id --*/
	private String chkAuditReason; /*-- 认证意见 --*/
	private Date bankChkAuditTime; /*-- 银行卡审核时间 --*/
	private Long bankChkOperAuditId; /*-- 银行卡审核人id --*/
	private String bankChkAuditReason; /*-- 银行卡审核意见 --*/
	private String mailProvCode; /*-- 邮寄省份代码 --*/
	private String mailCityCode; /*-- 邮寄地市代码 --*/
	private String mailAreaCode; /*-- 邮寄区县代码 --*/
	private String mailProvDesc; /*-- 邮寄省份 --*/
	private String mailCityDesc; /*-- 邮寄地市 --*/
	private String mailAreaDesc; /*-- 邮寄区县 --*/
	private String mailAddress; /*-- 邮寄地址 --*/
	private String myCredit; /*--  --*/
	private String isFanhua; /*-- 是否泛华员工  1是 0否 --*/
	private String sound; /*-- 推送铃声 --*/
	private String orgName; /*--  --*/
	private String orgShortName; /*--  --*/
	private String signRescue; /*-- 是否与车童网签约救援 0为签约 1已签约 2取消签约 --*/
	private String ext1; /*-- 是否发放礼品 1发放 0 未发放 --*/
	private String ext2; /*--  --*/
	private String ext3; /*-- 车童来源 1 泛华车险 2泛华财险 3经代人员 4保险公司 5公估公司 6修理厂 7其他来源 --*/
	private String ext4; /*-- 自我介绍 --*/
	private String ext5; /*-- 收藏码 --*/
	private String isMgr; /*-- 是否项目经理 1-是 0-否 --*/
	private Integer errorLoginNum; /* 登陆密码错误5次锁定，记录数 */
	private String isSeedPerson;/* 是否是种子车童 */
	private String weichatid;/* 项目经理微信id wfj 150807 */

	private String token;
	private String tokenTime;
	
	private BigDecimal bondMoneyHy; /*-- 货运险保证金 --*/

	private String lazyId;

	/**
	 * 构造函数.
	 */
	public CtUser() {
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

	public BigDecimal getBondMoneyHy() {
		return bondMoneyHy;
	}

	public void setBondMoneyHy(BigDecimal bondMoneyHy) {
		this.bondMoneyHy = bondMoneyHy;
	}

	/**
	 * setId.
	 */

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * getPid.
	 */
	public Long getPid() {
		return pid;
	}

	/**
	 * setPid.
	 */

	public void setPid(Long pid) {
		this.pid = pid;
	}

	/**
	 * getUserType.
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * setUserType.
	 */

	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * getIsSub.
	 */
	public String getIsSub() {
		return isSub;
	}

	/**
	 * setIsSub.
	 */

	public void setIsSub(String isSub) {
		this.isSub = isSub;
	}

	/**
	 * getChkStat.
	 */
	public String getChkStat() {
		return chkStat;
	}

	/**
	 * setChkStat.
	 */

	public void setChkStat(String chkStat) {
		this.chkStat = chkStat;
	}

	/**
	 * getStat.
	 */
	public String getStat() {
		return stat;
	}

	/**
	 * setStat.
	 */

	public void setStat(String stat) {
		this.stat = stat;
	}

	/**
	 * getAcStat.
	 */
	public String getAcStat() {
		return acStat;
	}

	/**
	 * setAcStat.
	 */

	public void setAcStat(String acStat) {
		this.acStat = acStat;
	}

	/**
	 * getBankChkStat.
	 */
	public String getBankChkStat() {
		return bankChkStat;
	}

	/**
	 * setBankChkStat.
	 */

	public void setBankChkStat(String bankChkStat) {
		this.bankChkStat = bankChkStat;
	}

	/**
	 * getServiceStat.
	 */
	public String getServiceStat() {
		return serviceStat;
	}

	/**
	 * setServiceStat.
	 */

	public void setServiceStat(String serviceStat) {
		this.serviceStat = serviceStat;
	}

	/**
	 * getOrigin.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * setOrigin.
	 */

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * getLoginName.
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * setLoginName.
	 */

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * getLoginPwd.
	 */
	public String getLoginPwd() {
		return loginPwd;
	}

	/**
	 * setLoginPwd.
	 */

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	/**
	 * getPayPwd.
	 */
	public String getPayPwd() {
		return payPwd;
	}

	/**
	 * setPayPwd.
	 */

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	/**
	 * getEmail.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * setEmail.
	 */

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * getLastname.
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * setLastname.
	 */

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * getFirstname.
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * setFirstname.
	 */

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * getDepartment.
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * setDepartment.
	 */

	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * getPin.
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * setPin.
	 */

	public void setPin(String pin) {
		this.pin = pin;
	}

	/**
	 * getBirthday.
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * setBirthday.
	 */

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * getSex.
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * setSex.
	 */

	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * getBank.
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * setBank.
	 */

	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * getBranch.
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * setBranch.
	 */

	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * getBankNo.
	 */
	public String getBankNo() {
		return bankNo;
	}

	/**
	 * setBankNo.
	 */

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	/**
	 * getLinkBank.
	 */
	public String getLinkBank() {
		return linkBank;
	}

	/**
	 * setLinkBank.
	 */

	public void setLinkBank(String linkBank) {
		this.linkBank = linkBank;
	}

	/**
	 * getMobile.
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * setMobile.
	 */

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * getTel.
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * setTel.
	 */

	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * getRegTime.
	 */
	public Date getRegTime() {
		return regTime;
	}

	/**
	 * setRegTime.
	 */

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	/**
	 * getLastTime.
	 */
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * setLastTime.
	 */

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * getLastIp.
	 */
	public String getLastIp() {
		return lastIp;
	}

	/**
	 * setLastIp.
	 */

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	/**
	 * getVisitCount.
	 */
	public Integer getVisitCount() {
		return visitCount;
	}

	/**
	 * setVisitCount.
	 */

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	/**
	 * getWelcome.
	 */
	public String getWelcome() {
		return welcome;
	}

	/**
	 * setWelcome.
	 */

	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}

	/**
	 * getPayableBondMoney.
	 */
	public BigDecimal getPayableBondMoney() {
		return payableBondMoney;
	}

	/**
	 * setPayableBondMoney.
	 */

	public void setPayableBondMoney(BigDecimal payableBondMoney) {
		this.payableBondMoney = payableBondMoney;
	}

	/**
	 * getUserMoney.
	 */
	public BigDecimal getUserMoney() {
		return userMoney;
	}

	/**
	 * setUserMoney.
	 */

	public void setUserMoney(BigDecimal userMoney) {
		this.userMoney = userMoney;
	}

	/**
	 * getBondMoney.
	 */
	public BigDecimal getBondMoney() {
		return bondMoney;
	}

	/**
	 * setBondMoney.
	 */

	public void setBondMoney(BigDecimal bondMoney) {
		this.bondMoney = bondMoney;
	}

	/**
	 * getFrozenMoney.
	 */
	public BigDecimal getFrozenMoney() {
		return frozenMoney;
	}

	/**
	 * setFrozenMoney.
	 */

	public void setFrozenMoney(BigDecimal frozenMoney) {
		this.frozenMoney = frozenMoney;
	}

	/**
	 * getAvailableMoney.
	 */
	public BigDecimal getAvailableMoney() {
		return availableMoney;
	}

	/**
	 * setAvailableMoney.
	 */

	public void setAvailableMoney(BigDecimal availableMoney) {
		this.availableMoney = availableMoney;
	}

	/**
	 * getCreditMoney.
	 */
	public BigDecimal getCreditMoney() {
		return creditMoney;
	}

	/**
	 * setCreditMoney.
	 */

	public void setCreditMoney(BigDecimal creditMoney) {
		this.creditMoney = creditMoney;
	}

	/**
	 * getTotalMoney.
	 */
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	/**
	 * setTotalMoney.
	 */

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	/**
	 * getOperId.
	 */
	public Long getOperId() {
		return operId;
	}

	/**
	 * setOperId.
	 */

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	/**
	 * getChkAppTime.
	 */
	public Date getChkAppTime() {
		return chkAppTime;
	}

	/**
	 * setChkAppTime.
	 */

	public void setChkAppTime(Date chkAppTime) {
		this.chkAppTime = chkAppTime;
	}

	/**
	 * getChkAuditTime.
	 */
	public Date getChkAuditTime() {
		return chkAuditTime;
	}

	/**
	 * setChkAuditTime.
	 */

	public void setChkAuditTime(Date chkAuditTime) {
		this.chkAuditTime = chkAuditTime;
	}

	/**
	 * getChkOperAuditId.
	 */
	public Long getChkOperAuditId() {
		return chkOperAuditId;
	}

	/**
	 * setChkOperAuditId.
	 */

	public void setChkOperAuditId(Long chkOperAuditId) {
		this.chkOperAuditId = chkOperAuditId;
	}

	/**
	 * getChkAuditReason.
	 */
	public String getChkAuditReason() {
		return chkAuditReason;
	}

	/**
	 * setChkAuditReason.
	 */

	public void setChkAuditReason(String chkAuditReason) {
		this.chkAuditReason = chkAuditReason;
	}

	/**
	 * getBankChkAuditTime.
	 */
	public Date getBankChkAuditTime() {
		return bankChkAuditTime;
	}

	/**
	 * setBankChkAuditTime.
	 */

	public void setBankChkAuditTime(Date bankChkAuditTime) {
		this.bankChkAuditTime = bankChkAuditTime;
	}

	/**
	 * getBankChkOperAuditId.
	 */
	public Long getBankChkOperAuditId() {
		return bankChkOperAuditId;
	}

	/**
	 * setBankChkOperAuditId.
	 */

	public void setBankChkOperAuditId(Long bankChkOperAuditId) {
		this.bankChkOperAuditId = bankChkOperAuditId;
	}

	/**
	 * getBankChkAuditReason.
	 */
	public String getBankChkAuditReason() {
		return bankChkAuditReason;
	}

	/**
	 * setBankChkAuditReason.
	 */

	public void setBankChkAuditReason(String bankChkAuditReason) {
		this.bankChkAuditReason = bankChkAuditReason;
	}

	/**
	 * getMailProvCode.
	 */
	public String getMailProvCode() {
		return mailProvCode;
	}

	/**
	 * setMailProvCode.
	 */

	public void setMailProvCode(String mailProvCode) {
		this.mailProvCode = mailProvCode;
	}

	/**
	 * getMailCityCode.
	 */
	public String getMailCityCode() {
		return mailCityCode;
	}

	/**
	 * setMailCityCode.
	 */

	public void setMailCityCode(String mailCityCode) {
		this.mailCityCode = mailCityCode;
	}

	/**
	 * getMailAreaCode.
	 */
	public String getMailAreaCode() {
		return mailAreaCode;
	}

	/**
	 * setMailAreaCode.
	 */

	public void setMailAreaCode(String mailAreaCode) {
		this.mailAreaCode = mailAreaCode;
	}

	/**
	 * getMailProvDesc.
	 */
	public String getMailProvDesc() {
		return mailProvDesc;
	}

	/**
	 * setMailProvDesc.
	 */

	public void setMailProvDesc(String mailProvDesc) {
		this.mailProvDesc = mailProvDesc;
	}

	/**
	 * getMailCityDesc.
	 */
	public String getMailCityDesc() {
		return mailCityDesc;
	}

	/**
	 * setMailCityDesc.
	 */

	public void setMailCityDesc(String mailCityDesc) {
		this.mailCityDesc = mailCityDesc;
	}

	/**
	 * getMailAreaDesc.
	 */
	public String getMailAreaDesc() {
		return mailAreaDesc;
	}

	/**
	 * setMailAreaDesc.
	 */

	public void setMailAreaDesc(String mailAreaDesc) {
		this.mailAreaDesc = mailAreaDesc;
	}

	/**
	 * getMailAddress.
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * setMailAddress.
	 */

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * getMyCredit.
	 */
	public String getMyCredit() {
		return myCredit;
	}

	/**
	 * setMyCredit.
	 */

	public void setMyCredit(String myCredit) {
		this.myCredit = myCredit;
	}

	/**
	 * getIsFanhua.
	 */
	public String getIsFanhua() {
		return isFanhua;
	}

	/**
	 * setIsFanhua.
	 */

	public void setIsFanhua(String isFanhua) {
		this.isFanhua = isFanhua;
	}

	/**
	 * getSound.
	 */
	public String getSound() {
		return sound;
	}

	/**
	 * setSound.
	 */

	public void setSound(String sound) {
		this.sound = sound;
	}

	/**
	 * getOrgName.
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * setOrgName.
	 */

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * getOrgShortName.
	 */
	public String getOrgShortName() {
		return orgShortName;
	}

	/**
	 * setOrgShortName.
	 */

	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}

	/**
	 * getSignRescue.
	 */
	public String getSignRescue() {
		return signRescue;
	}

	/**
	 * setSignRescue.
	 */

	public void setSignRescue(String signRescue) {
		this.signRescue = signRescue;
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
	 * getIsMgr.
	 */
	public String getIsMgr() {
		return isMgr;
	}

	/**
	 * setIsMgr.
	 */

	public void setIsMgr(String isMgr) {
		this.isMgr = isMgr;
	}

	public Integer getErrorLoginNum() {
		return errorLoginNum;
	}

	public void setErrorLoginNum(Integer errorLoginNum) {
		this.errorLoginNum = errorLoginNum;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenTime() {
		return tokenTime;
	}

	public void setTokenTime(String tokenTime) {
		this.tokenTime = tokenTime;
	}

	public String getIsSeedPerson() {
		return isSeedPerson;
	}

	public void setIsSeedPerson(String isSeedPerson) {
		this.isSeedPerson = isSeedPerson;
	}

	public String getWeichatid() {
		return weichatid;
	}

	public void setWeichatid(String weichatid) {
		this.weichatid = weichatid;
	}

	public String getLazyId() {
		return lazyId;
	}

	public void setLazyId(String lazyId) {
		this.lazyId = lazyId;
	}

}
