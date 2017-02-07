package com.chetong.ctwechat.aop;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chetong.aic.enums.ProcessCodeEnum;
import com.chetong.ctwechat.util.redis.RedissonUtils;
import com.chetong.ctwechat.util.token.TokenUtils;

@Aspect
@Order(1)
@Component
public class RecordOperationAop {
	
	@Value("${loginValidTime}")
	private int loginValidTime;
	
	private Logger log = LogManager.getLogger("opLogger");
	
	@Before("execution (* com.chetong.ctwechat.controller.WechatController.*(..))")
	public void aroundApp(JoinPoint point) throws Exception {
		try {
			//直接从request中取token
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			String token = request.getHeader("token");
			
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String string = (String) headerNames.nextElement();
				log.warn(string+":"+request.getHeader(string));
			}
			
			if(StringUtils.isNotBlank(token)){
				String tokenMsg = TokenUtils.praseToken(token);
				
				String[] params = tokenMsg.split("#");
				
				String userId = params[0];
				
				String origin = params[3];
				
				String redisKey = "Token:"+origin;
				
				if("PC".equals(origin)){
					//刷新相应redisToken
					RedissonUtils.flushMapCacheValue(redisKey, userId, loginValidTime);
					RedissonUtils.flushMapCacheValue("userInfo", userId, loginValidTime);
				}
				
			}
			
			StringBuffer requestURL = request.getRequestURL();
			String methodName = point.getSignature().getName();
			Object[] args = point.getArgs();
			log.warn("-----------------------------oplog---------------------------:\n"
					+"url:"+requestURL.toString()+"\n"
					+"args:"+Arrays.toString(args)+"\n"
					+"methodName:"+methodName+"\n"
					+"token:"+token);
		} catch (Exception e) {
			log.error("操作日志异常",e);
			throw ProcessCodeEnum.FAIL.buildProcessException(e);
		}
	}
}
