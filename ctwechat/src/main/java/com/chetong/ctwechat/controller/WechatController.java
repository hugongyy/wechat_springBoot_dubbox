package com.chetong.ctwechat.controller;

import java.net.URLDecoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.chetong.aic.entity.ResultVO;
import com.chetong.ctwechat.entity.model.BasesModel;
import com.chetong.ctwechat.entity.model.QueryOrderBaseBean;
import com.chetong.ctwechat.entity.model.WeChatHyResponseModel;
import com.chetong.ctwechat.entity.model.WeChatResponseModel;
import com.chetong.ctwechat.helper.wechat.TokenThread;
import com.chetong.ctwechat.service.PushMessageService;
import com.chetong.ctwechat.service.WechatService;
import com.chetong.ctwechat.util.token.OperaterUtils;

@RestController
@RequestMapping("/ctWeChat")
public class WechatController {
	
	private Log log = LogFactory.getLog(WechatController.class);

	@Autowired
	private WechatService wechatService;
	@Autowired
	private PushMessageService pushMessageService;
	@Autowired
	private TokenThread tokenThread;

	@RequestMapping(value = "/bindingopenId", method = RequestMethod.POST)
	@ResponseBody
	public WeChatResponseModel bindingopenId(@RequestBody ModelMap map) {
		WeChatResponseModel response = new WeChatResponseModel();
		String uname = (String) map.get("uname");
		String pwd = (String) map.get("pwd");
		String openId = (String) map.get("openId");
		try {
			response = wechatService.bindingopenId(uname, pwd, openId);
		} catch (Exception e) {
			log.error("绑定openId失败",e);			
			response.setCode("fail");
			response.setMessage("绑定openId失败");
		}

		return response;
	}

	/**
	 * @Description: 解绑openid
	 * @return
	 * @return WeChatResponseModel
	 * @author zhouchushu
	 * @date 2015年11月4日 下午2:24:24
	 */
	@RequestMapping(value = "/unbindingOpenId", method = RequestMethod.POST)
	@ResponseBody
	public WeChatResponseModel unbindingOpenId(@RequestBody ModelMap map) {
		WeChatResponseModel response = new WeChatResponseModel();
		String uname = (String) map.get("uname");
		String pwd = (String) map.get("pwd");
		String openId = (String) map.get("openId");

		try {
			response = wechatService.unbindingOpenId(uname, pwd, openId);
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("解除绑定openId失败");
		}

		return response;
	}

	@RequestMapping(value = "/cxOrdersStatisticsNum", method = RequestMethod.POST)
	@ResponseBody
	public WeChatResponseModel cxOrdersStatisticsNum(@RequestBody ModelMap map) {
		WeChatResponseModel response = new WeChatResponseModel();
		String userId = (String) map.get("userId");
		String otherLike = (String) map.get("otherLike");
		String orderState = (String) map.get("orderState");

		if (StringUtils.isEmpty(userId)) {
			response.setCode("fail");
			response.setMessage("必要参数为空");
			return response;
		}
		
		Long operaterUserId = OperaterUtils.getOperaterUserId();
		if(!userId.equals(operaterUserId.toString())){
			response.setCode("fail");
			response.setMessage("当前请求用户与所查用户数据不一致！");
			return response;
		}

		otherLike = "".equals(otherLike) ? null : otherLike;

		try {
			response = wechatService.cxOrdersStatisticsNum(userId, null, orderState);
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("统计车险订单状态失败");
		}

		return response;
	}

	@RequestMapping(value = "/hyOrdersStatisticsNum", method = RequestMethod.POST)
	@ResponseBody
	public WeChatResponseModel hyOrdersStatisticsNum(@RequestBody ModelMap map) {
		WeChatResponseModel response = new WeChatResponseModel();
		String userId = (String) map.get("userId");
		String otherLike = (String) map.get("otherLike");

		if (StringUtils.isEmpty(userId)) {
			response.setCode("fail");
			response.setMessage("必要参数为空");
			return response;
		}
		
		Long operaterUserId = OperaterUtils.getOperaterUserId();
		if(!userId.equals(operaterUserId.toString())){
			response.setCode("fail");
			response.setMessage("当前请求用户与所查用户数据不一致！");
			return response;
		}


		otherLike = "".equals(otherLike) ? null : otherLike;
		try {
			response = wechatService.hyOrdersStatisticsNum(userId, null);
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("统计货运险订单状态失败");
		}
		return response;
	}

