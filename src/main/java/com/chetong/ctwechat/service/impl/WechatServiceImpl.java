package com.chetong.ctwechat.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chetong.aic.page.domain.PageBounds;
import com.chetong.aic.page.domain.PageList;
import com.chetong.aic.util.PHPMd5;
import com.chetong.aic.util.StringUtil;
import com.chetong.ctwechat.dao.CommExeSqlDAO;
import com.chetong.ctwechat.entity.mapping.CtBindInfo;
import com.chetong.ctwechat.entity.mapping.CtGroup;
import com.chetong.ctwechat.entity.mapping.CtUser;
import com.chetong.ctwechat.entity.mapping.FmOrder;
import com.chetong.ctwechat.entity.model.OrderListModel;
import com.chetong.ctwechat.entity.model.QueryOrderBaseBean;
import com.chetong.ctwechat.entity.model.WeChatHyResponseModel;
import com.chetong.ctwechat.entity.model.WeChatResponseModel;
import com.chetong.ctwechat.helper.DateUtil;
import com.chetong.ctwechat.helper.wechat.TokenThread;
import com.chetong.ctwechat.helper.wechat.WeixinUtil;
import com.chetong.ctwechat.service.WechatService;

import net.sf.json.JSONObject;

@Service("wechatService")
public class WechatServiceImpl implements WechatService {

	private Log log = LogFactory.getLog(WechatServiceImpl.class);

	@Value("${headerUrl}")
	private String headerUrl;

	private static List<String> spAreas = new ArrayList<String>();

	static {
		spAreas.add("440300"); // 深圳
		spAreas.add("330200"); // 宁波
		// spAreas.add("370200"); // 青岛
		spAreas.add("210200"); // 大连
	}

	@Resource
	private CommExeSqlDAO commExeSqlDAO;
	
	@Value("${appid}")
	private String appid;
	@Value("${appsecret}")
	private String appsecret;

	/**
	 * 绑定
	 */
	@Override
	@Transactional
	public WeChatResponseModel bindingopenId(String uname, String pwd, String openId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();

		WeChatResponseModel response = new WeChatResponseModel();
		String password = "";

		if (uname == null || "".equals(uname) || pwd == null || pwd == null || "".equals(pwd) || openId == null
				|| "".equals(openId)) {
			response.setCode("fail");
			response.setMessage("绑定信息有空值");
			return response;
		}
		password = PHPMd5.getInstance().getStringMd5(pwd + "FanhuaChetongBangtai");

		paramMap.put("loginName", uname);
		List<CtUser> list = commExeSqlDAO.queryForList("ct_bind_info_MAPPER.getBindInfo", paramMap);
		paramMap.put("weichatid", openId);
		List<CtUser> uList = commExeSqlDAO.queryForList("ct_bind_info_MAPPER.getBindInfo", paramMap);

		if (list == null || list.size() == 0) {
			response.setCode("fail");
			response.setMessage("账号不存在,请重新输入");
			return response;
		} else if (!list.get(0).getLoginPwd().equals(password)) {
			response.setCode("fail");
			response.setMessage("密码错误");
			return response;
		} else {

			if (uList != null && uList.size() > 0) {
				response.setCode("fail");
				response.setMessage("该微信号已经被绑定，请勿重复绑定!");
				return response;
			}
			String accessToken = TokenThread.access;
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid="
					+ openId + "&lang=zh_CN";
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
			if (!StringUtil.isNullOrEmpty(jsonObject.get("errcode"))) {
				response.setCode("fail");
				response.setMessage("获取用户微信账号信息失败!");
				log.error(jsonObject);
				// return response;
			}

			CtBindInfo bindInfo = new CtBindInfo();
			bindInfo.setBindId(openId);
			bindInfo.setBindType(1);
			bindInfo.setUserId(list.get(0).getId());
			bindInfo.setNickname((String) jsonObject.get("nickname"));
			bindInfo.setHeadPic((String) jsonObject.get("headimgurl"));

			commExeSqlDAO.insertVO("ct_bind_info_MAPPER.insertSelective", bindInfo);

			
			response.setCtUser(list.get(0));
			response.setCode("success");
			response.setMessage("绑定成功");
			return response;
		}

	}

