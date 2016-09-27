package com.chetong.ctwechat.helper.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chetong.ctwechat.entity.model.AccessTokenModel;

/**
 * 定时获取微信access_token的线程
 * 
 */
@Component("tokenThread")
public class TokenThread {
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);
	// 第三方用户唯一凭证
	// public static String appid = "";
	// 第三方用户唯一凭证密钥
	// public static String appsecret = "";
	public static AccessTokenModel accessToken = null;
	public static String access = "";

	@Value("${appid}")
	private String appid;
	@Value("${appsecret}")
	private String appsecret;

	@Scheduled(fixedRate = 10 * 60 * 1000)
	// @Scheduled(fixedDelay = 7100 * 1000)
	// @Scheduled(cron = "*/5 * * * * *")
	public void getAccess() {
		try {
			accessToken = WeixinUtil.getAccessToken(appid, appsecret);
			if (null != accessToken) {
				log.info("get access_token SUCCESS ,times{}s token:{}", accessToken.getExpiresIn(),
						accessToken.getToken());
				// 休眠7000秒
				access = accessToken.getToken();
				// Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
			} else {
				// 如果access_token为null，60秒后再获取
				Thread.sleep(60 * 1000);
			}
		} catch (InterruptedException e) {
			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e1) {
				log.error("{}", e1);
			}
			log.error("{}", e);
		}
	}
}
