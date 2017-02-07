package com.chetong.ctwechat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chetong.aic.entity.base.BaseModel;
import com.chetong.ctwechat.entity.sms.SendSmsBack;
import com.chetong.ctwechat.entity.sms.SendSmsResp;
import com.chetong.ctwechat.util.token.DESUtils;
import com.chetong.ctwechat.helper.xml.WechatXmlUtil;
import com.chetong.ctwechat.service.InterfaceService;
import com.chetong.ctwechat.service.SysSmsService;
import com.chetong.ctwechat.service.impl.SysSmsServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping("/interface")
public class InterfaceController {

	public final static String secretKey = "chetongwang@tum#526FVghJU8RX#2016";

	private Log log = LogFactory.getLog(WechatController.class);

	@Value("${headerUrl}")
	private String headerUrl;

	@Resource
	private InterfaceService interfaceService;
	
	@Resource
	private SysSmsService sysSmsService;
	
	@RequestMapping(value = "/wechatMsgUrl")
	@ResponseBody
	public BaseModel wechatMsgUrl(@RequestBody ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		BaseModel model = new BaseModel();
		String result = null;
		try {
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）,必须在response.getWriter();之前
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			PrintWriter out = response.getWriter();

			// 随机字符串
			String echostr = request.getParameter("echostr");
			log.info("echostr=" + echostr);
			out.println(echostr);
			ModelMap map = WechatXmlUtil.parseXml(request);
			log.info("parse xml to modelMap = " + map.toString());

			String MsgType = (String) map.get("MsgType");
			String Event = (String) map.get("Event");

			MsgType = WechatXmlUtil.killCDATAsign(MsgType);
			Event = WechatXmlUtil.killCDATAsign(Event);

			if (MsgType != null && "event".equals(MsgType) && Event != null && "LOCATION".equals(Event)) {
				// 定位信息
				result = interfaceService.locationSave(map);
			} else if (MsgType != null && "text".equals(MsgType)) {
				// 在公众号发Text消息
				result = interfaceService.textSave(map);
			} else if (MsgType != null && "image".equals(MsgType)) {
				// 在公众号发图片消息
				result = interfaceService.imageSave(map);
			} else if (MsgType != null && "location".equals(MsgType)) {
				// 在公众号发地理位置消息
				result = interfaceService.locationHandle(map);
			}
			
			model.setKeyWords(result);

			if (result != null) {
				out.print(result);
			}

		} catch (Exception e) {
			log.error(this, e);
		}

		return model;
	}

	/**
	 * 显示订单详情,参数加密过的.
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/showOrderInfo", method = RequestMethod.GET)
	public void showOrderInfo(String params, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.info("params = " + params);
		String url = "www.chetong.net";
		if (params != null) {
			try {
				params = URLDecoder.decode(params);
				params = DESUtils.decode(params, secretKey);
				url = headerUrl + "wechat/#/order/info?" + params;
			} catch (Exception e) {
				log.error(this, e);
				url = "www.chetong.net";
			}
		} else {
			url = "www.chetong.net";
		}

		 response.sendRedirect(url);
//		request.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * 短信下发后的短信接收状态的回调接口.
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSendSmsResp")
	@ResponseBody
	public SendSmsBack saveSendSmsResp(@RequestBody ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SendSmsBack back = new SendSmsBack();
		String result = null;
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）,必须在response.getWriter();之前
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 获取其中的信息.
		String json = IOUtils.toString(inputStream, "UTF-8");

		List<SendSmsResp> ssrList = new ArrayList<SendSmsResp>();
		Gson gson = new Gson();
		ssrList = gson.fromJson(json, ssrList.getClass());

		if (ssrList != null && ssrList.size() > 0 && ssrList.get(0) != null) {
			result = sysSmsService.reportTencentSms(ssrList);
		}

		back.setResult("0");
		back.setErrmsg("SUCCESS");

		return back;
	}

	/**
	 * 测试短信分通道发送
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/testSendSms")
	@ResponseBody
	public BaseModel testSendSms(@RequestBody ModelMap modelMap) {	
		BaseModel baseModel = new BaseModel();
		String mobile = (String) modelMap.get("mobile");
		String content = (String) modelMap.get("content");
		String smsType = (String) modelMap.get("smsType");
		String senderId = (String) modelMap.get("senderId");
		
		if (smsType == null) {
			sysSmsService.sendSms(mobile, content);
		} else {
			sysSmsService.sendSms(mobile, content, smsType, senderId);
		}
		
		baseModel.setKeyWords("sendSmsType = "+SysSmsServiceImpl.sendSmsChannel);
		return baseModel;
	}
	
	@RequestMapping(value = "/testSendTemplateSms")
	@ResponseBody
	public BaseModel testSendTemplateSms(@RequestBody ModelMap modelMap) {	
		BaseModel baseModel = new BaseModel();
		final String mobile = (String) modelMap.get("mobile");
		final String templateNo = (String) modelMap.get("templateNo");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Map map = new HashMap();
				map.put("orderNo", "A1609000125");
				map.put("caseNo", "25462542");
				map.put("carNo", "粤BN5iu6");
				
				sysSmsService.sendTemplateSms(mobile, templateNo, map);
			}
		}).start();
		
		
		baseModel.setKeyWords(templateNo);
		return baseModel;		
	}
}
