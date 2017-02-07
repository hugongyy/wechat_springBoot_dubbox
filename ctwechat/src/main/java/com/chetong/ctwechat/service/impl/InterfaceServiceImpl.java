package com.chetong.ctwechat.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.chetong.ctwechat.dao.CommExeSqlDAO;
import com.chetong.ctwechat.entity.mapping.CtBindInfo;
import com.chetong.ctwechat.entity.mapping.CtPersonStat;
import com.chetong.ctwechat.entity.mapping.CtUser;
import com.chetong.ctwechat.entity.mapping.FmOrder;
import com.chetong.ctwechat.entity.mapping.SysSms;
import com.chetong.ctwechat.entity.sms.SendSmsResp;
import com.chetong.ctwechat.helper.xml.WechatXmlUtil;
import com.chetong.ctwechat.service.InterfaceService;
import com.chetong.ctwechat.service.PushMessageService;

@Service("interfaceService")
public class InterfaceServiceImpl implements InterfaceService {
	private Log log = LogFactory.getLog(WechatServiceImpl.class);
	
	@Value("${headerUrl}")
	private String headerUrl;
	@Value("${appid}")
	private String appid;
	@Autowired
	private CommExeSqlDAO commExeSqlDAO;

	@Autowired
	private PushMessageService pushMessageService;

	@Override
	public String locationSave(ModelMap map) {
		String ToUserName = (String) map.get("ToUserName"); // 开发者微信号
		String FromUserName = (String) map.get("FromUserName"); // 发送方帐号（一个OpenID）
		String CreateTime = (String) map.get("CreateTime"); // 消息创建时间 （整型）
		String MsgType = (String) map.get("MsgType"); // 消息类型，event
		String Event = (String) map.get("Event"); // 事件类型，LOCATION
		String Latitude = (String) map.get("Latitude"); // 地理位置纬度
		String Longitude = (String) map.get("Longitude"); // 地理位置经度
		String Precision = (String) map.get("Precision"); // 地理位置精度

		ToUserName = WechatXmlUtil.killCDATAsign(ToUserName);
		FromUserName = WechatXmlUtil.killCDATAsign(FromUserName);
		MsgType = WechatXmlUtil.killCDATAsign(MsgType);
		Event = WechatXmlUtil.killCDATAsign(Event);
		Latitude = WechatXmlUtil.killCDATAsign(Latitude);
		Longitude = WechatXmlUtil.killCDATAsign(Longitude);
		Precision = WechatXmlUtil.killCDATAsign(Precision);

		// TODO 看日志,暂时不要返回.
		return null;
	}


	@Override
	public String textSave(ModelMap map) {
		String result = null;

		String ToUserName = (String) map.get("ToUserName"); // 开发者微信号
		String FromUserName = (String) map.get("FromUserName"); // 发送方帐号（一个OpenID）
		String CreateTime = (String) map.get("CreateTime"); // 消息创建时间 （整型）
		String MsgType = (String) map.get("MsgType"); // 消息类型，image
		String Content = (String) map.get("Content"); // 文本消息内容
		String MsgId = (String) map.get("MsgId"); // 消息id，64位整型

		ToUserName = WechatXmlUtil.killCDATAsign(ToUserName);
		FromUserName = WechatXmlUtil.killCDATAsign(FromUserName);
		MsgType = WechatXmlUtil.killCDATAsign(MsgType);
		Content = WechatXmlUtil.killCDATAsign(Content);

		if (Content != null) {
			result = fixNews8Param(ToUserName, FromUserName, Content);
			
			/*if ("订单".equals(Content)) {
				result = fixNews8Text(ToUserName, FromUserName, Content);
			} else if ("排行榜".equals(Content)) {
				result = fixNews8Text(ToUserName, FromUserName, Content);
			} else if (Content.matches("A[0-9]{10}")) {
				// 订单号
				result = sendPushMsg8OrderNo(ToUserName, FromUserName, Content);
			} else if (Content.matches("[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}")) {
				// 车牌号码
				result = sendPushMsg8CarNo(ToUserName, FromUserName, Content);
			} else {
				result = fixText8Other(ToUserName, FromUserName, Content);
			}*/
		}
		return result;
	}

	

	private String sendPushMsg8OrderNo(String toUserName, String fromUserName, String content) {
		List<FmOrder> orderList = queryCommonOrder(toUserName, fromUserName, content);
		if (orderList == null || orderList.size() == 0) {
			return null;
		}
		String result = fixNews4OrderList(toUserName, fromUserName, orderList);
		return result;
	}

