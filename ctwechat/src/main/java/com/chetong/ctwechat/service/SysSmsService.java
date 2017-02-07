package com.chetong.ctwechat.service;

import java.util.List;
import java.util.Map;

import com.chetong.aic.entity.ResultVO;
import com.chetong.ctwechat.entity.mapping.VersionInfo;
import com.chetong.ctwechat.entity.sms.SendSmsResp;

public interface SysSmsService {
	// 都不要加【车童网】.
	/**
	 * 发模板短信
	 * @param mobile 手机号码
	 * @param templateNo 模板编号
	 * @param map 参数
	 * @return
	 */
	ResultVO<Object> sendTemplateSms(String mobile, String templateNo, Map<String, String> map);
	
	/**
	 * 发普通短信
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @return
	 */
	ResultVO<Object> sendSms(String mobile, String content);
	
	/**
	 * 发普通短信
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @param smsType 短信类型
	 * @param senderId 发送方
	 * @return
	 */
	ResultVO<Object> sendSms(String mobile, String content, String smsType, String senderId);
	
	ResultVO<Object> sendEmaySms(String mobile, String content);

	ResultVO<Object> sendEmaySms(String mobile, String content, String smsType, String senderId);

	ResultVO<Object> sendTencentSms(String mobile, String content);

	ResultVO<Object> sendTencentSms(String mobile, String content, String smsType, String senderId);

	List<VersionInfo> queryVersionInfoList();

	String reportTencentSms(List<SendSmsResp> ssrList);
}
