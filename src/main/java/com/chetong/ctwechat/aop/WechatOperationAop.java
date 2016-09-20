package com.chetong.ctwechat.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.chetong.ctwechat.controller.WechatController;

@Aspect
@Component
public class WechatOperationAop {
	private Log log = LogFactory.getLog(WechatOperationAop.class);

	@Pointcut("execution (* com.chetong.ctwechat.controller.*.*(..))")
	public void excudeService() {
	}

	@Around("excudeService()")
	public Object aroundWeb(ProceedingJoinPoint point) throws Throwable {
		// 获取将要执行的方法名称
		String methodName = point.getSignature().getName();
		log.info("*********** method=" + methodName);
		// 获取执行方法的参数
		Object[] args = point.getArgs();
		// 从参数列表中获取参数对象
		for (Object obj : args) {// 查看参数值
			log.info("*********** " + obj.toString());
		}
		Object object = point.proceed();
		return object;
	}
}