	private String sendPushMsg8CarNo(String toUserName, String fromUserName, String content) {
		List<FmOrder> orderList = queryCommonOrder(toUserName, fromUserName, content);
		if (orderList == null || orderList.size() == 0) {
			return null;
		}
		String result = fixNews4OrderList(toUserName, fromUserName, orderList);
		return result;
	}
	
	private String fixNews4OrderList(String toUserName, String fromUserName, List<FmOrder> orderList) {
		Date now = new Date();
		int size = orderList.size();
		StringBuffer desc = null;
		String orderNo = null;
		String caseNo = null;
		String carNo = null;
		String sellerUserName = null;
		String buyerUserName = null;
		String linkMan = null;
		String workAddress = null;

		String orderInfoUrl = "";
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
		sb.append("<CreateTime>" + now.getTime() + "</CreateTime>");
		sb.append("<MsgType><![CDATA[news]]></MsgType>");
		sb.append("<ArticleCount>" + size + "</ArticleCount>");
		sb.append("<Articles>");
		for (FmOrder order : orderList) {
			orderNo = order.getOrderNo();
			caseNo = order.getCaseNo();
			carNo = order.getCarNo();
			sellerUserName = order.getSellerUserName();
			buyerUserName = order.getBuyerUserName();
			linkMan = order.getLinkMan();
			workAddress = order.getWorkAddress();
			// TODO url可能不对.		
			orderInfoUrl = pushMessageService.fixUrl8FmOrder(order, fromUserName);
			
			
			desc.append("案件号:").append(caseNo);
			desc.append(",车牌号:").append(carNo);
			desc.append(",车童:").append(sellerUserName);
			desc.append(",委托方:").append(buyerUserName);
			desc.append(",车主:").append(linkMan);
			desc.append(",出险地:").append(workAddress);

			sb.append("<item>");
			sb.append("<Title><![CDATA[" + orderNo + "]]></Title>");
			sb.append("<Description><![CDATA[" + desc.toString() + "]]></Description>");
			sb.append(
					"<PicUrl><![CDATA[http://200681.image.myqcloud.com/200681/0/212d8dcb-f448-4e41-a215-b01721305221/original]]></PicUrl>");
			sb.append("<Url><![CDATA[" + orderInfoUrl + "]]></Url>");
			sb.append("</item>");
		}
		sb.append("</Articles>");
		sb.append("</xml> ");

		return sb.toString();
	}

	// 通过orderNo和userId查出订单(如果userId与该订单没有关系,就不查出来)
	private List<FmOrder> queryCommonOrder(String toUserName, String fromUserName, String searchKey) {
		Long userId = queryUserId8Openid(toUserName, fromUserName);
		if (userId == null) {
			return null;
		}

		FmOrder fmOrder = new FmOrder();
		fmOrder.setOrderNo(searchKey);
		fmOrder.setCarNo(searchKey);
		fmOrder.setSellerUserId(userId);
		fmOrder.setBuyerUserId(userId);
		fmOrder.setGroupUserId(userId);

		List<FmOrder> orderList = commExeSqlDAO.queryForObject("custom_rank_mapper.queryFmOrder8NoAndUserId", fmOrder);
		return orderList;
	}

	// TODO 通过openid查出userId,是否需要加上公众号的id,因为以后可能有多个公众号.
	private Long queryUserId8Openid(String publicid, String openid) {
		Map map = new HashMap();
		map.put("weichatid", openid);
//		map.put("appid", publicid);
		
		List<CtUser> userList = commExeSqlDAO.queryForList("ct_bind_info.getBindInfo", map);
		
		if (userList != null && userList.size() > 0) {
			return userList.get(0).getId();
		} else {
			return null;
		}
	}

	private String fixText8Other(String toUserName, String fromUserName, String content) {
		Date now = new Date();
		String text = "请输入\"订单\",\"排行榜\",或订单号,或车牌号.";

		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
		sb.append("<CreateTime>" + now.getTime() + "</CreateTime>");
		sb.append("<MsgType><![CDATA[text]]></MsgType>");
		sb.append("<Content><![CDATA[" + text + "]]></Content>");
		sb.append("</xml>");

		return sb.toString();
	}
	
