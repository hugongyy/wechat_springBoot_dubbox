package com.chetong.ctwechat.helper;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import com.chetong.aic.util.StringUtil;

public class DateUtil {
	// ~ Static fields/initializers
	// =============================================

	private static Log log = LogFactory.getLog(DateUtil.class);
	private static String defaultDatePattern = null;
	private static String timePattern = "HH:mm:ss";

	// 短日期格式
	public static String DATE_FORMAT = "yyyy-MM-dd";
	// 长日期格式
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// ~ Methods
	// ================================================================

	/**
	 * 返回两个时间的差
	 * @param now 当前时间 "yyyy-MM-dd HH:mm:ss"
	 * @param oldTime 过去时间 "yyyy-MM-dd HH:mm:ss"
	 * @return days + "天" + hours + "小时" + minutes + "分"
	 */
	public static synchronized String diffTimeToString(String now, String targetTime) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;
		try {
			d1 = df.parse(targetTime);
			d2 = df.parse(now);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return diffTimeToString(d1, d2);
	}
	
	/**
	 * 返回两个时间的差
	 * @param now
	 * @param targetTime
	 * @return days + "天" + hours + "小时" + minutes + "分"
	 */
	public static synchronized String diffTimeToString(Date now, Date targetTime) {
		
		long diff = now.getTime() - targetTime.getTime();// 这样得到的差值是微秒级别
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
		String diffTime = days + "天" + hours + "小时";
		return diffTime;
	}

	/**
	 * Return default datePattern (yyyy-MM-dd)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static synchronized String getDatePattern() {
		Locale locale = LocaleContextHolder.getLocale();
		try {
			// defaultDatePattern =
			// ResourceBundle.getBundle(Constants.BUNDLE_KEY,
			// locale).getString("date.format");
			defaultDatePattern = "yyyy-MM-dd";
		} catch (MissingResourceException mse) {
			// defaultDatePattern = "MM/dd/yyyy";
			defaultDatePattern = "yyyy-MM-dd";
		}

		return defaultDatePattern;
	}

	/**
	 * 获取中国人的日期模式--added by liuxinxing 20100108
	 * 
	 * @param dateStr
	 * @return
	 */
	public static synchronized String getDatePattern(String dateStr) {
		String aMask = null;
		if (dateStr.contains("-")) {
			aMask = "yyyy-MM-dd";
		} else if (dateStr.contains("年") || dateStr.contains("月") || dateStr.contains("日")) {
			aMask = "yyyy年MM月dd日";
		} else if (dateStr.contains("/")) {
			aMask = "yyyy/MM/dd";
		} else {
			aMask = "yyyyMMdd";
		}
		return aMask;
	}

