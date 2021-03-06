package com.chetong.ctwechat.helper.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chetong.aic.redis.RedisClinet;
import com.chetong.ctwechat.entity.model.AccessTokenModel;
import com.chetong.ctwechat.util.redis.RedissonUtils;

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
	@Value("${WE_CHAT_PUSH_SWITCH}")
	private String WE_CHAT_PUSH_SWITCH;
	
	@Autowired
	private RedissonUtils redissonUtils;

	@Scheduled(fixedRate = 5 * 60 * 1000)
	// @Scheduled(fixedDelay = 7100 * 1000)
	// @Scheduled(cron = "*/5 * * * * *")
	public void getAccess() {
		try {
			if(!"Y".equals(WE_CHAT_PUSH_SWITCH)){
				return;
			}
			
			accessToken = WeixinUtil.getAccessToken(appid, appsecret);
			if (null != accessToken) {
				log.info("get access_token SUCCESS ,times{}s token:{}", accessToken.getExpiresIn(),
						accessToken.getToken());
				// 休眠7000秒
				access = accessToken.getToken();
				redissonUtils.set(WeixinUtil.ACCESS_TOKEN, access, 6 * 60);
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
