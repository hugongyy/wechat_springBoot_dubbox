package com.chetong.ctwechat.entity.model;

import java.util.Map;

import com.chetong.aic.page.domain.PageList;
import com.chetong.ctwechat.entity.mapping.CtUser;
import com.chetong.ctwechat.entity.mapping.FmOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeChatResponseModel extends BasesModel {

	private CtUser ctUser;
	private FmOrder fmOrder;// 订单表
	private PageList<FmOrder> list;
	private Map<String, Integer> count;

	public CtUser getCtUser() {
		return ctUser;
	}

	public void setCtUser(CtUser ctUser) {
		this.ctUser = ctUser;
	}

	public FmOrder getFmOrder() {
		return fmOrder;
	}

	public void setFmOrder(FmOrder fmOrder) {
		this.fmOrder = fmOrder;
	}

	public PageList<FmOrder> getList() {
		return list;
	}

	public void setList(PageList<FmOrder> list) {
		this.list = list;
	}

	public Map<String, Integer> getCount() {
		return count;
	}

	public void setCount(Map<String, Integer> count) {
		this.count = count;
	}

}