	/**
	 * 车险订单查询（list） workType:0-本地,1-异地作业地,2-异地委托地
	 */
	@RequestMapping(value = "/cxOrdersSelect", method = RequestMethod.POST)
	@ResponseBody
	public WeChatResponseModel cxOrdersSelect(@RequestBody ModelMap map) {
		WeChatResponseModel response = new WeChatResponseModel();
		String userId = (String) map.get("userId");
		String workType = (String) map.get("workType");
		String otherLike = (String) map.get("otherLike");
		String orderState = (String) map.get("orderState");
		Integer page = (Integer) map.get("page");
		Integer pageSize = (Integer) map.get("pageSize");

		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(workType) || StringUtils.isEmpty(orderState)) {
			response.setCode("fail");
			response.setMessage("必要参数为空");
			return response;
		}
		
		Long operaterUserId = OperaterUtils.getOperaterUserId();
		if(!userId.equals(operaterUserId.toString())){
			response.setCode("fail");
			response.setMessage("当前请求用户与所查用户数据不一致！");
			return response;
		}


		if (page == null || pageSize == null) {
			page = 1;
			pageSize = 10;
		}
		otherLike = "".equals(otherLike) ? null : otherLike;

		try {
			response = wechatService.cxOrdersSelect(userId, workType, null, orderState, page, pageSize);
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("车险订单查询失败");
		}
		return response;
	}
	
	
	 
		
	
	/**
	 * 车险订单查询（list）
	 */
	@RequestMapping(value = "/cxOrdersList", method = RequestMethod.POST)
	@ResponseBody
	public WeChatResponseModel cxOrdersList(@RequestBody ModelMap map) {
		WeChatResponseModel response = new WeChatResponseModel();
		String userId = (String) map.get("userId");
		String otherLike = (String) map.get("otherLike");
		Integer page = (Integer) map.get("page");
		Integer pageSize = (Integer) map.get("pageSize");

		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(otherLike)) {
			response.setCode("fail");
			response.setMessage("必要参数为空");
			return response;
		}
		
		Long operaterUserId = OperaterUtils.getOperaterUserId();
		if(!userId.equals(operaterUserId.toString())){
			response.setCode("fail");
			response.setMessage("当前请求用户与所查用户数据不一致！");
			return response;
		}

		if (page == null || pageSize == null) {
			page = 1;
			pageSize = 10;
		}
		otherLike = "".equals(otherLike) ? null : otherLike;
		try {
			otherLike = URLDecoder.decode(otherLike, "UTF-8");	
		} catch (Exception e) {
			log.error("URLDecoder.decode(otherLike, \"UTF-8\") error, otherLike="+otherLike,e);
		}

		try {
			response = wechatService.cxOrdersSelect(userId, null, otherLike, null, page, pageSize);
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("车险订单查询失败");
		}
		return response;
	}

	/**
	 * 货运险列表(list)
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/hyOrdersSelect", method = RequestMethod.POST)
	@ResponseBody
	public WeChatHyResponseModel hyOrdersSelect(@RequestBody ModelMap map) {
		WeChatHyResponseModel response = new WeChatHyResponseModel();
		String userId = (String) map.get("userId");
		String workType = "0";
		String otherLike = (String) map.get("otherLike");
		String orderState = (String) map.get("orderState");
		Integer page = (Integer) map.get("page");
		Integer pageSize = (Integer) map.get("pageSize");

		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(workType) || StringUtils.isEmpty(orderState)) {
			response.setCode("fail");
			response.setMessage("必要参数为空");
			return response;
		}
		
		Long operaterUserId = OperaterUtils.getOperaterUserId();
		if(!userId.equals(operaterUserId.toString())){
			response.setCode("fail");
			response.setMessage("当前请求用户与所查用户数据不一致！");
			return response;
		}

		if (page == null || pageSize == null) {
			page = 1;
			pageSize = 10;
		}
		otherLike = "".equals(otherLike) ? null : otherLike;

		try {
			response = wechatService.hyOrdersSelect(userId, workType, null, orderState, page, pageSize);
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("货运险订单查询失败");
		}
		return response;
	}
	
	/**
	 * 货运险列表(list)
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/hyOrdersList", method = RequestMethod.POST)
	@ResponseBody
	public WeChatHyResponseModel hyOrdersList(@RequestBody ModelMap map) {
		WeChatHyResponseModel response = new WeChatHyResponseModel();
		String userId = (String) map.get("userId");
		String workType = "0";
		String otherLike = (String) map.get("otherLike");
		Integer page = (Integer) map.get("page");
		Integer pageSize = (Integer) map.get("pageSize");

		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(workType)) {
			response.setCode("fail");
			response.setMessage("必要参数为空");
			return response;
		}
		
		Long operaterUserId = OperaterUtils.getOperaterUserId();
		if(!userId.equals(operaterUserId.toString())){
			response.setCode("fail");
			response.setMessage("当前请求用户与所查用户数据不一致！");
			return response;
		}

		if (page == null || pageSize == null) {
			page = 1;
			pageSize = 10;
		}
		otherLike = "".equals(otherLike) ? null : otherLike;
		
		try {
			otherLike = URLDecoder.decode(otherLike, "UTF-8");	
		} catch (Exception e) {
			log.error("URLDecoder.decode(otherLike, \"UTF-8\") error, otherLike="+otherLike,e);
		}

		try {
			response = wechatService.hyOrdersSelect(userId, workType, otherLike, null, page, pageSize);
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("货运险订单查询失败");
		}
		return response;
	}
	
	/**
	 * 获取 access_token
	 */
	@RequestMapping(value = "/getAccessToken", method = RequestMethod.GET)
	@ResponseBody
	public ResultVO<String> getAccessToken() {
		return this.wechatService.getAccessToken();
	};
	
	/**
	 * @return 获取OPENID
	 */
	@RequestMapping(value = "/getOpenId/{code}", method = RequestMethod.GET)
	@ResponseBody
	public String getOpenId(@PathVariable("code") String code) {
		return this.wechatService.getOpenId(code);
	}
	
	/**
	 * @param openId
	 * @return 根据openId 获取用户
	 */
	@RequestMapping(value = "/getUserInfoByOpenId/{openId}", method = RequestMethod.GET)
	@ResponseBody
	public WeChatResponseModel getUserInfoByOpenId(@PathVariable("openId") String openId) {
		return this.wechatService.getUserInfoByOpenId(openId);
	};
	
	/**
	 * 获取关联订单.
	 * @param map
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/getRelationOrders", method = RequestMethod.POST)
	@ResponseBody
	public QueryOrderBaseBean getRelationOrders(@RequestBody ModelMap map, @RequestHeader("token") String token) {
		QueryOrderBaseBean response = new QueryOrderBaseBean();
		String userId = (String) map.get("userId");
		String caseNo = (String) map.get("caseNo");
		if (userId == null || StringUtils.isEmpty(caseNo)) {
			response.setCode("fail");
			response.setMessage("必要参数为空");
			return response;
		}
		
		Long operaterUserId = OperaterUtils.getOperaterUserId();
		if(!userId.equals(operaterUserId.toString())){
			response.setCode("fail");
			response.setMessage("当前请求用户与所查用户数据不一致！");
			return response;
		}

		try {
			response = wechatService.getRelationOrders(Long.parseLong(userId), caseNo, token);
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("获取关联订单列表失败");
		}
		return response;
	}
	
	@RequestMapping(value = "/savePushMsg4Wechat", method = RequestMethod.POST)
	@ResponseBody
	public Long savePushMsg4Wechat(@RequestBody ModelMap map) {
		String userId = (String) map.get("userId");
		String orderNo = (String) map.get("orderNo");
		String orderType = (String) map.get("orderType");
		String content = (String) map.get("content");
		String createBy = (String) map.get("createBy");

		return pushMessageService.savePushMsg4Wechat(Long.parseLong(userId), orderNo, orderType, content, createBy);
	}
	
	@RequestMapping(value = "/autoSendOverTimeOrder2SellerAndOrg", method = RequestMethod.GET)
	@ResponseBody
	public void autoSendOverTimeOrder2SellerAndOrg() {
		pushMessageService.autoSendOverTimeOrder2SellerAndOrg();
	}


	@RequestMapping(value = "/receiveAccessToken", method = RequestMethod.GET)
	@ResponseBody
	public BasesModel receiveAccessToken() {
		BasesModel response = new BasesModel();
		tokenThread.getAccess();
		response.setCode("success");
		response.setMessage("access_token = "+tokenThread.access);
		return response;
	}
	
	
	
	@RequestMapping(value = "/testSendOutMsg", method = RequestMethod.POST)
	@ResponseBody
	public String testSendOutMsg(@RequestBody ModelMap map) {
		String userId = (String) map.get("userId");
		String content = (String) map.get("content");
		
		return pushMessageService.testSendOutMsg(content, userId);
	}
	
	@RequestMapping(value = "/sendPushMsg4Wechat", method = RequestMethod.POST)
	@ResponseBody
	public Long sendPushMsg4Wechat(@RequestBody ModelMap map) {
		String userId = (String) map.get("userId");		
		String orderNo = (String) map.get("orderNo");
		String orderType = (String) map.get("orderType");
		String content = (String) map.get("content");
		String createBy = (String) map.get("createBy");
		
		return pushMessageService.sendPushMsg4Wechat(Long.parseLong(userId), orderNo, orderType, content, createBy);
	}
	
	@RequestMapping(value = "/receiveUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public String receiveUserInfo(@RequestBody ModelMap map) {
		String userId = (String) map.get("userId");
		return wechatService.receiveUserInfo(userId);
	}
}
