package com.chetong.ctwechat.util.common;

import com.chetong.aic.util.StringUtil;

public class MobileUtils {
	
	/**
	 * 模糊处理电话号码
	 * fuzzyPhone
	 * @param phone
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String fuzzyPhone(String phone){
		if(StringUtil.isNullOrEmpty(phone)){
			return phone;
		}
		phone = phone.trim();
		if(phone.length()==11){
			return new StringBuilder(phone.substring(0, 3)).append("****").append(phone.substring(7,11)).toString();
		}
		return phone;
	}
	
	
	/**
	 * 模糊（中间四位编程*）所有的手机号
	 * @param originStr 原始字符串
	 * @return 处理后的数据
	 */
	public static String fuzzyPhoneOfText(Object originStr){
		if(isNullOrEmpty(originStr)){
			return "";
		}
		return originStr.toString().replaceAll("([^\\d])(1[34578]\\d)(\\d{4})(\\d{4})([^\\d])", "$1$2****$4$5");
	}
	
	/**
	 * 判断对象是否为空
	 * 
	 * @param Obj
	 * @return 为空返回 TRUE
	 */
	public static boolean isNullOrEmpty(Object o) {
		if (o == null || "".equals(o)) {
			return true;
		}
		return false;
	}

}
