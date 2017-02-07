package com.chetong.ctwechat.entity.notice;

public class TemplateNewOrderData {
	private TemplateValue first = new TemplateValue();
	private TemplateValue keyword1 = new TemplateValue();
	private TemplateValue keyword2 = new TemplateValue();
	private TemplateValue keyword3 = new TemplateValue();
	private TemplateValue keyword4 = new TemplateValue();
	private TemplateValue keyword5 = new TemplateValue();
	private TemplateValue remark = new TemplateValue();

	public TemplateValue getFirst() {
		return first;
	}

	public void setFirst(TemplateValue first) {
		this.first = first;
	}

	public void setFirst(String first) {
		this.first.setValue(first);
	}

	public TemplateValue getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(TemplateValue keyword1) {
		this.keyword1 = keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1.setValue(keyword1);
	}

	public TemplateValue getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(TemplateValue keyword2) {
		this.keyword2 = keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2.setValue(keyword2);
	}

	public TemplateValue getKeyword3() {
		return keyword3;
	}

	public void setKeyword3(TemplateValue keyword3) {
		this.keyword3 = keyword3;
	}

	public void setKeyword3(String keyword3) {
		this.keyword3.setValue(keyword3);
	}

	public TemplateValue getKeyword4() {
		return keyword4;
	}

	public void setKeyword4(TemplateValue keyword4) {
		this.keyword4 = keyword4;
	}

	public void setKeyword4(String keyword4) {
		this.keyword4.setValue(keyword4);
	}

	public TemplateValue getKeyword5() {
		return keyword5;
	}

	public void setKeyword5(TemplateValue keyword5) {
		this.keyword5 = keyword5;
	}

	public void setKeyword5(String keyword5) {
		this.keyword5.setValue(keyword5);
	}

	public TemplateValue getRemark() {
		return remark;
	}

	public void setRemark(TemplateValue remark) {
		this.remark = remark;
	}

	public void setRemark(String remark) {
		this.remark.setValue(remark);
	}

}
