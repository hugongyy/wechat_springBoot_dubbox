package com.chetong.ctwechat.entity.sms;

public class SendSmsResp {
	private String user_receive_time; // 用户实际接收到短信的时间
	private String nationcode;// 国家码
	private String mobile;// 手机号码
	private String report_status;// 实际是否收到短信接收状态。SUCCESS（成功）、FAIL（失败）
	private String errmsg;// 用户接收短信状态码
	private String description;// 用户接收短信状态描述
	private String sid;// 标识本次发送id

	public String getUser_receive_time() {
		return user_receive_time;
	}

	public void setUser_receive_time(String user_receive_time) {
		this.user_receive_time = user_receive_time;
	}

	public String getNationcode() {
		return nationcode;
	}

	public void setNationcode(String nationcode) {
		this.nationcode = nationcode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReport_status() {
		return report_status;
	}

	public void setReport_status(String report_status) {
		this.report_status = report_status;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

}
