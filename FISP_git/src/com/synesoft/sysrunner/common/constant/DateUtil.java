/**
 * 
 */
package com.synesoft.sysrunner.common.constant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Huanglejun
 * @date 2013-4-22
 * @description TODO
 * 
 * 
 */
public class DateUtil {

	public static final String DATE_FORMAT_OUTPUT_PAGE = "yyyy-MM-dd";

	public static final String DATE_FORMAT_INPUT_PAGE = "yyyyMMdd";

	/**
	 * get now day format : yyyy/MM/dd
	 * 
	 * @return
	 */
	public static String getNowOutputDate() {
		return timeToString(DATE_FORMAT_OUTPUT_PAGE, new Date());
	}

	/**
	 * get now day format : yyyyMMdd
	 * 
	 * @return
	 */
	public static String getNowInputDate() {
		return timeToString(DATE_FORMAT_INPUT_PAGE, new Date());
	}

	/**
	 * add by zhaoliguo format date to string by input pattern
	 * 
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String getDateToString(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String returnDate = format.format(date);
		return returnDate;
	}

	/**
	 * add by zhaoliguo format current date to string by input pattern
	 * 
	 * @param pattern
	 * @return String
	 */
	public static String getCurrentDateToString(String pattern) {
		Date date = new Date();
		return getDateToString(date, pattern);
	}

	/**
	 * date --> string
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static String timeToString(String format, Date date) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	/**
	 * zhaoliguo format date 'yyyy/MM/dd' to 'yyyyMMdd'
	 */
	public final static String getFormatDateRemoveSprit(String date) {
		if (date == null) {
			return "";
		} else {
			return date.replace("-", "");
		}
	}

	/**
	 * @author Huanglejun 2013-4-24
	 * @param String
	 * @return String format yyyy/MM/dd HH:mm:ss to yyyymmddhhmmss
	 * 
	 */
	public final static String getFormatDateRemoveSpritAndColon(String date) {
		if (date == null) {
			return "";
		} else {
			date = date.replace("-", "");
			date = date.replace(" ", "");
			date = date.replace(":", "");
			return date;
		}
	}

	/**
	 * compare dateString if date1 > date2,return true;else return false
	 */
	public final static boolean compareDateString(String date1, String date2) {
		boolean flag = false;
		if (Double.valueOf(date1) - Double.valueOf(date2) > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	public final static String getFormatdateAddSplit(String date) {
		if (date == null || date.trim().length() == 0) {
			return date;
		} else if (date.length() == 14) {
			return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
					+ date.substring(6, 8) + " " + date.substring(8, 10) + ":"
					+ date.substring(10, 12) + ":" + date.substring(12, 14);
		}
		return date;
	}
	
	public final static String getdateAddSplit(String date) {
		if (date == null || date.trim().length() == 0) {
			return date;
		} else if (date.length() == 8) {
			return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
					+ date.substring(6, 8) ;
		}
		return date;
	}

	public static void main(String[] args) {
		// System.out.println(getCurrentDateToString("HHmmss"));
		// System.out.println(getCurrentDateToString("HH:mm:ss"));
		// System.out.println(getCurrentDateToString("yyyyMMdd"));
		// System.out.println(getFormatDateAddSpritAndColon("20130101111111"));
		// System.out.println(getFormatDateRemoveSpritAndColon("2013/01/01 11:11:11"));
		System.out.println(compareDateString("20110320", "20110321"));
	}

	/**
	 * @param specifiedDay
	 * @return String
	 */
	public static String getSpecifiedDayAfter(String pattern,
			String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat(pattern).parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat(pattern).format(c.getTime());
		return dayAfter;
	}

	public static String getFormatTimeRemoveColon(String time) {
		if (time == null || time.trim().length() == 0) {
			return "";
		} else {
			return time.replace(":", "");
		}
	}

	public static String getFormatTimeAddColon(String time) {
		if (time == null || time.trim().length() == 0) {
			return "";
		} else {
			return time.substring(0, 2) + ":" + time.substring(2, 4) + ":"
					+ time.substring(4, 6);
		}
	}
}
