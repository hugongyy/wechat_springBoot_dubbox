package com.chetong.ctwechat.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.chetong.ctwechat.controller.InterfaceController;
import com.chetong.ctwechat.dao.CommExeSqlDAO;
import com.chetong.ctwechat.entity.mapping.CmsOutBox;
import com.chetong.ctwechat.entity.mapping.CtBindInfo;
import com.chetong.ctwechat.entity.mapping.FmOrder;
import com.chetong.ctwechat.entity.notice.MsgResult;
import com.chetong.ctwechat.entity.notice.TemplateMsg;
import com.chetong.ctwechat.entity.notice.TemplateNewOrderMsg;
import com.chetong.ctwechat.entity.notice.TemplateOrderStatMsg;
import com.chetong.ctwechat.helper.wechat.TokenThread;
import com.chetong.ctwechat.service.PushMessageService;
import com.chetong.ctwechat.util.token.DESUtils;
import com.google.gson.Gson;

@Service("pushMessageService")
public class PushMessageServiceImpl implements PushMessageService {
	private Log log = LogFactory.getLog(WechatServiceImpl.class);
	@Resource
	private CommExeSqlDAO commExeSqlDAO;

	@Value("${WE_CHAT_PUSH_SWITCH}")
	private String WE_CHAT_PUSH_SWITCH;

	@Value("${headerUrl}")
	private String headerUrl;

	@Value("${appid}")
	private String appid;
	@Value("${appsecret}")
	private String appsecret;
	
	@Value("${newOrderTemplateId}")
	private String newOrderTemplateId;
	@Value("${changeStatTemplateId}")
	private String changeStatTemplateId;
	

	@Override
	public Long savePushMsg4Wechat(Long userId, String orderNo, String orderType, String content, String createBy) {

		CmsOutBox cob = new CmsOutBox();
		cob.setUserId(userId);
		cob.setMsgType("1");
		cob.setOrderNo(orderNo);
		cob.setOrderType(orderType);
		cob.setMsgContent(content);
		cob.setCreateBy(createBy);
		cob.setCreateTime(new Date());

		int i = commExeSqlDAO.insertVO("cms_out_box.insertNotNull", cob);
		if (i > 0) {
			return cob.getId();
		} else {
			return 0L;
		}
	}

	// public static void main(String[] args) {
	// String first = null;
	// String mi = null;
	// String money = null;
	// String remark = null;
	// String content =
	// "您有一个查勘订单。距离你4.38公里，报价98.100000元。委托方:永安江西，订单地点:江西省景德镇市乐平市乐平市。报案人:(朱爱水，13900000000)。请及时抢单。";
	// // String content =
	// // "您的团员叶松剑有一个查勘订单。委托方:永安江西，订单地点:江西省景德镇市乐平市乐平市。报案人:(朱爱水，13900000000)。";
	// int jh = content.indexOf("。");
	// int mi_start = content.indexOf("距离你");
	// int mi_end = content.indexOf("公里");
	// int money_start = content.indexOf("报价");
	// int money_end = content.indexOf("元");
	//
	// first = content.substring(0, jh + 1);
	// if (mi_start != -1) {
	// mi = content.substring(mi_start + 3, mi_end);
	// }
	// if (money_start != -1) {
	// money = content.substring(money_start + 2, money_end);
	// remark = "请及时抢单。";
	// }
	//
	// System.out.println(first);
	// System.out.println(mi);
	// System.out.println(money);
	// System.out.println(remark);
	// }

