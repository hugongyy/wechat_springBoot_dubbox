package com.chetong.ctwechat.entity.notice;

public class TemplateOrderStatMsg extends TemplateMsg {
	private String template_id = "fzf0MoDggmyJLI-mVy3fGeb-OXxXTJHziodKBMMLmmc"; // 订单状态更新
	public TemplateOrderStatData data = new TemplateOrderStatData();

	public TemplateOrderStatData getData() {
		return data;
	}

	public void setData(TemplateOrderStatData data) {
		this.data = data;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
}