	/**
	 * 解除绑定
	 */
	@Override
	@Transactional
	public WeChatResponseModel unbindingOpenId(String uname, String pwd, String openId) {
		WeChatResponseModel response = new WeChatResponseModel();
		String password = "";

		if (uname == null || "".equals(uname) || pwd == null || pwd == null || "".equals(pwd)
				|| StringUtil.isNullOrEmpty(openId)) {
			response.setCode("fail");
			response.setMessage("绑定信息有空值");
			return response;
		}

		password = PHPMd5.getInstance().getStringMd5(pwd + "FanhuaChetongBangtai");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", uname);
		List<CtUser> list = commExeSqlDAO.queryForList("ct_bind_info_MAPPER.getBindInfo", paramMap);

		if (null == list) {
			log.error("微信解绑失败：无此账号信息");
			response.setCode("fail");
			response.setMessage("解绑失败");
			return response;
		} else if (!password.equals(list.get(0).getLoginPwd())) {
			response.setCode("fail");
			response.setMessage("密码错误");
			return response;
		} else {
			paramMap.put("openId", openId);
			paramMap.put("userId", list.get(0).getId());
			int num = commExeSqlDAO.deleteVO("ct_bind_info_MAPPER.unbindWechat", paramMap);
			if (num == 0) {
				response.setCode("fail");
				response.setMessage("解绑失败");
				return response;
			}
			response.setCode("success");
			response.setMessage("解绑成功");
			return response;
		}

	}

	/**
	 * TODO 车险的订单数量.
	 */
	@Override
	public WeChatResponseModel cxOrdersStatisticsNum(String userId, String otherLike) {
		List<OrderListModel> mapOrder = null;
		WeChatResponseModel response = new WeChatResponseModel();
		Map<String, Object> param = new HashMap<String, Object>();
		String userIds = null;
		String subUserIds = null;
		String[] userIdArr = null;
		String provCode = null;
		String cityCode = null;
		CtGroup group = null;
		String userTypeLabel = null;

		if (userId == null || "".equals(userId)) {
			response.setCode("fail");
			response.setMessage("必须传入的数据有空值");
			return response;
		}
		// 转码
		// if (otherLike != null && !"".equals(otherLike)) {
		// otherLike = URLDecoder.decode(otherLike, "utf-8");
		// }

		CtUser u = (CtUser) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.queryUserAllInfoByUserId", userId);
		provCode = u.getMailProvCode();
		cityCode = u.getMailCityCode();

		if ("0".equals(u.getUserType()) && "1".equals(u.getIsMgr())) {
			userTypeLabel = "PM";
			// 项目经理,使用所属的机构的userId
			group = (CtGroup) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.queryGroupByPorjectManage", userId);
			provCode = group.getProvCode();
			cityCode = group.getCityCode();
			userId = group.getUserId().toString();

			param.put("userId", userId); // 使用项目经理所属的机构的userId查询订单.
			param.put("otherLike", otherLike);
			if (spAreas.contains(cityCode)) {
				param.put("cityCode", cityCode);
			} else {
				param.put("provCode", provCode);
				param.put("spAreas", spAreas);
			}
			mapOrder = commExeSqlDAO.queryForList("CT_WECHAT_INFO.countOrder4Org", param);
		} else if ("1".equals(u.getUserType())) {
			userTypeLabel = "Org";
			// 机构
			if ("1".equals(u.getIsFanhua())) {
				// 车险机构,子账户(ct_user)
				userId = u.getPid() != null ? u.getPid().toString() : userId; // 子账户的转为主账户

				param.put("userId", userId);
				param.put("otherLike", otherLike);
				if (spAreas.contains(cityCode)) {
					param.put("cityCode", cityCode);
				} else {
					param.put("provCode", provCode);
					param.put("spAreas", spAreas);
				}

				if (u.getPid() != null) { // 子账户的权限,及地区和报案号规则
					if (u.getMailAddress() != null) {
						param.put("auditAreas", u.getMailAddress().split(","));
					}
					if (u.getBankNo() != null) {
						param.put("likeCaseNos", u.getBankNo().split(","));
					}
				}

				mapOrder = commExeSqlDAO.queryForList("CT_WECHAT_INFO.countOrder4Org", param);

			} else {
				userTypeLabel = "Company";
				// 保险公司,分公司(ct_user_level)
				userId = u.getPid() != null ? u.getPid().toString() : userId; // 子账户的转为主账户
				subUserIds = (String) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.querySubUserIdByCompanyUserId",
						userId);

				if (subUserIds != null) {
					userIds = subUserIds + "," + userId;
				} else {
					userIds = userId;
				}
				userIdArr = userIds.split(","); // 保险公司及下属的分公司的userId.
				param.put("userIdArr", userIdArr);
				param.put("otherLike", otherLike);

				if (u.getPid() != null) { // 子账户的权限,及地区和报案号规则
					if (u.getMailAddress() != null) {
						param.put("auditAreas", u.getMailAddress().split(","));
					}
					if (u.getBankNo() != null) {
						param.put("likeCaseNos", u.getBankNo().split(","));
					}
				}
				mapOrder = commExeSqlDAO.queryForList("CT_WECHAT_INFO.countOrder4Company", param);
			}
		} else if ("2".equals(u.getUserType())) {
			userTypeLabel = "Group";
			// 团队,下属车童(ct_person_group)
			userId = u.getPid() != null ? u.getPid().toString() : userId; // 子账户的转为主账户
			subUserIds = (String) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.querySubUserIdByGroupUserId", userId);

			if (subUserIds != null) {
				userIds = subUserIds + "," + userId;
			} else {
				userIds = userId;
			}
			userIdArr = userIds.split(","); // 团队长及下属车童的userId.
			param.put("userIdArr", userIdArr);
			param.put("otherLike", otherLike);
			mapOrder = commExeSqlDAO.queryForList("CT_WECHAT_INFO.countOrder4Seller", param);

		} else if ("0".equals(u.getUserType()) && !"1".equals(u.getIsMgr())) {
			userTypeLabel = "Seller";
			// 普通车童
			userIdArr = userId.split(","); // 车童的userId.
			param.put("userIdArr", userIdArr);
			param.put("otherLike", otherLike);
			mapOrder = commExeSqlDAO.queryForList("CT_WECHAT_INFO.countOrder4Seller", param);
		}