	private String fixNews8Param(String toUserName, String fromUserName, String content) {
		List<FmOrder> orderList = queryCommonOrder(toUserName, fromUserName, content);
		if (orderList == null || orderList.size() == 0) {
			return null;
		}
		
		int size = orderList.size() + 2;
		StringBuffer desc = null;
		String orderNo = null;
		String caseNo = null;
		String carNo = null;
		String sellerUserName = null;
		String buyerUserName = null;
		String linkMan = null;
		String workAddress = null;

		String orderInfoUrl = "";
		Date now = new Date();
		String headUrl = headerUrl.replaceAll("http://", "").replaceAll("/", "");
		// TODO 这里的url可能不对.
		String orderStatUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri=http%3A%2F%2F"+headUrl+"%2Fctweb%2Fview%2Ftowechat.html&response_type=code&scope=snsapi_base&state=order#wechat_redirect";
		String rankUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri=http%3A%2F%2F"+headUrl+"%2Fctweb%2Fview%2Ftowechat.html&response_type=code&scope=snsapi_base&state=rank#wechat_redirect";
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
		sb.append("<CreateTime>" + now.getTime() + "</CreateTime>");
		sb.append("<MsgType><![CDATA[news]]></MsgType>");
		sb.append("<ArticleCount>"+size+"</ArticleCount>");
		sb.append("<Articles>");
		sb.append("<item>");
		sb.append("<Title><![CDATA[订单状态分类]]></Title>");
		sb.append("<Description><![CDATA[订单状态分类]]></Description>");
		sb.append(
				"<PicUrl><![CDATA[http://200681.image.myqcloud.com/200681/0/212d8dcb-f448-4e41-a215-b01721305221/original]]></PicUrl>");
		sb.append("<Url><![CDATA["+orderStatUrl+"]]></Url>");
		sb.append("</item>");
		sb.append("<item>");
		sb.append("<Title><![CDATA[排行榜]]></Title>");
		sb.append("<Description><![CDATA[订单量和品质的排行榜]]></Description>");
		sb.append(
				"<PicUrl><![CDATA[http://200681.image.myqcloud.com/200681/0/a20c66a8-24ec-486c-b4e7-6c980e0408bf/original]]></PicUrl>");
		sb.append("<Url><![CDATA["+rankUrl+"]]></Url>");
		sb.append("</item>");
		for (FmOrder order : orderList) {
			orderNo = order.getOrderNo();
			caseNo = order.getCaseNo();
			carNo = order.getCarNo();
			sellerUserName = order.getSellerUserName();
			buyerUserName = order.getBuyerUserName();
			linkMan = order.getLinkMan();
			workAddress = order.getWorkAddress();
			// TODO url可能不对.		
			orderInfoUrl = pushMessageService.fixUrl8FmOrder(order, fromUserName);			
			
			desc.append("案件号:").append(caseNo);
			desc.append(",车牌号:").append(carNo);
			desc.append(",车童:").append(sellerUserName);
			desc.append(",委托方:").append(buyerUserName);
			desc.append(",车主:").append(linkMan);
			desc.append(",出险地:").append(workAddress);

			sb.append("<item>");
			sb.append("<Title><![CDATA[" + orderNo + "]]></Title>");
			sb.append("<Description><![CDATA[" + desc.toString() + "]]></Description>");
			sb.append(
					"<PicUrl><![CDATA[http://200681.image.myqcloud.com/200681/0/212d8dcb-f448-4e41-a215-b01721305221/original]]></PicUrl>");
			sb.append("<Url><![CDATA[" + orderInfoUrl + "]]></Url>");
			sb.append("</item>");
		}
		sb.append("</Articles>");
		sb.append("</xml> ");

		return sb.toString();
	}