	// TODO 直接发送的消息,实验
	@Override
	public Long sendPushMsg4Wechat(Long userId, String orderNo, String orderType, String content, String createBy) {
		StringBuffer sb = new StringBuffer();
		String result = null;
		String openid = null;
		String first = null;
		String mi = null;
		String money = null;
		String remark = null;
		Date now = new Date();

		FmOrder fmOrder = new FmOrder();
		fmOrder.setOrderNo(orderNo);
		fmOrder = commExeSqlDAO.queryForObject("CT_WECHAT_INFO.queryFmOrder", fmOrder);

		int jh = content.indexOf("。");
		int mi_start = content.indexOf("距离你");
		int mi_end = content.indexOf("公里");
		int money_start = content.indexOf("报价");
		int money_end = content.indexOf("元");

		first = content.substring(0, jh + 1);
		if (mi_start != -1) {
			mi = content.substring(mi_start + 3, mi_end);
		}
		if (money_start != -1) {
			money = content.substring(money_start + 2, money_end);
			remark = "请及时抢单。";
		}

		// String url = headerUrl + "wechat/#/order/info?no=" + orderNo +
		// "&type=" + orderType + "&caseNo"
		// + fmOrder.getCaseNo();

		String url = fixUrl8FmOrder(fmOrder, openid);

		TemplateNewOrderMsg msg = new TemplateNewOrderMsg();
		// msg.setUrl(url); // TODO 这里应该是抢单的url.
		msg.setTemplate_id(newOrderTemplateId);
		msg.data.setFirst(first);
		msg.data.setKeyword1(fmOrder.getOrderNo());
		msg.data.setKeyword2(fmOrder.getOrderTypeLabel()); // 订单类型
		if (money != null) {
			msg.data.setKeyword3(money + "元/" + mi + "公里");
		}
		msg.data.setKeyword4(fmOrder.getSendTimeLabel());
		msg.data.setKeyword5(fmOrder.getLinkMan() + "," + fmOrder.getLinkTel());
		msg.data.setRemark(remark);

		CtBindInfo param = new CtBindInfo();
		param.setUserId(userId);
		List<CtBindInfo> cbiList = commExeSqlDAO.queryForList("ct_bind_info_MAPPER.queryCtBindInfo", param);
		for (CtBindInfo cbi : cbiList) {
			openid = cbi.getBindId();
			result = sendModuleMessageToUser(msg, openid, TokenThread.access);
			sb.append(",").append(result);
		}

		CmsOutBox cob = new CmsOutBox();
		cob.setUserId(userId);
		cob.setMsgType("1");
		cob.setMsgTo(openid);
		cob.setOrderNo(orderNo);
		cob.setOrderType(orderType);
		cob.setMsgContent(content);

		Gson gson = new Gson();
		MsgResult msgResult = gson.fromJson(result, MsgResult.class);

		if ("0".equals(msgResult.getErrcode())) {
			cob.setSendFlag("1");
		} else { // 没有发送成功
			cob.setSendFlag(result);
		}
		cob.setSendNum(1);
		cob.setSendTime(now);
		cob.setCreateBy(createBy);
		cob.setCreateTime(now);

		int i = commExeSqlDAO.insertVO("cms_out_box.insertNotNull", cob);
		if (i > 0) {
			return cob.getId();
		} else {
			return 0L;
		}
	}

	// @Override
	public Long sendPushMsg4Wechat_2(Long userId, String orderNo, String orderType, String content, String createBy) {
		StringBuffer sb = new StringBuffer();
		String result = null;
		String openid = null;
		Date now = new Date();
		CtBindInfo param = new CtBindInfo();
		param.setUserId(userId);
		List<CtBindInfo> cbiList = commExeSqlDAO.queryForList("ct_bind_info_MAPPER.queryCtBindInfo", param);
		for (CtBindInfo cbi : cbiList) {
			openid = cbi.getBindId();
			result = sendTextMessageToUser(content, openid, TokenThread.access);
			sb.append(",").append(result);
		}

		CmsOutBox cob = new CmsOutBox();
		cob.setUserId(userId);
		cob.setMsgType("1");
		cob.setMsgTo(openid);
		cob.setOrderNo(orderNo);
		cob.setOrderType(orderType);
		cob.setMsgContent(content);
		cob.setSendFlag(result);
		cob.setSendNum(1);
		cob.setSendTime(now);
		cob.setCreateBy(createBy);
		cob.setCreateTime(now);

		int i = commExeSqlDAO.insertVO("cms_out_box.insertNotNull", cob);
		if (i > 0) {
			return cob.getId();
		} else {
			return 0L;
		}
	}

