package com.chetong.ctwechat.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.chetong.aic.entity.ResultVO;
import com.chetong.aic.enums.ProcessCodeEnum;
import com.chetong.aic.exception.ProcessException;
import com.chetong.aic.util.SingletonClient;
import com.chetong.ctwechat.dao.CommExeSqlDAO;
import com.chetong.ctwechat.entity.mapping.CmsTemplateInfo;
import com.chetong.ctwechat.entity.mapping.SysSms;
import com.chetong.ctwechat.entity.mapping.VersionInfo;
import com.chetong.ctwechat.entity.sms.Mo;
import com.chetong.ctwechat.entity.sms.OneSmsReq;
import com.chetong.ctwechat.entity.sms.OneSmsResp;
import com.chetong.ctwechat.entity.sms.SendSmsResp;
import com.chetong.ctwechat.helper.DateUtil;
import com.chetong.ctwechat.helper.sms.SDKHttpClient;
import com.chetong.ctwechat.service.SysSmsService;
import com.google.gson.Gson;

import cn.emay.sdk.client.api.StatusReport;

@Service("sysSmsService")
public class SysSmsServiceImpl implements SysSmsService {

	public static String sendSmsChannel = "emay";
	public static String receiveSmsChannel = "emay";

	private Log log = LogFactory.getLog(SysSmsService.class);
	@Resource
	private CommExeSqlDAO commExeSqlDAO;

	private Random random = new Random();
	// 请根据我们的说明文档适时调整 url
	private String url = "https://yun.tim.qq.com/v3/tlssmssvr/sendsms";
	// 腾讯云短信服务器配置
	@Value("${sdkappid}")
	private int sdkappid;
	@Value("${appkey}")
	private String appkey;
	// 亿美短信服务器配置
	@Value("${softwareSerialNo}")
	private String softwareSerialNo;
	@Value("${key}")
	private String key;

	@Value("${isTestEnv}")
	private String isTestEnv;

	private final static String NO_PARAM = "缺少必要参数";

	@Override
	public ResultVO<Object> sendTemplateSms(String mobile, String templateNo, Map<String, String> map) {
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(templateNo) || map == null) {
			throw ProcessCodeEnum.PARAMETER_ERR.buildProcessException();
		}
		String content = "";
		CmsTemplateInfo template = new CmsTemplateInfo();
		template.setTemplateNo(templateNo);
		template = commExeSqlDAO.queryForObject("cms_template_info.queryCmsTemplateInfo", template);
		if (template == null) {
			throw ProcessCodeEnum.PARAMETER_ERR.buildProcessException();
		}
		
		content = template.getTemplateInfo();

		Set<String> set = map.keySet();
		String ks = null;
		for (String param : set) {
			ks = map.get(param);
			ks = ks == null ? "" : ks;
			content = content.replaceAll("#" + param + "#", ks);
		}

