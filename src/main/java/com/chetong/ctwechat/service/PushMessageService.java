package com.chetong.ctwechat.service;

public interface PushMessageService {
	Long savePushMsg4Wechat(Long userId, String content, String createBy);
	
	void autoSendOverTimeOrder2SellerAndOrg();

	String testSendOutMsg(String content, String userId);
}