	// 每小时轮询一次,将超时订单发给车童和机构负责人.
	@Scheduled(fixedRate = 60 * 60 * 1000)
	@Override
	public void autoSendOverTimeOrder2SellerAndOrg() {
		if (!"Y".equals(WE_CHAT_PUSH_SWITCH)) {
			return;
		}

		String sellerWechatId = null;
		String orgWechatId = null;
		String url = null;
		StringBuffer content = null;
		Long sellerUserId = null;
		Long orgUserId = null;
		String result = null;
		Date now = new Date();
		CmsOutBox cob = null;
		// 应该把超时订单,分别查询车童和机构负责人的list.
		Map<String, Object> param = new HashMap<String, Object>();

		List<FmOrder> orderList2Seller = commExeSqlDAO.queryForList("CT_WECHAT_INFO.autoSendOverTimeOrder2Seller",
				param);

		List<FmOrder> orderList2Org = commExeSqlDAO.queryForList("CT_WECHAT_INFO.autoSendOverTimeOrder2Org", param);

		for (FmOrder order : orderList2Seller) {
			content = new StringBuffer();
			sellerUserId = order.getSellerUserId();
			sellerWechatId = order.getExt7();

			// 组合内容
			content.append("超时订单提醒\n\n即将超时").append("\n订单号:").append(order.getOrderNo()).append("\n报案号:")
					.append(order.getCaseNo()).append("\n车牌号:").append(order.getCarNo()).append("\n车童:")
					.append(order.getSellerUserName()).append("\n联系方式:").append(order.getSellerUserType())
					.append("\n委托方:").append(order.getBuyerUserName()).append("\n订单类型:")
					.append(order.getOrderTypeLabel()).append("\n派单时间:").append(order.getSendTimeLabel());

			// result = {"errcode":0,"errmsg":"ok"};
			try {
				if (StringUtils.isNotEmpty(sellerWechatId)) {
					// 点击查看
					url = fixUrl8FmOrder(order, sellerWechatId);
					// content = content.append("\n<a href='" + url +
					// "'>点击查看</a>");

					result = sendTextMessageToUser(content.toString(), sellerWechatId, TokenThread.access);
					cob = new CmsOutBox();
					cob.setUserId(sellerUserId);
					cob.setMsgType("1");
					cob.setMsgTo(sellerWechatId);
					cob.setOrderNo(order.getOrderNo());
					cob.setOrderType(order.getOrderType());
					cob.setMsgContent(content.toString());
					cob.setSendTime(now);
					cob.setSendNum(3);
					cob.setCreateBy("0");
					cob.setCreateTime(now);
					if ("{\"errcode\":0,\"errmsg\":\"ok\"}".equals(result)) {
						cob.setSendFlag("1");
					} else {
						cob.setSendFlag(result);
					}

					commExeSqlDAO.insertVO("cms_out_box.insertNotNull", cob);
				}
			} catch (Exception e) {
				log.error(e);
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.error(e);
			}
		}

		for (FmOrder order2 : orderList2Org) {
			content = new StringBuffer();
			orgUserId = order2.getGroupUserId();
			orgWechatId = order2.getExt8();

			// 组合内容
			content.append("超时订单提醒\n\n即将超时").append("\n订单号:").append(order2.getOrderNo()).append("\n报案号:")
					.append(order2.getCaseNo()).append("\n车牌号:").append(order2.getCarNo()).append("\n车童:")
					.append(order2.getSellerUserName()).append("\n联系方式:").append(order2.getSellerUserType())
					.append("\n委托方:").append(order2.getBuyerUserName()).append("\n订单类型:")
					.append(order2.getOrderTypeLabel()).append("\n派单时间:").append(order2.getSendTimeLabel());

			url = fixUrl8FmOrder(order2, orgWechatId);
			// content = content.append("\n<a href='" + url + "'>点击查看</a>");

			try {
				if (StringUtils.isNotEmpty(orgWechatId)) {
					result = sendTextMessageToUser(content.toString(), orgWechatId, TokenThread.access);
					cob = new CmsOutBox();
					cob.setUserId(orgUserId);
					cob.setMsgType("1");
					cob.setMsgTo(orgWechatId);
					cob.setOrderNo(order2.getOrderNo());
					cob.setOrderType(order2.getOrderType());
					cob.setMsgContent(content.toString());
					cob.setSendTime(now);
					cob.setSendNum(3);
					cob.setCreateBy("0");
					cob.setCreateTime(now);

					if ("{\"errcode\":0,\"errmsg\":\"ok\"}".equals(result)) {
						cob.setSendFlag("1");
					} else {
						cob.setSendFlag(result);
					}

					commExeSqlDAO.insertVO("cms_out_box.insertNotNull", cob);
				}
			} catch (Exception e) {
				log.error(e);
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.error(e);
			}
		}
	}