		return sendSms(mobile, content);
	}

	@Override
	public ResultVO<Object> sendSms(String mobile, String content) {

		if ("tencent".equals(sendSmsChannel)) {
			return sendTencentSms(mobile, content);
		} else {
			return sendEmaySms(mobile, content);
		}
	}

	@Override
	public ResultVO<Object> sendSms(String mobile, String content, String smsType, String senderId) {

		if ("tencent".equals(sendSmsChannel)) {
			return sendTencentSms(mobile, content, smsType, senderId);
		} else {
			return sendEmaySms(mobile, content, smsType, senderId);
		}
	}

	@Override
	public ResultVO<Object> sendEmaySms(String mobile, String content) {

		// 9-代表系统短信.
		return sendEmaySms(mobile, content, "9", "system_emay");

	}

	@Override
	public ResultVO<Object> sendEmaySms(String mobile, String content, String smsType, String senderId) {
		ResultVO<Object> resultVO = new ResultVO<Object>();
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(content) || StringUtils.isEmpty(smsType)
				|| StringUtils.isEmpty(senderId)) {
			throw ProcessCodeEnum.PARAMETER_ERR.buildProcessException();
		}

		String sendSmsUrl = "http://bj999.eucp.b2m.cn:8080/sdkproxy/sendsms.action";
		// 发送短信.和保存短信内容.
		SysSms sms = new SysSms();
		sms.setSmsType(smsType);
		sms.setMobileNumber(mobile);
		sms.setSmsContent(content);
		sms.setSentTime(DateUtil.getDateTime());
		sms.setDealStatus("0");
		sms.setCreateTime(DateUtil.getDateTime());
		sms.setReceiverId(mobile);
		sms.setSenderId(senderId);

		if ("emay2".equals(sendSmsChannel)) {
			try {
				int k = SingletonClient.getClient(softwareSerialNo, key).sendSMS(new String[] { mobile },
						"【车童网】" + content, "", 5);
				sms.setSmsStatus(k + "");
			} catch (Exception e) {
				log.error(this, e);
				throw ProcessCodeEnum.FAIL.buildProcessException("发送亿美Socket短信失败");
			}

		} else if ("emay".equals(sendSmsChannel)) {
			try {
				String message = URLEncoder.encode("【车童网】" + content, "UTF-8");
				String code = "888";
				long seqId = System.currentTimeMillis();
				String param = "cdkey=" + softwareSerialNo + "&password=" + key + "&phone=" + mobile + "&message="
						+ message + "&addserial=" + code + "&seqid=" + seqId;
				String ret = SDKHttpClient.sendSMSByPost(sendSmsUrl, param);
				sms.setSmsStatus(ret);
				sms.setReplyPid(seqId + ""); // 自造的seqId也存在replay_pid中,与tencent的不同,与上行短信回复的id也不同.
			} catch (UnsupportedEncodingException e) {
				log.error(this, e);
				throw ProcessCodeEnum.FAIL.buildProcessException("发送亿美HTTP短信失败");
			}
		}

		int r = commExeSqlDAO.insertVO("sys_sms.insertNotNull", sms);
		ProcessCodeEnum.SUCCESS.buildResultVO(resultVO);
		return resultVO;
	}

	@Override
	public ResultVO<Object> sendTencentSms(String mobile, String content) {
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(content)) {
			throw ProcessCodeEnum.PARAMETER_ERR.buildProcessException();
		}
		// 9-代表系统短信.
		return sendTencentSms(mobile, content, "9", "system_tencent");
	}

	@Override
	public ResultVO<Object> sendTencentSms(String mobile, String content, String smsType, String senderId) {
		ResultVO<Object> resultVO = new ResultVO<Object>();
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(content) || StringUtils.isEmpty(smsType)
				|| StringUtils.isEmpty(senderId)) {
			throw ProcessCodeEnum.PARAMETER_ERR.buildProcessException();
		}

		try {
			// 发送短信.和保存短信内容.
			SysSms sms = new SysSms();
			sms.setSmsType(smsType);
			sms.setMobileNumber(mobile);
			//2016-12-21 过滤短信内容特殊字符
			content = this.filterSpecialChar(content);
			sms.setSmsContent(content);
			sms.setSentTime(DateUtil.getDateTime());
			sms.setDealStatus("0");
			sms.setCreateTime(DateUtil.getDateTime());
			sms.setReceiverId(mobile);
			sms.setSenderId(senderId);

			OneSmsResp resp = sendMsg("86", mobile, content);
			if (resp == null) {
				sms.setSmsStatus("-1");
			} else if ("0".equals(resp.getResult())) {
				sms.setSmsStatus(resp.getResult());
				sms.setReplyPid(resp.getSid()); // sid 存在replyPid中
			} else {
				sms.setSmsStatus(resp.getResult());
			}

			int r = commExeSqlDAO.insertVO("sys_sms.insertNotNull", sms);
			ProcessCodeEnum.SUCCESS.buildResultVO(resultVO);
		} catch (Exception e) {
			log.error(this, e);
			throw ProcessCodeEnum.FAIL.buildProcessException("发送腾讯云短信失败");
		}

		return resultVO;
	}

	public OneSmsResp sendMsg(String nationCode, String phoneNumber, String content)
			throws IOException, NoSuchAlgorithmException {
		OneSmsResp resp = null;
		long rnd = random.nextInt(999999) % (999999 - 100000 + 1) + 100000;
		String wholeUrl = String.format("%s?sdkappid=%d&random=%d", url, sdkappid, rnd);

		URL object = new URL(wholeUrl);
		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestMethod("POST");

		Gson gson = new Gson();
		String sig = stringMD5(appkey.concat(phoneNumber));
		OneSmsReq req = new OneSmsReq();
		req.setTel(phoneNumber, nationCode);
		req.setMsg(content);
		req.setSig(sig);
		String data = gson.toJson(req);

		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), "utf-8");
		wr.write(data.toString());
		wr.flush();

		// 显示 POST 请求返回的内容
		StringBuilder sb = new StringBuilder();
		int HttpResult = con.getResponseCode();
		if (HttpResult == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			log.info(sb.toString());
			resp = gson.fromJson(sb.toString(), OneSmsResp.class);
		} else {
			log.info(con.getResponseMessage());
		}
		return resp;
	}

	private static String stringMD5(String input) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		byte[] inputByteArray = input.getBytes();
		messageDigest.update(inputByteArray);
		byte[] resultByteArray = messageDigest.digest();
		return byteArrayToHex(resultByteArray);
	}

	private static String byteArrayToHex(byte[] byteArray) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] resultCharArray = new char[byteArray.length * 2];
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultCharArray);
	}

	@Override
	public List<VersionInfo> queryVersionInfoList() {
		List<VersionInfo> viList = commExeSqlDAO.queryForList("version_info.queryVersionInfo", null);
		return viList;
	}

	// 每10秒收一次上行短信.
	@Scheduled(fixedRate = 10 * 1000)
	public void receiveEmaySms() {
		// log.info(">>>>>>>>>> receiveEmaySms()");
		if (!"N".equals(isTestEnv)) {
			// 测试环境不能收取短信.
			return;
		}

		int r = 0;
		SysSms one = null;
		String receiveSmsUrl = "http://bj999.eucp.b2m.cn:8080/sdkproxy/getmo.action";
		List<Mo> list = SDKHttpClient.getMos(receiveSmsUrl, softwareSerialNo, key);
		if (list != null && list.size() > 0) {
			for (Mo mo : list) {
				one = new SysSms();
				one.setSmsType("1");
				one.setMobileNumber(mo.getMobileNumber());
				one.setReceiverId("admin2");
				one.setSenderId(mo.getMobileNumber());
				one.setSmsContent(mo.getSmsContent());
				one.setChannelNumber(mo.getChannelnumber());
				one.setAddSerialRav(mo.getAddSerialRev());
				one.setAddSerial(mo.getAddSerial());
				one.setSentTime(mo.getSentTime());
				one.setSmsStatus("3");
				one.setSmsRemark("");
				one.setCreateTime(DateUtil.getDateTime());

				r += commExeSqlDAO.insertVO("sys_sms.insertNotNull", one);
			}

			log.info("receive emay sms size = " + list.size());
		}
		return;
	}

	@Override
	public String reportTencentSms(List<SendSmsResp> ssrList) {
		// TODO 腾讯云接收状态报告.要配置.
		SysSms sms = null;
		int r = 0;
		String user_receive_time = null;
		String mobile = null;
		String report_status = null;
		String errmsg = null;
		String description = null;
		String sid = null;

		if (ssrList != null && ssrList.size() > 0 && ssrList.get(0) != null) {
			List<SysSms> smsList = new ArrayList<SysSms>();
			for (SendSmsResp ssr : ssrList) {
				sms = new SysSms();
				user_receive_time = ssr.getUser_receive_time();
				mobile = ssr.getMobile();
				report_status = ssr.getReport_status();
				errmsg = ssr.getErrmsg();
				description = ssr.getDescription();
				sid = ssr.getSid();

				if ("SUCCESS".equals(report_status)) {
					sms.setReplyPid(sid); // sid 存在replyPid中
					sms.setSmsStatus("9"); // 9-手机已收到短信.
				} else {
					sms.setReplyPid(sid);
					sms.setSmsStatus("-1"); // -1 - 手机没有收到短信.
					sms.setSmsRemark(description); // 失败原因.
				}
				smsList.add(sms);
			}
			r = commExeSqlDAO.updateBatchVO("sys_sms.updateByReplayPid", smsList);
		}
		return r + "";
	}

	// 每60秒主动收一次亿美下行短信状态报告
	@Scheduled(fixedRate = 60 * 1000)
	public void reportEmaySms() {
		// TODO 亿美接收状态报告
		log.info(">>>>>>>>>> reportEmaySms()");
		long seqId = 0L;
		String errorCode = null;

		int r = 0;
		SysSms one = null;
		List<SysSms> smsList = new ArrayList<SysSms>();
		String reportSmsUrl = "http://bj999.eucp.b2m.cn:8080/sdkproxy/getreport.action";
		List<StatusReport> list = SDKHttpClient.getReports(reportSmsUrl, softwareSerialNo, key);
		if (list != null && list.size() > 0) {
			for (StatusReport sr : list) {
				one = new SysSms();

				seqId = sr.getSeqID();
				errorCode = sr.getErrorCode();
				log.info("mobile=" + sr.getMobile() + ", seqId=" + seqId + ", errorCode=" + errorCode);
				one.setReplyPid(seqId + "");
				if ("DELIVRD".equals(errorCode)) {
					one.setSmsStatus("9");
				} else {
					one.setSmsStatus(errorCode);
				}

				smsList.add(one);
			}
			r = commExeSqlDAO.updateBatchVO("sys_sms.updateByReplayPid", smsList);
			log.info("report emay sms size = " + list.size());
		}
		return;
	}
	
	/**
	 * xuming 2016-12-21 过滤特殊字符
	 * @param smsContent
	 * @return
	 * String
	 */
	private String filterSpecialChar(String smsContent){
		String strReturn = smsContent;
		strReturn = strReturn.replaceAll("【", "[");//把所有的【替换
		strReturn = strReturn.replaceAll("】", "]");//把所有的】替换
		return strReturn;
	}
}
