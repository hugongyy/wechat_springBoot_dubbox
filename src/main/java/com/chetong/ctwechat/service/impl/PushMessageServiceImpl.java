package com.chetong.ctwechat.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chetong.ctwechat.dao.CommExeSqlDAO;
import com.chetong.ctwechat.entity.mapping.CmsOutBox;
import com.chetong.ctwechat.entity.model.BasesModel;
import com.chetong.ctwechat.service.PushMessageService;

@Service("pushMessageService")
public class PushMessageServiceImpl implements PushMessageService {
	private Log log = LogFactory.getLog(WechatServiceImpl.class);
	@Autowired
	private CommExeSqlDAO commExeSqlDAO;

	@Value("$(WE_CHAT_PUSH_SWITCH)")
	private String WE_CHAT_PUSH_SWITCH;

	@Override
	public BasesModel savePushMsg4Wechat(Long userId, String content, String createBy) {
		BasesModel model = new BasesModel();
		CmsOutBox cob = new CmsOutBox();
		cob.setUserId(userId);
		cob.setMsgType("1");
		cob.setMsgContent(content);
		cob.setCreateBy(createBy);
		cob.setCreateTime(new Date());

		int i = commExeSqlDAO.insertVO("cms_out_box.insertNotNull", cob);
		if (i > 0) {
			model.setCode("success");
			model.setMessage("保存外发消息成功");
			model.setKeyWords(cob.getId() + "");
		} else {
			model.setCode("fail");
			model.setMessage("保存外发消息失败");
		}

		return model;
	}

	public void sendTextMessageToUser(String content, String openid, String accessToken) {
		String json = "";
		StringBuffer sb = new StringBuffer("\"");
		sb.append(openid);
		sb.append("\"");
		json = "{\"touser\":" + sb.toString() + ",\"msgtype\": \"text\", \"text\": {\"content\": \"" + content + "\"}}";
		// 获取请求路径
		String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken;
		try {
			connectWeiXinInterface(action, json);
		} catch (Exception e) {
			log.error("微信推送错误：", e);
		}
	}

	public void connectWeiXinInterface(String action, String json) {
		URL url;
		try {
			url = new URL(action);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

			log.info("微信推送信息:" + new Date().toString() + "  信息：" + json);

			if ("YES".equals(WE_CHAT_PUSH_SWITCH)) {
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
			}

		} catch (Exception e) {
			log.error("微信推送错误：", e);
		}
	}

}