	// TODO 新的订单状态变更模板消息.
	@Scheduled(fixedDelay = 10 * 60 * 1000)
	public void autoSendAllOutMsg() {
		if (!"Y".equals(WE_CHAT_PUSH_SWITCH)) {
			return;
		}
		String url = null;
		String openid = null;
		String content = null;
		Long userId = null;
		String orderNo = null;
		String orderType = null;
		String result = null;
		Date now = new Date();
		CmsOutBox cob = new CmsOutBox();
		FmOrder fmOrder = null;

		List<CmsOutBox> cobList = commExeSqlDAO.queryForList("CT_WECHAT_INFO.queryAllHaveNotSendOutMsg", cob);

		for (CmsOutBox o : cobList) {
			openid = o.getMsgTo();
			content = o.getMsgContent();
			userId = o.getUserId();
			orderNo = o.getOrderNo();
			orderType = o.getOrderType();
			try {
				// 组合文字成:"点击查看"
				if (orderNo != null) {
					fmOrder = new FmOrder();
					fmOrder.setOrderNo(orderNo);
					fmOrder = commExeSqlDAO.queryForObject("CT_WECHAT_INFO.queryFmOrder", fmOrder);
					if (fmOrder != null) {
						// url = headerUrl + "wechat/#/order/info?no=" + orderNo
						// + "&type=" + orderType + "&caseNo=" +
						// fmOrder.getCaseNo();
						url = fixUrl8FmOrder(fmOrder, openid);
					}
				}

				TemplateOrderStatMsg msg = new TemplateOrderStatMsg();
				msg.setTemplate_id(changeStatTemplateId);
				msg.data.setFirst("车险订单状态更新");
				msg.data.setOrderSn(orderNo);
				msg.data.setOrderStatus(fmOrder.getDealStatLabel());
				msg.data.setRemark(content);
				if (url != null) {
					msg.setUrl(url);
				}

				result = sendModuleMessageToUser(msg, openid, TokenThread.access);

				o.setMsgTo(openid);
				o.setSendTime(now);
				o.setSendNum(o.getSendNum() + 1);

				Gson gson = new Gson();
				MsgResult msgResult = gson.fromJson(result, MsgResult.class);

				if ("0".equals(msgResult.getErrcode())) {
					o.setSendFlag("1");
				} else { // 没有发送成功
					o.setSendFlag(result);
				}
				commExeSqlDAO.updateVO("cms_out_box.updateByKeyNotNull", o);
				Thread.sleep(5000);
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	// 每10分钟轮询一次,自动将外发消息箱中的非成功的消息推送到微信.(最多三次)
	// @Scheduled(fixedDelay = 10 * 60 * 1000)
	public void autoSendAllOutMsg_2() {
		if (!"Y".equals(WE_CHAT_PUSH_SWITCH)) {
			return;
		}
		String url = null;
		String openid = null;
		String content = null;
		Long userId = null;
		String orderNo = null;
		String orderType = null;
		String result = null;
		Date now = new Date();
		CmsOutBox cob = new CmsOutBox();
		FmOrder fmOrder = null;

		List<CmsOutBox> cobList = commExeSqlDAO.queryForList("CT_WECHAT_INFO.queryAllHaveNotSendOutMsg", cob);

		for (CmsOutBox o : cobList) {
			openid = o.getMsgTo();
			content = o.getMsgContent();
			userId = o.getUserId();
			orderNo = o.getOrderNo();
			orderType = o.getOrderType();
			try {
				// 组合文字成:"点击查看"
				if (orderNo != null) {
					fmOrder = new FmOrder();
					fmOrder.setOrderNo(orderNo);
					fmOrder = commExeSqlDAO.queryForObject("CT_WECHAT_INFO.queryFmOrder", fmOrder);
					if (fmOrder != null) {
						// url = headerUrl + "wechat/#/order/info?no=" + orderNo
						// + "&type=" + orderType + "&caseNo="
						// + fmOrder.getCaseNo();
						url = fixUrl8FmOrder(fmOrder, openid);
					}

					// content = content + "\n<a href='" + url + "'>点击查看</a>";
				}
				result = sendTextMessageToUser(content, openid, TokenThread.access);

				o.setMsgTo(openid);
				o.setSendTime(now);
				o.setSendNum(o.getSendNum() + 1);

				if ("{\"errcode\":0,\"errmsg\":\"ok\"}".equals(result)) {
					o.setSendFlag("1");
				} else { // 没有发送成功
					o.setSendFlag(result);
				}
				commExeSqlDAO.updateVO("cms_out_box.updateByKeyNotNull", o);
				Thread.sleep(5000);
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	// 创建订单详情的url.
	@Override
	public String fixUrl8FmOrder(FmOrder fmOrder, String openid) {
		String headUrl = headerUrl.replaceAll("http://", "").replaceAll("/", "");
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid
				+ "&redirect_uri=http%3A%2F%2F" + headUrl
				+ "%2Fctweb%2Fview%2Ftowechat.html&response_type=code&scope=snsapi_base&state=orderinfo&no="
				+ fmOrder.getOrderNo() + "&type=" + fmOrder.getOrderType() + "&caseNo=" + fmOrder.getCaseNo()
				+ "#wechat_redirect";
		// return url;
		return null;
	}

	@Override
	public String testSendOutMsg(String content, String userId) {
		StringBuffer sb = new StringBuffer();
		String result = null;
		CtBindInfo param = new CtBindInfo();
		param.setUserId(Long.parseLong(userId));
		List<CtBindInfo> cbiList = commExeSqlDAO.queryForList("ct_bind_info_MAPPER.queryCtBindInfo", param);
		for (CtBindInfo cbi : cbiList) {
			result = sendTextMessageToUser(content, cbi.getBindId(), TokenThread.access);
			sb.append(",").append(result);
		}
		return sb.toString();
	}

	public String sendTextMessageToUser(String content, String openid, String accessToken) {
		String json = "";
		StringBuffer sb = new StringBuffer("\"");
		sb.append(openid);
		sb.append("\"");
		json = "{\"touser\":" + sb.toString() + ",\"msgtype\": \"text\", \"text\": {\"content\": \"" + content + "\"}}";
		// 获取请求路径
		String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken;
		try {
			return connectWeiXinInterface(action, json);
		} catch (Exception e) {
			log.error(e);
			return e.getMessage();
		}
	}

	// TODO 模板消息
	public String sendModuleMessageToUser(TemplateMsg msg, String openid, String accessToken) {
		String json = "";

		msg.setTouser(openid);
		Gson gson = new Gson();
		json = gson.toJson(msg);

		// 获取请求路径
		String action = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;
		try {
			return connectWeiXinInterface(action, json);
		} catch (Exception e) {
			log.error(e);
			return e.getMessage();
		}
	}

	public String connectWeiXinInterface(String action, String json) throws Exception {
		URL url = null;
		try {
			url = new URL(action);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

			log.debug("微信推送信息:" + new Date().toString() + "  信息：" + json);

			if ("Y".equals(WE_CHAT_PUSH_SWITCH)) {
				http.connect();
				OutputStream os = http.getOutputStream();
				os.write(json.getBytes("UTF-8"));// 传入参数
				InputStream is = http.getInputStream();
				int size = is.available();
				byte[] jsonBytes = new byte[size];
				is.read(jsonBytes);
				String result = new String(jsonBytes, "UTF-8");
				log.info("请求返回结果:" + result);
				os.flush();
				os.close();
				return result;
			} else {
				return "0";
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
