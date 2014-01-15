package com.synesoft.ftzmis.app.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static final String DATE_FORMAT_OUTPUT_PAGE = "yyyy-MM-dd";

	public static final String DATE_FORMAT_INPUT_PAGE = "yyyyMMdd";
	
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static final String DATE_FORMAT_HHMMSS = "HHmmss";
	
	public static String getNowInputDateTime() {
		return timeToString(DATE_FORMAT_YYYYMMDDHHMMSS, new Date());
	}
	
	public static String getNowInputDate() {
		return timeToString(DATE_FORMAT_INPUT_PAGE, new Date());
	}

	public static String getNowInputTime() {
		return timeToString(DATE_FORMAT_HHMMSS, new Date());
	}

	public static String getNowOutputDate() {
		return timeToString(DATE_FORMAT_OUTPUT_PAGE, new Date());
	}
	
	public static String timeToString(String format, Date date) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	
	public final static String getFormatDateRemoveSprit(String date) {
		if (date == null) {
			return null;
		} else {
			return date.replace("-", "");
		}
	}
	
	public final static String getFormatDateAddSprit(String date) {
		if (date == null || date.trim().length() == 0) {
			return date;
		} else if (date.length() == 8) {
			return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
					+ date.substring(6, 8) ;
		}
		return date;
	}

	public final static String getFormatDateTimeAddSpritAndColon(String date) {
		if (date == null || date.trim().length() == 0) {
			return date;
		} else if (date.length() == 14) {
			return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
					+ date.substring(6, 8) + " " + date.substring(8, 10) + ":"
					+ date.substring(10, 12) + ":" + date.substring(12, 14);
		}
		return date;
	}
	
	public final static String getFormatDateTimeRemoveSpritAndColon(String date) {
		if (date == null) {
			return null;
		} else {
			date = date.replace("-", "");
			date = date.replace(" ", "");
			date = date.replace(":", "");
			return date;
		}
	}
	
	public static String getFormatTimeRemoveColon(String time) {
		if (time == null || time.trim().length() == 0) {
			return null;
		} else {
			return time.replace(":", "");
		}
	}

	public static String getFormatTimeAddColon(String time) {
		if (time == null || time.trim().length() == 0) {
			return null;
		} else {
			return time.substring(0, 2) + ":" + time.substring(2, 4) + ":"
					+ time.substring(4, 6);
		}
	}

	/**
	 * 比较两个时间的大小
	 * @param date1 - 时间1
	 * @param date2 - 时间2
	 * @param pattern - 时间格式
	 * @return 
	 * <p>&nbsp;&nbsp;1:date1>date2</p>
	 * <p>&nbsp;&nbsp;-1:date1<date2</p>
	 * <p>&nbsp;&nbsp;0:date1=date2</p>
	 * <p>&nbsp;&nbsp;-9999:异常</p>
	 */
	public static int compareDate(String date1, String date2, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			Date d1 = df.parse(date1);
			Date d2 = df.parse(date2);
			if (d1.getTime() > d2.getTime()) {
				return 1;
			} else if (d1.getTime() < d2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return -9999;
	}

}
