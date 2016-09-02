package com.chetong.ctwechat.entity.model;

import java.util.List;
import java.util.Map;

import com.chetong.ctwechat.entity.mapping.CtUser;
import com.chetong.ctwechat.entity.mapping.HyOrderVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeChatHyResponseModel extends BasesModel {

	private CtUser ctUser;
	private HyOrderVO hyOrder;// 订单表
	private List<Map<String, Object>> list;
	private Map<String, Integer> count;

	public CtUser getCtUser() {
		return ctUser;
	}

	public void setCtUser(CtUser ctUser) {
		this.ctUser = ctUser;
	}

	public HyOrderVO getHyOrder() {
		return hyOrder;
	}

	public void setHyOrder(HyOrderVO hyOrder) {
		this.hyOrder = hyOrder;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public Map<String, Integer> getCount() {
		return count;
	}

	public void setCount(Map<String, Integer> count) {
		this.count = count;
	}

}
