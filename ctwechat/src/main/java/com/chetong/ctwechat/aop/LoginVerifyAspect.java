package com.chetong.ctwechat.aop;


import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chetong.aic.enums.ProcessCodeEnum;
import com.chetong.aic.exception.ProcessException;
import com.chetong.ctwechat.util.redis.RedissonUtils;
import com.chetong.ctwechat.util.token.TokenUtils;



/**
 * 登录验证（后续可以添加权限校验）
 * 
 * @author Dylan
 * @date 2015年12月17日
 */
@Aspect
@Order(2)
@Component
public class LoginVerifyAspect {
	
	private Logger log = LogManager.getLogger("opLogger");
	
	@Value("${isTestEnv}")
	private String isTestEnv;
	
	@Before("execution (* com.chetong.ctwechat.controller.WechatController.*(..))"
			+ "&& !execution (* com.chetong.ctwechat.controller.InterfaceController.testSendSms(..))"
			+ "&& !execution (* com.chetong.ctwechat.controller.InterfaceController.saveSendSmsResp(..))"
			+ "&& !execution (* com.chetong.ctwechat.controller.InterfaceController.wechatMsgUrl(..))"
			+ "&& !execution (* com.chetong.ctwechat.controller.InterfaceController.showOrderInfo(..))"
			+ "&& !execution (* com.chetong.ctwechat.controller.WechatController.getAccessToken(..))"
			+ "&& !execution (* com.chetong.ctwechat.controller.WechatController.testSendOutMsg(..))"
			+ "&& !execution (* com.chetong.ctwechat.controller.WechatController.bindingopenId(..))"
			+ "&& !execution (* com.chetong.ctwechat.controller.WechatController.getOpenId(..))"
			+ "&& !execution (* com.chetong.ctwechat.controller.WechatController.getUserInfoByOpenId(..))")
	public void loginVerify(JoinPoint joinPoint) throws Exception {
		try {
			if ("Y".equals(isTestEnv)) {
				return;
			}
			
			//直接从request中取token
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			
			String url = request.getRequestURL().toString();
			//token
			String token = request.getHeader("token");
			String userId = request.getHeader("userId");
			
			if (StringUtils.isBlank(token)||StringUtils.isBlank(userId)) {
				log.warn("登陆无效：无token或无userId");
				throw new ProcessException("3333", "登陆超时,请重新登陆");
			}

			String tokenMsg = TokenUtils.praseToken(token);
			
			String[] params = tokenMsg.split("#");
			
			String tokenUserId = params[0];
			
			String origin = params[3];
			
			log.warn("请求验证--->:参数：token:"+token+" userId:"+userId+" origin:"+origin+" url:"+url);
			
			String redisKey = "Token:"+origin;
			String redisToken = (String) RedissonUtils.getMapCacheValue(redisKey, userId);
			if (StringUtils.isBlank(redisToken)){
				log.warn("token:"+token+"登陆无效：token失效");
				throw new ProcessException("3333", "登陆超时,请重新登陆");
			}
			
			if(!(redisToken.equals(token)&&userId.equals(tokenUserId))){
				log.warn("登陆无效：token校验错误:\n"
						+ "token:"+token+"\n"
						+ "redisToken:"+redisToken+"\n"
						+ "tokenUserId:"+tokenUserId+"\n"
						+ "userId:"+userId);
				throw new ProcessException("3333", "登陆超时,请重新登陆");
			}
		} catch (Throwable e) {
			log.error("登陆校验异常",e);
			throw new ProcessException("3333", "登陆超时,请重新登陆");
		}
        
	}

}
