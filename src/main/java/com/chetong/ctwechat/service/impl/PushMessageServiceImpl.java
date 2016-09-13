package com.chetong.ctwechat.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.chetong.ctwechat.dao.CommExeSqlDAO;
import com.chetong.ctwechat.entity.mapping.CmsOutBox;
import com.chetong.ctwechat.entity.mapping.FmOrder;
import com.chetong.ctwechat.helper.wechat.TokenThread;
import com.chetong.ctwechat.service.PushMessageService;

@Service("pushMessageService")
public class PushMessageServiceImpl implements PushMessageService {
	private Log log = LogFactory.getLog(WechatServiceImpl.class);
	@Resource
	private CommExeSqlDAO commExeSqlDAO;

	@Value("${WE_CHAT_PUSH_SWITCH}")
	private String WE_CHAT_PUSH_SWITCH;
	
	@Value("${headerUrl}")
	private String headerUrl;
	
	private static List<String> spAreas = new ArrayList<String>();

	static {
		spAreas.add("440300"); // 深圳
		spAreas.add("330200"); // 宁波
		//spAreas.add("370200"); // 青岛
		spAreas.add("210200"); // 大连
	}

	@Override
	public Long savePushMsg4Wechat(Long userId, String content, String createBy) {
		
		CmsOutBox cob = new CmsOutBox();
		cob.setUserId(userId);
		cob.setMsgType("1");
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

	// 每小时轮询一次,将超时订单发给车童和机构负责人.
	@Scheduled(fixedRate = 60 * 60 * 1000)
	@Override
	public void autoSendOverTimeOrder2SellerAndOrg() {
		if (!"Y".equals(WE_CHAT_PUSH_SWITCH)) {
			return;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("spAreas", spAreas);
		List<FmOrder> orderList = commExeSqlDAO.queryForList("CT_WECHAT_INFO.autoSendOverTimeOrder2SellerAndOrg",
				param);
		String sellerWechatId = null;
		String orgWechatId = null;
		StringBuffer content = null;
		Long sellerUserId = null;
		Long orgUserId = null;
		String result = null;
		Date now = new Date();

		for (FmOrder order : orderList) {
			content = new StringBuffer();
			sellerUserId = order.getSellerUserId();
			orgUserId = order.getGroupUserId();
			sellerWechatId = order.getExt7();
			orgWechatId = order.getExt8();
			
			
			// 组合内容
			content.append("超时订单提醒\n\n超时订单 <a href='" + headerUrl + "wechat/#/order/info?no=")
			.append(order.getOrderNo())
			.append("&id=").append(order.getId())
			.append("&openid=").append(orgWechatId).append("'>")
			.append("\n订单号:").append(order.getOrderNo())
			.append("\n报案号:").append(order.getCaseNo())
			.append("\n车童:").append(order.getSellerUserName())
			.append("\n联系方式:").append(order.getSellerUserType())
			.append("\n委托方:").append(order.getBuyerUserName())
			.append("\n订单类型:").append(order.getOrderTypeLabel())
			.append("\n派单时间:").append(order.getSendTimeLabel())
			.append("</a>");	
			
			try {
				if (StringUtils.isNotEmpty(sellerWechatId)) {
					result = sendTextMessageToUser(content.toString(), sellerWechatId, TokenThread.access);
					CmsOutBox cob = new CmsOutBox();
					cob.setUserId(sellerUserId);
					cob.setMsgType("1");
					cob.setMsgTo(sellerWechatId);
					cob.setMsgContent(content.toString());
					cob.setSendFlag(result);
					cob.setSendTime(now);
					cob.setSendNum(1);
					cob.setCreateBy("0");
					cob.setCreateTime(now);

					commExeSqlDAO.insertVO("cms_out_box.insertNotNull", cob);

				}
			} catch (Exception e) {
				log.error(e);
			}
			try {				
				if (StringUtils.isNotEmpty(orgWechatId)) {
					result = sendTextMessageToUser(content.toString(), orgWechatId, TokenThread.access);
					CmsOutBox cob = new CmsOutBox();
					cob.setUserId(orgUserId);
					cob.setMsgType("1");
					cob.setMsgTo(orgWechatId);
					cob.setMsgContent(content.toString());
					cob.setSendFlag(result);
					cob.setSendTime(now);
					cob.setSendNum(1);
					cob.setCreateBy("0");
					cob.setCreateTime(now);

					commExeSqlDAO.insertVO("cms_out_box.insertNotNull", cob);
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	// 每10分钟轮询一次,自动将外发消息箱中的非成功的消息推送到微信.(最多三次)
	@Scheduled(fixedDelay = 10 * 60 * 1000)
	public void autoSendAllOutMsg() {
		if (!"Y".equals(WE_CHAT_PUSH_SWITCH)) {
			return;
		}
		String openid = null;
		String content = null;
		String result = null;
		Date now = new Date();
		CmsOutBox cob = new CmsOutBox();

		try {

			List<CmsOutBox> cobList = commExeSqlDAO.queryForList("CT_WECHAT_INFO.queryAllHaveNotSendOutMsg", cob);

			for (CmsOutBox o : cobList) {
				openid = o.getMsgTo();
				content = o.getMsgContent();
				result = sendTextMessageToUser(content, openid, TokenThread.access);
				if (!"0".equals(result)) { // 没有发送
					o.setMsgTo(openid);
					o.setSendFlag(result);
					o.setSendTime(now);
					o.setSendNum(o.getSendNum() + 1);

					commExeSqlDAO.insertVO("cms_out_box.updateByKeyNotNull", o);
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
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