	public static String getDateTimePattern() {
		return DateUtil.getDatePattern() + " HH:mm:ss";
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	public static final String getDateTime() {
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat df = null;
		String returnValue = "";

		if (date != null) {
			df = new SimpleDateFormat(getDateTimePattern());
			returnValue = df.format(date);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		log.info("converting '" + strDate + "' to date with mask '" + aMask + "'");

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	public static String getYYYYMMDDHHMMSS(Date theTime) {
		return getDateTime(TIME_FORMAT, theTime);
	}

	public static Date getTimeNow() {
		return getDateTime(getDateTimePattern());
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	public static final Date getDateTime(String aMask) {
		SimpleDateFormat df = null;
		String returnValue = "";
		df = new SimpleDateFormat(aMask);
		returnValue = df.format(new Date());
		try {
			return df.parse(returnValue);
		} catch (ParseException e) {
			log.error(e);
			return new Date();
		}
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	public static final String convertDateTimeToString(Date aDate) {
		return getDateTime(getDateTimePattern(), aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + getDatePattern(strDate));
			}

			aDate = convertStringToDate(getDatePattern(strDate), strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate + "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}

		return aDate;
	}

	public static Date convertStringToDateTime(String strDate) throws ParseException {
		Date aDate = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + getDateTimePattern());
			}

			aDate = convertStringToDate(getDateTimePattern(), strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate + "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}

		return aDate;
	}

	/**
	 * 系统时间向后推20天
	 * 
	 * sunyuanhua
	 */

	public static String getEndTime() {
		java.sql.Timestamp date1 = new java.sql.Timestamp(System.currentTimeMillis() + (20 * 24 * 3600 * 1000));
		String endTime = date1.toString();
		return endTime.substring(0, 19);
	}

	/**
	 * 比较指定时间与系统时间相差的天数
	 * 
	 * @param date指定时间
	 *            ("yyyy-mm-dd" 形式)
	 * 
	 */
	public static int compareTime(String date) {
		Date dates;
		int i = 0;
		try {
			dates = DateUtil.convertStringToDate(date);
			i = (int) ((dates.getTime() - System.currentTimeMillis()) / 24 / 3600 / 1000);
		} catch (ParseException e) {
			log.error("转换日期出错");
			e.printStackTrace();
		}
		return i;
	}

	public static Date getDateBegin(String dateStr) {
		return getDateBegin(dateStr, DateUtil.getDateTimePattern());
	}

	public static Date getDateBegin(String dateStr, String dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		try {
			return df.parse(dateStr + " 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static Date getDateEnd(String dateStr) {
		return getDateEnd(dateStr, DateUtil.getDateTimePattern());
	}

	public static Date getDateEnd(String dateStr, String dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		try {
			return df.parse(dateStr + " 23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将日期格式的字符串转换为长整型
	 * 
	 * @title : convert2long
	 * @description :
	 * @param : @param date
	 * @param : @param format
	 * @param : @return
	 * @return : long
	 * @throws
	 */
	public static long convertDateString2Long(String date, String format) {
		try {
			if (StringUtil.isNullOrEmpty(date)) {
				if (StringUtil.isNullOrEmpty(format))
					format = DateUtil.TIME_FORMAT;

				SimpleDateFormat sf = new SimpleDateFormat(format);
				return sf.parse(date).getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0l;
	}

	/**
	 * 将长整型数字转换为日期格式的字符串
	 * 
	 * @title : convert2String
	 * @description :
	 * @param : @param time
	 * @param : @param format
	 * @param : @return
	 * @return : String
	 * @throws
	 */
	public static String convertLong2DateString(long time, String format) {
		if (time > 0l) {
			if (StringUtil.isNullOrEmpty(format))
				format = DateUtil.TIME_FORMAT;

			Date d = new Date(time);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			return sf.format(d);
		}

		return "";
	}

	/**
	 * 获取当前系统的日期,返回long型毫秒数
	 * 
	 * @title : curTimeMillis
	 * @description :
	 * @param : @return
	 * @return : long
	 * @throws
	 */
	/**
	 * 备注by zhangmuqing：该函数在菜篮子项目使用时，需要先减去8小时。 curTimeMillis()/1000 - 8*3600
	 * 得出之后再与 ec_order_info的add_time做比较
	 */
	public static long curTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 闰年判断 输入一个年份，如果是闰年就返回true,否则为false
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isYear(int year) {
		boolean flag = false;
		if ((year % 4 == 0 && year % 100 != 0) || (year % 4 == 0 && year % 400 == 0)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 得到指定日期的月的最后一天 输入一个字符时期(yyyy-MM/yyyy-MM-dd),返回月的最后一天
	 * 
	 * @param str
	 * @return
	 */
	public static String dateStr(String str) {
		String[] strArray = str.split("-");
		String year = strArray[0];
		String month = strArray[1];
		String day = "";
		if (strArray.length == 2) {
			switch (Integer.parseInt(month)) {
			case 1:
				day = "31";
				break;
			case 2:
				boolean flag = isYear(Integer.parseInt(year));
				if (flag) {
					day = "29";
					break;
				} else {
					day = "28";
					break;
				}
			case 3:
				day = "31";
				break;
			case 4:
				day = "30";
				break;
			case 5:
				day = "31";
				break;
			case 6:
				day = "30";
				break;
			case 7:
				day = "31";
				break;
			case 8:
				day = "31";
			case 9:
				day = "30";
				break;
			case 10:
				day = "31";
				break;
			case 11:
				day = "30";
				break;
			case 12:
				day = "31";
				break;
			}
		} else {
			return str;
		}
		String dateStr = year + "-" + month + "-" + day;
		return dateStr;
	}

	/**
	 * 判断时间
	 */
	public static int dataCompareTo(String d1, String d2) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date1 = format.parse(d1);
			Date date2 = format.parse(d2);
			if (date1.getTime() > date2.getTime()) {
				return 1;
			} else if (date1.getTime() == date2.getTime()) {
				return 0;
			} else {
				return -1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -2;
	}

	/**
	 * 判断时间
	 */
	public static int dataCompareTo(Date date1, Date date2) {
		try {
			if (date1.getTime() > date2.getTime()) {
				return 1;
			} else if (date1.getTime() == date2.getTime()) {
				return 0;
			} else {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2;
	}

	/**
	 * 获取当前时间的左右时间
	 * 
	 * @param formatStr
	 * @param day
	 *            前移 还是后退
	 */
	public static String getNowDate(String formatStr, int day) {
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
			SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
			String dateString = formatter.format(calendar.getTime());// 这个时间就是日期往后推一天的结果
			return dateString;
		} catch (Exception e) {
			return getDateTime(formatStr, new Date());// 当前时间
		}
	}
	
	// 取上个月的日期,格式如2016-02
	public static String getLastMonthDateYYYYMM() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH); // 上个月的,就不用+1了

		String YYYY = year + "";
		String MM = month >= 10 ? "" + month : "0" + month;

		return YYYY + "-" + MM;
	}
	
}
