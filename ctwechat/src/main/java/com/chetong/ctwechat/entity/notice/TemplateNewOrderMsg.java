package com.chetong.ctwechat.entity.notice;

public class TemplateNewOrderMsg extends TemplateMsg {
	private String template_id = "t8y2ogBMXICoISLsmT8W55fRct6kiw2cU9vDndESK_Y"; // 新订单通知
	public TemplateNewOrderData data = new TemplateNewOrderData();

	public TemplateNewOrderData getData() {
		return data;
	}

	public void setData(TemplateNewOrderData data) {
		this.data = data;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

}
