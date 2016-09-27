package com.chetong.ctwechat.service;

import com.chetong.ctwechat.entity.model.QueryOrderBaseBean;
import com.chetong.ctwechat.entity.model.WeChatHyResponseModel;
import com.chetong.ctwechat.entity.model.WeChatResponseModel;

public interface WechatService {

	WeChatResponseModel cxOrdersStatisticsNum(String userId, String otherLike);

	WeChatResponseModel hyOrdersStatisticsNum(String userId, String otherLike);

	WeChatResponseModel cxOrdersSelect(String userId, String workType, String otherLike, String orderState, Integer page,
			Integer pageSize);

	WeChatHyResponseModel hyOrdersSelect(String userId, String workType, String otherLike, String orderState, Integer page,
			Integer pageSize);

	QueryOrderBaseBean getRelationOrders(Long userId, String caseNo, String token);

	WeChatResponseModel bindingopenId(String uname, String pwd, String openId);

	WeChatResponseModel unbindingOpenId(String uname, String pwd, String openId);

	String getOpenId(String code);

	WeChatResponseModel getUserInfoByOpenId(String openId);

	WeChatResponseModel getAccessToken();

	String receiveUserInfo(String userId);

}
