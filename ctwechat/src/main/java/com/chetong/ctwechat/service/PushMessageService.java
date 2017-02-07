package com.chetong.ctwechat.service;

import com.chetong.ctwechat.entity.mapping.FmOrder;

public interface PushMessageService {
	/**
	 * 保存微信推送内容,以后轮询发送
	 * @param userId
	 * @param orderNo
	 * @param orderType
	 * @param content
	 * @param createBy
	 * @return
	 */
	Long savePushMsg4Wechat(Long userId, String orderNo, String orderType, String content, String createBy);
	
	/**
	 * 立即发送微信推送,需要修改content
	 * @param userId
	 * @param orderNo
	 * @param orderType
	 * @param content
	 * @param createBy
	 * @return
	 */
	Long sendPushMsg4Wechat(Long userId, String orderNo, String orderType, String content, String createBy);
	
	/**
	 * 自动发送超时订单信息.
	 */
	void autoSendOverTimeOrder2SellerAndOrg();

	String testSendOutMsg(String content, String userId);
	
	String fixUrl8FmOrder(FmOrder fmOrder, String openid);
}
