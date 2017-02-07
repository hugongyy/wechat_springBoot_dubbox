package com.chetong.ctwechat.entity.sms;

public class OneSmsReq {
	private Tel tel = new Tel();
	private String type = "0";
	private String msg = "";
	private String sig = "";
	private String extend = "";
	private String ext = "";

	public Tel getTel() {
		return tel;
	}

	public void setTel(Tel tel) {
		this.tel = tel;
	}

	public void setTel(String phone, String nationcode) {
		this.tel.setNationcode(nationcode);
		this.tel.setPhone(phone);

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

}