		if (mapOrder == null || mapOrder.size() <= 0) {
			response.setCode("fail");
			response.setMessage("没有数据");
			return response;
		} else {

			Map<String, Integer> map = new HashMap<String, Integer>();
			response.setCode("success");
			for (int i = 0; i < mapOrder.size(); i++) {
				map.put(mapOrder.get(i).getState(), mapOrder.get(i).getSumCount());
			}
			response.setCount(map);
			u.setDepartment(userTypeLabel);
			response.setCtUser(u);
			response.setMessage("统计车险各个状态订单数成功");
			return response;
		}

	}

	/**
	 * TODO 货运险的订单数量.
	 */
	@Override
	public WeChatResponseModel hyOrdersStatisticsNum(String userId, String otherLike) {
		List<OrderListModel> mapOrder = null;
		WeChatResponseModel response = new WeChatResponseModel();
		Map<String, Object> param = new HashMap<String, Object>();
		String userIds = null;
		String subUserIds = null;
		String[] userIdArr = null;
		String provCode = null;
		String cityCode = null;
		CtGroup group = null;
		String userTypeLabel = null;
		if (userId == null || "".equals(userId)) {
			response.setCode("fail");
			response.setMessage("必须传入的数据有空值");
			return response;
		}
		// 转码
		// if (otherLike != null && !"".equals(otherLike)) {
		// otherLike = URLDecoder.decode(otherLike, "utf-8");
		// }

		CtUser u = (CtUser) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.queryUserAllInfoByUserId", userId);
		provCode = u.getMailProvCode();
		cityCode = u.getMailCityCode();

		if ("0".equals(u.getUserType()) && "1".equals(u.getIsMgr())) {
			userTypeLabel = "PM";
			// 项目经理,使用所属的机构的userId
			group = (CtGroup) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.queryGroupByPorjectManage", userId);
			provCode = group.getProvCode();
			cityCode = group.getCityCode();
			userId = group.getUserId().toString();

			param.put("userId", userId); // 使用项目经理所属的机构的userId查询订单.
			param.put("otherLike", otherLike);
			if (spAreas.contains(cityCode)) {
				param.put("cityCode", cityCode);
			} else {
				param.put("provCode", provCode);
				param.put("spAreas", spAreas);
			}

			mapOrder = commExeSqlDAO.queryForList("CT_WECHAT_INFO.countHyOrder4Org", param);
		} else if ("1".equals(u.getUserType())) {
			userTypeLabel = "Org";
			// 机构
			if ("1".equals(u.getIsFanhua())) {
				// 车险机构,子账户(ct_user)
				userId = u.getPid() != null ? u.getPid().toString() : userId; // 子账户的转为主账户

				param.put("userId", userId);
				param.put("otherLike", otherLike);
				if (spAreas.contains(cityCode)) {
					param.put("cityCode", cityCode);
				} else {
					param.put("provCode", provCode);
					param.put("spAreas", spAreas);
				}
				mapOrder = commExeSqlDAO.queryForList("CT_WECHAT_INFO.countHyOrder4Org", param);

			} else {
				userTypeLabel = "Company";
				// 保险公司,分公司(ct_user_level)
				userId = u.getPid() != null ? u.getPid().toString() : userId; // 子账户的转为主账户
				subUserIds = (String) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.querySubUserIdByCompanyUserId",
						userId);

				if (subUserIds != null) {
					userIds = subUserIds + "," + userId;
				} else {
					userIds = userId;
				}

				// 因为货运险的send_user_id可能存在子账户.
				subUserIds = (String) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.querySubUserIdByOrgUserId", userId);
				if (subUserIds != null) {
					userIds = userIds + "," + subUserIds;
				}
				userIdArr = userIds.split(","); // 保险公司及下属的分公司的userId.
				param.put("userIdArr", userIdArr);
				param.put("otherLike", otherLike);
				mapOrder = commExeSqlDAO.queryForList("CT_WECHAT_INFO.countHyOrder4Company", param);
			}
		} else if ("2".equals(u.getUserType())) {
			userTypeLabel = "Group";
			// 团队,下属车童(ct_person_group)
			userId = u.getPid() != null ? u.getPid().toString() : userId; // 子账户的转为主账户
			subUserIds = (String) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.querySubUserIdByGroupUserId", userId);

			if (subUserIds != null) {
				userIds = subUserIds + "," + userId;
			} else {
				userIds = userId;
			}
			userIdArr = userIds.split(","); // 团队长及下属车童的userId.
			param.put("userIdArr", userIdArr);
			param.put("otherLike", otherLike);
			mapOrder = commExeSqlDAO.queryForList("CT_WECHAT_INFO.countHyOrder4Seller", param);

		} else if ("0".equals(u.getUserType()) && !"1".equals(u.getIsMgr())) {
			userTypeLabel = "Seller";
			// 普通车童
			userIdArr = userId.split(","); // 车童的userId.
			param.put("userIdArr", userIdArr);
			param.put("otherLike", otherLike);
			mapOrder = commExeSqlDAO.queryForList("CT_WECHAT_INFO.countHyOrder4Seller", param);
		}

		if (mapOrder == null || mapOrder.size() <= 0) {
			response.setCode("fail");
			response.setMessage("没有数据");
			return response;
		} else {

			Map<String, Integer> map = new HashMap<String, Integer>();
			response.setCode("success");
			for (int i = 0; i < mapOrder.size(); i++) {
				map.put(mapOrder.get(i).getState(), mapOrder.get(i).getSumCount());
			}
			response.setCount(map);
			u.setDepartment(userTypeLabel);
			response.setCtUser(u);
			response.setMessage("统计货运险各个状态订单数成功");
			return response;
		}
	}

	/**
	 * TODO 车险订单查询（list）
	 */
	@Override
	public WeChatResponseModel cxOrdersSelect(String userId, String workType, String otherLike, String dealStat,
			Integer page, Integer pageSize) {
		WeChatResponseModel response = new WeChatResponseModel();
		Map<String, Object> param = new HashMap<String, Object>();
		PageList<FmOrder> list = null;
		String userIds = null;
		String subUserIds = null;
		String[] userIdArr = null;
		String provCode = null;
		String cityCode = null;
		CtGroup group = null;
		String userTypeLabel = null;
		Date sendTime = null;
		Date now = new Date();
		String diffTimeLabel = null;
		if (userId == null || "".equals(userId)) {
			response.setCode("fail");
			response.setMessage("必须传入的数据有空值");
			return response;
		}
		// 转码
		// if (otherLike != null && !"".equals(otherLike)) {
		// otherLike = URLDecoder.decode(otherLike, "utf-8");
		// }

		CtUser u = (CtUser) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.queryUserAllInfoByUserId", userId);
		provCode = u.getMailProvCode();
		cityCode = u.getMailCityCode();

		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(page);
		pageBounds.setLimit(pageSize);

		// Integer startRecord = (page - 1) * pageSize;

		if ("0".equals(u.getUserType()) && "1".equals(u.getIsMgr())) {
			userTypeLabel = "PM";
			// 项目经理,使用所属的机构的userId
			group = (CtGroup) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.queryGroupByPorjectManage", userId);
			provCode = group.getProvCode();
			cityCode = group.getCityCode();
			userId = group.getUserId().toString();

			if (spAreas.contains(cityCode)) {
				param.put("cityCode", cityCode);
			} else {
				param.put("provCode", provCode);
				param.put("spAreas", spAreas);
			}
			param.put("userId", userId); // 使用项目经理所属的机构的userId查询订单.
			param.put("otherLike", otherLike);
			param.put("dealStat", dealStat);
			param.put("workType", workType);

			// 分页查询
			list = commExeSqlDAO.queryForPage("CT_WECHAT_INFO.queryOrderList4Org", param, pageBounds);

		} else if ("1".equals(u.getUserType())) {
			// 机构
			if ("1".equals(u.getIsFanhua())) {
				userTypeLabel = "Org";
				// 车险机构,子账户(ct_user)
				userId = u.getPid() != null ? u.getPid().toString() : userId; // 子账户的转为主账户

				param.put("userId", userId);
				param.put("otherLike", otherLike);
				param.put("dealStat", dealStat);
				param.put("workType", workType);

				if (spAreas.contains(cityCode)) {
					param.put("cityCode", cityCode);
				} else {
					param.put("provCode", provCode);
					param.put("spAreas", spAreas);
				}
				if (u.getPid() != null) { // 子账户的权限,及地区和报案号规则
					if (u.getMailAddress() != null) {
						param.put("auditAreas", u.getMailAddress().split(","));
					}
					if (u.getBankNo() != null) {
						param.put("likeCaseNos", u.getBankNo().split(","));
					}
				}
				// 分页查询
				list = commExeSqlDAO.queryForPage("CT_WECHAT_INFO.queryOrderList4Org", param, pageBounds);

			} else {
				userTypeLabel = "Company";
				// 保险公司,分公司(ct_user_level)
				userId = u.getPid() != null ? u.getPid().toString() : userId; // 子账户的转为主账户
				subUserIds = (String) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.querySubUserIdByCompanyUserId",
						userId);

				if (subUserIds != null) {
					userIds = subUserIds + "," + userId;
				} else {
					userIds = userId;
				}
				userIdArr = userIds.split(","); // 保险公司及下属的分公司的userId.
				param.put("userIdArr", userIdArr);
				param.put("otherLike", otherLike);
				param.put("dealStat", dealStat);
//				param.put("workType", workType); // 保险公司不分本异地订单.

				if (u.getPid() != null) { // 子账户的权限,及地区和报案号规则
					if (u.getMailAddress() != null) {
						param.put("auditAreas", u.getMailAddress().split(","));
					}
					if (u.getBankNo() != null) {
						param.put("likeCaseNos", u.getBankNo().split(","));
					}
				}
				// 分页查询
				list = commExeSqlDAO.queryForPage("CT_WECHAT_INFO.queryOrderList4Company", param, pageBounds);

			}
		} else if ("2".equals(u.getUserType())) {
			userTypeLabel = "Group";
			// 团队,下属车童(ct_person_group)
			userId = u.getPid() != null ? u.getPid().toString() : userId; // 子账户的转为主账户
			subUserIds = (String) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.querySubUserIdByGroupUserId", userId);

			if (subUserIds != null) {
				userIds = subUserIds + "," + userId;
			} else {
				userIds = userId;
			}
			userIdArr = userIds.split(","); // 团队长及下属车童的userId.
			param.put("userIdArr", userIdArr);
			param.put("otherLike", otherLike);
			param.put("dealStat", dealStat);
//			param.put("workType", workType); // 团队不分本异地订单.
			// param.put("startRecord", startRecord);
			// param.put("pageSize", pageSize);
			// 查询
			list = commExeSqlDAO.queryForPage("CT_WECHAT_INFO.queryOrderList4Seller", param, pageBounds);

		} else if ("0".equals(u.getUserType()) && !"1".equals(u.getIsMgr())) {
			userTypeLabel = "Seller";
			// 普通车童
			userIdArr = userId.split(","); // 车童的userId.
			param.put("userIdArr", userIdArr);
			param.put("otherLike", otherLike);
			param.put("dealStat", dealStat);
//			param.put("workType", workType); // 车童不分本异地订单.

			// 查询
			list = commExeSqlDAO.queryForPage("CT_WECHAT_INFO.queryOrderList4Seller", param, pageBounds);
		}

		// 处理数据.
		for (FmOrder order : list) {
			sendTime = order.getSendTime();
			diffTimeLabel = DateUtil.diffTimeToString(now, sendTime);
			order.setCtArriveInfo(diffTimeLabel);
		}

		response.setList(list);
		response.setTotalCount(list.getPaginator().getTotalCount());
		u.setDepartment(userTypeLabel);
		response.setCtUser(u);
		response.setPage(page);
		response.setCode("success");
		response.setMessage("查询车险订单列表成功");

		return response;
	}

	/**
	 * TODO 货运险订单列表
	 */
	@Override
	public WeChatHyResponseModel hyOrdersSelect(String userId, String workType, String otherLike, String dealStat,
			Integer page, Integer pageSize) {
		WeChatHyResponseModel response = new WeChatHyResponseModel();
		Map<String, Object> param = new HashMap<String, Object>();
		PageList<Map<String, Object>> list = null;
		String userIds = null;
		String subUserIds = null;
		String[] userIdArr = null;
		String provCode = null;
		String cityCode = null;
		String transportType = null;
		Date sendTime = null;
		Date now = new Date();
		String diffTimeLabel = null;
		CtGroup group = null;
		String userTypeLabel = null;

		if (userId == null || "".equals(userId)) {
			response.setCode("fail");
			response.setMessage("必须传入的数据有空值");
			return response;
		}
		// 转码
		// if (otherLike != null && !"".equals(otherLike)) {
		// otherLike = URLDecoder.decode(otherLike, "utf-8");
		// }

		CtUser u = (CtUser) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.queryUserAllInfoByUserId", userId);
		provCode = u.getMailProvCode();
		cityCode = u.getMailCityCode();

		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(page);
		pageBounds.setLimit(pageSize);

		// Integer startRecord = (page - 1) * pageSize;

		if ("0".equals(u.getUserType()) && "1".equals(u.getIsMgr())) {
			userTypeLabel = "PM";
			// 项目经理,使用所属的机构的userId
			group = (CtGroup) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.queryGroupByPorjectManage", userId);
			provCode = group.getProvCode();
			cityCode = group.getCityCode();
			userId = group.getUserId().toString();

			if (spAreas.contains(cityCode)) {
				param.put("cityCode", cityCode);
			} else {
				param.put("provCode", provCode);
				param.put("spAreas", spAreas);
			}
			param.put("userId", userId); // 使用项目经理所属的机构的userId查询订单.
			param.put("otherLike", otherLike);
			param.put("dealStat", dealStat);
			param.put("workType", workType);
			// param.put("startRecord", startRecord);
			// param.put("pageSize", pageSize);

			// 分页查询
			list = commExeSqlDAO.queryForPage("CT_WECHAT_INFO.queryHyOrderList4Org", param, pageBounds);

		} else if ("1".equals(u.getUserType())) {
			userTypeLabel = "Org";
			// 机构
			if ("1".equals(u.getIsFanhua())) {
				// 车险机构,子账户(ct_user)
				userId = u.getPid() != null ? u.getPid().toString() : userId; // 子账户的转为主账户

				param.put("userId", userId);
				param.put("otherLike", otherLike);
				param.put("dealStat", dealStat);
				param.put("workType", workType);

				if (spAreas.contains(cityCode)) {
					param.put("cityCode", cityCode);
				} else {
					param.put("provCode", provCode);
					param.put("cityCode", cityCode);
				}
				if (u.getPid() != null) { // 子账户的权限,及地区和报案号规则
					if (u.getMailAddress() != null) {
						param.put("auditAreas", u.getMailAddress().split(","));
					}
					if (u.getBankNo() != null) {
						param.put("likeCaseNos", u.getBankNo().split(","));
					}
				}
				// param.put("startRecord", startRecord);
				// param.put("pageSize", pageSize);
				// 分页查询
				list = commExeSqlDAO.queryForPage("CT_WECHAT_INFO.queryHyOrderList4Org", param, pageBounds);

			} else {
				userTypeLabel = "Company";
				// 保险公司,分公司(ct_user_level)
				userId = u.getPid() != null ? u.getPid().toString() : userId; // 子账户的转为主账户
				subUserIds = (String) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.querySubUserIdByCompanyUserId",
						userId);

				if (subUserIds != null) {
					userIds = subUserIds + "," + userId;
				} else {
					userIds = userId;
				}
				// 因为货运险的send_user_id可能存在子账户.
				subUserIds = (String) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.querySubUserIdByOrgUserId", userId);
				if (subUserIds != null) {
					userIds = userIds + "," + subUserIds;
				}

				userIdArr = userIds.split(","); // 保险公司及下属的分公司的userId.
				param.put("userIdArr", userIdArr);
				param.put("otherLike", otherLike);
				param.put("dealStat", dealStat);
				param.put("workType", workType);

				if (u.getPid() != null) { // 子账户的权限,及地区和报案号规则
					if (u.getMailAddress() != null) {
						param.put("auditAreas", u.getMailAddress().split(","));
					}
					if (u.getBankNo() != null) {
						param.put("likeCaseNos", u.getBankNo().split(","));
					}
				}
				// param.put("startRecord", startRecord);
				// param.put("pageSize", pageSize);
				// 分页查询
				list = commExeSqlDAO.queryForPage("CT_WECHAT_INFO.queryHyOrderList4Company", param, pageBounds);

			}
		} else if ("2".equals(u.getUserType())) {
			userTypeLabel = "Group";
			// 团队,下属车童(ct_person_group)
			userId = u.getPid() != null ? u.getPid().toString() : userId; // 子账户的转为主账户
			subUserIds = (String) commExeSqlDAO.queryForObject("CT_WECHAT_INFO.querySubUserIdByGroupUserId", userId);

			if (subUserIds != null) {
				userIds = subUserIds + "," + userId;
			} else {
				userIds = userId;
			}
			userIdArr = userIds.split(","); // 团队长及下属车童的userId.
			param.put("userIdArr", userIdArr);
			param.put("otherLike", otherLike);
			param.put("dealStat", dealStat);
			param.put("workType", workType);
			// param.put("startRecord", startRecord);
			// param.put("pageSize", pageSize);
			// 查询
			list = commExeSqlDAO.queryForPage("CT_WECHAT_INFO.queryHyOrderList4Seller", param, pageBounds);

		} else if ("0".equals(u.getUserType()) && !"1".equals(u.getIsMgr())) {
			userTypeLabel = "Seller";
			// 普通车童
			userIdArr = userId.split(","); // 车童的userId.
			param.put("userIdArr", userIdArr);
			param.put("otherLike", otherLike);
			param.put("dealStat", dealStat);
			param.put("workType", workType);
			// param.put("startRecord", startRecord);
			// param.put("pageSize", pageSize);
			// 查询
			list = commExeSqlDAO.queryForPage("CT_WECHAT_INFO.queryHyOrderList4Seller", param, pageBounds);

		}

		String dealStatLabel = null;
		String dstat = null;
		// 处理数据.
		for (Map<String, Object> hyOrder : list) {
			transportType = (String) hyOrder.get("transportType");
			if (transportType != null) {
				transportType = transportType.replaceAll("1", "铁路").replaceAll("2", "公路").replaceAll("3", "航空")
						.replaceAll("4", "水路");
				hyOrder.put("transportType", transportType);
			}
			sendTime = (Date) hyOrder.get("sendTime");
			diffTimeLabel = DateUtil.diffTimeToString(now, sendTime);
			hyOrder.put("diffTimeLabel", diffTimeLabel);
			
			dealStatLabel = null;
			dstat = (String) hyOrder.get("dealStat");
			if (dstat != null) {
				if ("00".equals(dstat)) {
					dealStatLabel = "派单中";
				} else if ("01".equals(dstat)) {
					dealStatLabel = "无响应";
				} else if ("02".equals(dstat)) {
					dealStatLabel = "注销";
				} else if ("03".equals(dstat)) {
					dealStatLabel = "撤单";
				} else if ("04".equals(dstat)) {
					dealStatLabel = "作业中";
				} else if ("05".equals(dstat)) {
					dealStatLabel = "待初审";
				} else if ("06".equals(dstat)) {
					dealStatLabel = "初审退回";
				} else if ("07".equals(dstat)) {
					dealStatLabel = "待审核";
				} else if ("08".equals(dstat)) {
					dealStatLabel = "已退回";
				} else if ("09".equals(dstat)) {
					dealStatLabel = "审核通过";
				} else if ("10".equals(dstat)) {
					dealStatLabel = "订单删除";
				} else {
					dealStatLabel = "未知";
				}
				hyOrder.put("dealStatLabel", dealStatLabel);
			}
		}

		response.setList(list);
		response.setTotalCount(list.getPaginator().getTotalCount());
		u.setDepartment(userTypeLabel);
		response.setCtUser(u);
		response.setPage(page);
		response.setCode("success");
		response.setMessage("查询货运险订单列表成功");

		return response;
	}
	
	@Override
	public WeChatResponseModel getAccessToken() {
		WeChatResponseModel response = new WeChatResponseModel();
		if (TokenThread.access == null || "".equals(TokenThread.access)) {
			response.setCode("fail");
			response.setMessage("参数为空,获取失败");
			return response;
		} else {
			response.setCode("success");
			response.setMessage(TokenThread.access);
			return response;
		}
	}
	
	@Override
	public String getOpenId(String code) {
		String openIdInfo = getHttps(appid, appsecret, code);
		return openIdInfo;
	}
	
	@Override
	public WeChatResponseModel getUserInfoByOpenId(String openId) {
		WeChatResponseModel response = new WeChatResponseModel();
		try {
			if (openId == null || "".equals(openId)) {
				response.setCode("fail");
				response.setMessage("openid传入值为空");
				return response;
			} else {
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("weichatid", openId);
				CtUser cu = (CtUser) commExeSqlDAO.queryForObject("ct_bind_info_MAPPER.getBindInfo", paraMap);
				if (cu == null) {
					response.setCode("fail");
					response.setMessage("账号不存在,请重新输入");
					return response;
				} else {
					cu.setLoginPwd(null);
					response.setCode("success");
					response.setCtUser(cu);
					return response;
				}
			}

		} catch (Exception e) {
			response.setCode("fail");
			response.setMessage("查询失败");
			return response;
		}
	}

	/**
	 * 获取关联订单,排除了医健险的订单.
	 */
	@Override
	public QueryOrderBaseBean getRelationOrders(Long userId, String caseNo, String token) {
		// String headerUrl = "http://dev.chetong.net/app/#";
		QueryOrderBaseBean response = new QueryOrderBaseBean();
		String buyerUserName = null;
		String sendTime = null;
		BigDecimal alowMoney = null;

		if (userId == null) {
			response.setCode("fail");
			response.setMessage("用户ID不能为空.");
			return response;
		}
		if (StringUtils.isEmpty(caseNo)) {
			response.setCode("fail");
			response.setMessage("委托人尚未提交案件信息，请稍后查看。");
			return response;
		}

		FmOrder param = new FmOrder();
		param.setCaseNo(caseNo);
		param.setSellerUserId(userId);

		List<FmOrder> orderList = commExeSqlDAO.queryForList("CT_WECHAT_INFO.getRelationOrders", param);
		if (orderList == null || orderList.size() == 0) {
			response.setCode("fail");
			response.setMessage("哇咔咔，异常了!");
			return response;
		}
		String systemSource = ""; // 订单任务来源 1=永诚系统
		for (FmOrder o : orderList) {
			Map<String, String> ycOrderRelMap = commExeSqlDAO.queryForObject("CT_WECHAT_INFO.getYcOrderRelation",
					o.getOrderNo());
			if (ycOrderRelMap != null && ycOrderRelMap.get("id") != null) {
				systemSource = "1";
				String url = null;
				if ("0".equals(o.getOrderType())) {
					buyerUserName = o.getBuyerUserName();
					sendTime = DateUtil.getDateTime("yyyy-MM-dd HH:mm", o.getSendTime());
					alowMoney = o.getAlowMoney().setScale(2, BigDecimal.ROUND_HALF_UP);
					// 调整到H5的URL.
					url = headerUrl + "apph5/#/yc/survey?userId=" + o.getSellerUserId() + "&orderNo=" + o.getOrderNo()
							+ "&reportNo=" + o.getCaseNo() + "&workId=" + ycOrderRelMap.get("workId");

				} else if ("1".equals(o.getOrderType()) || "2".equals(o.getOrderType())) {
					url = headerUrl + "apph5/#/yc/damage?userId=" + o.getSellerUserId() + "&orderNo=" + o.getOrderNo()
							+ "&reportNo=" + o.getCaseNo() + "&workId=" + ycOrderRelMap.get("workId");
				} else if ("3".equals(o.getOrderType())) {
					url = headerUrl + "apph5/#/yc/other?userId=" + o.getSellerUserId() + "&orderNo=" + o.getOrderNo()
							+ "&reportNo=" + o.getCaseNo() + "&workId=" + ycOrderRelMap.get("workId");
				}
				o.setOrderUrl(url);
				continue;
			}
			if ("0".equals(o.getOrderType())) {
				buyerUserName = o.getBuyerUserName();
				sendTime = DateUtil.getDateTime("yyyy-MM-dd HH:mm", o.getSendTime());
				alowMoney = o.getAlowMoney().setScale(2, BigDecimal.ROUND_HALF_UP);
				// 调整到H5的URL.
				o.setOrderUrl(headerUrl + "apph5/#/survey?orderCode=" + o.getOrderNo() + "&userId="
						+ o.getSellerUserId() + "&token=" + token);
			} else if ("1".equals(o.getOrderType()) || "2".equals(o.getOrderType())) {
				o.setOrderUrl(headerUrl + "apph5/#/damage?orderCode=" + o.getOrderNo() + "&userId="
						+ o.getSellerUserId() + "&token=" + token);
			} else if ("3".equals(o.getOrderType())) {
				o.setOrderUrl(headerUrl + "apph5/#/other?orderCode=" + o.getOrderNo() + "&userId=" + o.getSellerUserId()
						+ "&token=" + token);
			}
		}

		if (buyerUserName == null) {
			FmOrder o = orderList.get(0);
			buyerUserName = o.getBuyerUserName();
			sendTime = DateUtil.getDateTime("yyyy-MM-dd HH:mm", o.getSendTime());
			alowMoney = o.getAlowMoney().setScale(2, BigDecimal.ROUND_HALF_UP);
		}

		response.setSystemSource(systemSource);
		response.setBuyerUserName(buyerUserName);
		response.setSendTime(sendTime);
		response.setAlowMoney(alowMoney);
		response.setList(orderList);
		response.setCode("success");
		response.setMessage("查询成功");

		return response;
	}

	public String getHttps(String appid, String secret, String code) {
		String bytestr = "";
		HttpClient httpClient = new HttpClient();
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code=" + code
				+ "&grant_type=authorization_code";
		GetMethod method = new GetMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		try {
			httpClient.executeMethod(method);
			byte[] bytes = method.getResponseBody();
			bytestr = new String(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return bytestr;
	}

	@Override
	public String receiveUserInfo(String userId) {
		StringBuffer sb = new StringBuffer();
		String result = null;
		CtBindInfo param = new CtBindInfo();
		if (userId != null) {
			param.setUserId(Long.parseLong(userId));	
		}
		List<CtBindInfo> cbiList = commExeSqlDAO.queryForList("ct_bind_info_MAPPER.getNullCtBindInfo", param);
		for(CtBindInfo cbi : cbiList) {
			String openId = cbi.getBindId();
			String accessToken = TokenThread.access;
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid="
					+ openId + "&lang=zh_CN";
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
			if (!StringUtil.isNullOrEmpty(jsonObject.get("errcode"))) {
				sb.append(",").append("errcode=").append(jsonObject.get("errcode"));
				continue;
			}
			CtBindInfo bindInfo = new CtBindInfo();
			bindInfo.setId(cbi.getId());
			bindInfo.setNickname((String)jsonObject.get("nickname"));
			bindInfo.setHeadPic((String) jsonObject.get("headimgurl"));
			
			commExeSqlDAO.updateVO("ct_bind_info_MAPPER.updateByPrimaryKeySelective", bindInfo);
			sb.append(",").append("nickname="+bindInfo.getNickname());
			
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}	

}