	private String fixNews8Text(String toUserName, String fromUserName, String content) {
		Date now = new Date();
		String headUrl = headerUrl.replaceAll("http://", "").replaceAll("/", "");
		// TODO 这里的url可能不对.
		String orderStatUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri=http%3A%2F%2F"+headUrl+"%2Fctweb%2Fview%2Ftowechat.html&response_type=code&scope=snsapi_base&state=order#wechat_redirect";
		String rankUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri=http%3A%2F%2F"+headUrl+"%2Fctweb%2Fview%2Ftowechat.html&response_type=code&scope=snsapi_base&state=rank#wechat_redirect";
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
		sb.append("<CreateTime>" + now.getTime() + "</CreateTime>");
		sb.append("<MsgType><![CDATA[news]]></MsgType>");
		sb.append("<ArticleCount>2</ArticleCount>");
		sb.append("<Articles>");
		sb.append("<item>");
		sb.append("<Title><![CDATA[订单状态分类]]></Title>");
		sb.append("<Description><![CDATA[查看自己的订单状态分类]]></Description>");
		sb.append(
				"<PicUrl><![CDATA[http://200681.image.myqcloud.com/200681/0/212d8dcb-f448-4e41-a215-b01721305221/original]]></PicUrl>");
		sb.append("<Url><![CDATA["+orderStatUrl+"]]></Url>");
		sb.append("</item>");
		sb.append("<item>");
		sb.append("<Title><![CDATA[排行榜]]></Title>");
		sb.append("<Description><![CDATA[查看各自的订单量和品质的排行榜]]></Description>");
		sb.append(
				"<PicUrl><![CDATA[http://200681.image.myqcloud.com/200681/0/a20c66a8-24ec-486c-b4e7-6c980e0408bf/original]]></PicUrl>");
		sb.append("<Url><![CDATA["+rankUrl+"]]></Url>");
		sb.append("</item>");
		sb.append("</Articles>");
		sb.append("</xml> ");

		return sb.toString();
	}

	@Override
	public String imageSave(ModelMap map) {
		String ToUserName = (String) map.get("ToUserName"); // 开发者微信号
		String FromUserName = (String) map.get("FromUserName"); // 发送方帐号（一个OpenID）
		String CreateTime = (String) map.get("CreateTime"); // 消息创建时间 （整型）
		String MsgType = (String) map.get("MsgType"); // 消息类型，text
		String PicUrl = (String) map.get("PicUrl"); // 图片链接（由系统生成）
		String MediaId = (String) map.get("MediaId"); // 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
		String MsgId = (String) map.get("MsgId"); // 消息id，64位整型

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String locationHandle(ModelMap map) {
		CtUser user = null;
		CtPersonStat ps = new CtPersonStat();
		Map param = new HashMap();
		Date now = new Date();
		String result = null;

		String ToUserName = (String) map.get("ToUserName"); // 开发者微信号
		String FromUserName = (String) map.get("FromUserName"); // 发送方帐号（一个OpenID）
		String CreateTime = (String) map.get("CreateTime"); // 消息创建时间 （整型）
		String MsgType = (String) map.get("MsgType"); // 消息类型，text
		String Location_X = (String) map.get("Location_X"); // 地理位置维度
		String Location_Y = (String) map.get("Location_Y"); // 地理位置经度
		String Scale = (String) map.get("Scale"); // 地图缩放大小
		String Label = (String) map.get("Label"); // 地理位置信息
		String MsgId = (String) map.get("MsgId"); // 消息id，64位整型

		ToUserName = WechatXmlUtil.killCDATAsign(ToUserName);
		FromUserName = WechatXmlUtil.killCDATAsign(FromUserName);
		Label = WechatXmlUtil.killCDATAsign(Label);

		param.put("weichatid", FromUserName);

		List<CtUser> userList = commExeSqlDAO.queryForList("ct_bind_info_MAPPER.getBindInfo", param);

		if (userList != null && userList.get(0) != null) {
			user = userList.get(0);
			ps.setUserId(user.getId());
			ps.setLongitude(BigDecimal.valueOf(Long.parseLong(Location_Y)));
			ps.setDimension(BigDecimal.valueOf(Long.parseLong(Location_X)));
			ps.setLastNotifyTime(now);
			ps.setExt3("wechat");

			commExeSqlDAO.updateVO("ct_person_stat.updateLOCATION", ps);
			result = fixText8Location(user, ToUserName, FromUserName, Location_X, Location_Y, Label, MsgId);
		}

		// TODO Auto-generated method stub
		return result;
	}

	private String fixText8Location(CtUser user, String toUserName, String fromUserName, String location_X,
			String location_Y, String label, String msgId) {
		// TODO Auto-generated method stub
		String text = "";
		Date now = new Date();
		if (user.getOrgName() == null) {
			text = user.getLastname() + user.getFirstname();
		} else {
			text = user.getOrgName();
		}

		text = text + ",您在" + label + ",坐标是(" + location_Y + "," + location_X + ").";

		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
		sb.append("<CreateTime>" + now.getTime() + "</CreateTime>");
		sb.append("<MsgType><![CDATA[text]]></MsgType>");
		sb.append("<Content><![CDATA[" + text + "]]></Content>");
		sb.append("</xml>");

		return sb.toString();
	}

}
