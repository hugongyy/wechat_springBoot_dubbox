package com.chetong.ctwechat.service;

import java.util.List;

import com.chetong.aic.entity.ResultVO;
import com.chetong.ctwechat.entity.model.QueryOrderBaseBean;
import com.chetong.ctwechat.entity.model.WeChatHyResponseModel;
import com.chetong.ctwechat.entity.model.WeChatResponseModel;

public interface WechatService {

	WeChatResponseModel cxOrdersStatisticsNum(String userId, String otherLike, String dealStat);

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

	ResultVO<String> getAccessToken();

	String receiveUserInfo(String userId);

	List<String> querySpAreas();

}
