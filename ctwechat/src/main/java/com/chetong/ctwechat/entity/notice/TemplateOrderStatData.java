package com.chetong.ctwechat.entity.notice;

public class TemplateOrderStatData {
	private TemplateValue first = new TemplateValue();
	private TemplateValue OrderSn = new TemplateValue();
	private TemplateValue OrderStatus = new TemplateValue();
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

	public TemplateValue getOrderSn() {
		return OrderSn;
	}

	public void setOrderSn(TemplateValue orderSn) {
		OrderSn = orderSn;
	}

	public void setOrderSn(String orderSn) {
		OrderSn.setValue(orderSn);
	}

	public TemplateValue getOrderStatus() {
		return OrderStatus;
	}

	public void setOrderStatus(TemplateValue orderStatus) {
		OrderStatus = orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		OrderStatus.setValue(orderStatus);
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
