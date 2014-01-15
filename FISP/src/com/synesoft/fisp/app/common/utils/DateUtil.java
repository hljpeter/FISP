/**
 * DateUtil.java
 * 
 * Copyright 2011-2012 Mizuho Corporate Bank(China), Ltd. and Shanghai NTT DATA Synergy Software Co., Ltd. All Rights Reserved.
 * 
 * $Id: DateUtil.java,v 1.1 2012/08/30 03:45:00 zhb Exp $
 */
package com.synesoft.fisp.app.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * description:
 * 
 * @throws
 * @author dhj
 * @version 1.0 2011 2011-12-26
 * 
 */
public class DateUtil {

	public static final String JAPAN = "JST";
	public static final String CHINA = "PRC";
	public static final String GMT = "GMT";
	public static final String LOCAL_TIMEZONE_KEY = "local.timezone";
	public static final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String DATE_FORMAT_HHMMSS = "HHmmss";
	public static final String DATE_FORMAT_YYYYMM = "yyyyMM";
	public static final String SHORT_TIME_FORMAT_TIME = "HHmmss";
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	/** 鏃ユ湡绫诲瀷: yyyy-MM-dd */
	public static final String DATE_PATTEN = "yyyy-MM-dd";
	public static final String MONTH_PATTEN = "yyyy-MM";
	public static final String SHORT_TIME_PATTEN = "HH:mm:ss";
	public static final String DATE_FORMAT_OUTPUT_PAGE = "yyyy/MM/dd";

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
	 * get work day format : yyyy/MM/dd
	 * 
	 * @return
	 */
	public static String getWorkDate() {
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

	/**
	 * 鑾峰彇鏈�皬鏃堕棿
	 * 
	 * @return 1900-01-01 00:00:00 000
	 */
	public static Date getMiniTime() {
		try {
			return stringToTime("yyyy-MM-dd HH:mm:ss", "1900-01-01 00:00:00");
		} catch (Exception ex) {
			return new Date(0);
		}
	}

	/**
	 * 鑾峰彇褰撳墠鏃堕棿
	 * 
	 * @param format
	 * @return
	 */
	public static String getNow(String format) {
		return timeToString(format, new Date());
	}

	/**
	 * 鏃ユ湡绫诲瀷杞垚瀛楃涓�
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
	 * 瀛楃涓茶浆鏃ユ湡
	 * 
	 * @param format
	 * @param sDate
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToTime(String format, String sDate)
			throws Exception {
		DateFormat df = new SimpleDateFormat(format);
		Date _date = df.parse(sDate);
		if (timeToString(format, _date).equals(sDate)) {
			return _date;
		} else {
			throw new Exception(sDate + " is error");
		}
	}

	/**
	 * 杞崲鏃ユ湡鏍煎紡 yyyy-MM-dd => yyyyMMdd
	 * 
	 * @param dt
	 *            yyyy-MM-dd
	 * @return yyyyMMdd
	 */
	public static String transfer2ShortDate(String dt) {
		if (dt == null || dt.length() != 10) {
			return dt;
		}
		String[] tmp = StringUtils.split(dt, DATE_PATTEN);
		return tmp[0].concat(StringUtils.leftPad(tmp[1], 2, "0")).concat(
				StringUtils.leftPad(tmp[2], 2, "0"));
	}

	/***
	 * 姣鏃堕棿瀛楃涓茶浆鏃堕棿
	 * 
	 * @param time
	 * @return
	 */
	public static String timeToLongStr(Date time) {
		return "" + time.getTime();
	}

	/***
	 * 鏃堕棿杞绉掓椂闂村瓧绗︿覆
	 * 
	 * @param time
	 * @return
	 */
	public static Date longStrToTime(String time) {
		return new Date(Long.parseLong(time));
	}

	/**
	 * 鑾峰彇褰撳墠鏃堕棿鐨勫悇涓儴鍒�
	 * 
	 * @return 
	 *         string[0]:yyyyMMdd,string[1]:HHmm,string[2]:week,string[3]:season,
	 *         string[4]:yyyyMMddHHmmss
	 */
	public static String[] getDatePart() {
		Date now = new Date();
		String[] weeks = new String[] { "SUNDAY", "MONDAY", "TUESDAY",
				"WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
		String[] season = new String[] { "1", "1", "1", "2", "2", "2", "3",
				"3", "3", "4", "4", "4" };
		String sdate = timeToString(DATE_FORMAT_YYYYMMDDHHMMSS, now);
		if (sdate.length() == 14) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(now);
			return new String[] { sdate.substring(0, 8),
					sdate.substring(8, 12),
					weeks[cal.get(Calendar.DAY_OF_WEEK) - 1],
					season[cal.get(Calendar.MONTH)], sdate };
		} else {
			return null;
		}
	}

	/***
	 * 鑾峰彇鏃堕棿閮ㄥ垎
	 * 
	 * @param date
	 *            锛�yyyyMMdd"
	 * @return 杈撳嚭锛�:骞达紝1:鏈堬紝2:鏃ワ紝3:鏄熸湡
	 */
	public static String[] getPartDate(String date) throws Exception {
		String[] weeks = new String[] { "SUNDAY", "MONDAY", "TUESDAY",
				"WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
		if (date.length() == 8) {
			Date input = stringToTime("yyyyMMdd", date);
			Calendar cal = new GregorianCalendar();
			cal.setTime(input);
			return new String[] { date.substring(0, 4), date.substring(4, 6),
					date.substring(6, 8),
					weeks[cal.get(Calendar.DAY_OF_WEEK) - 1] };
		} else {
			return null;
		}
	}

	/**
	 * 寰楀埌涓�勾涓墍鏈夌殑鏃ユ湡,杈撳叆骞翠唤鏍煎紡yyyy
	 * 
	 * @return 杩斿洖鏃ユ湡鏍煎紡涓簓yyyMMdd
	 */
	public static String[] getDays(String yyyy) throws Exception {

		StringBuilder sb = new StringBuilder();
		long starttime = stringToTime("yyyyMMdd", yyyy + "0101").getTime();
		long lasttime = stringToTime("yyyyMMdd", yyyy + "1231").getTime();
		for (; starttime <= lasttime; starttime += 1000 * 3600 * 24) {
			sb.append(timeToString("yyyyMMdd", new Date(starttime)));
			sb.append(':');
		}
		return sb.toString().split(":");
	}

	/**
	 * 寰楀埌鎸囧畾鏃堕棿鐨刄TC鏃堕棿
	 * 
	 * @param aDate
	 *            鏃堕棿鎴�
	 * @return yyyy-MM-dd HH:mm:ss 鏍煎紡
	 */
	public static final String getUTCTime(Date aDate) {
		return getSpecifiedZoneTime(aDate, TimeZone.getTimeZone("GMT+0"));
	}

	/**
	 * 寰楀埌褰撳墠鏃堕棿鐨勬寚瀹氭椂鍖虹殑鏃堕棿
	 * 
	 * @param tz
	 * @return
	 */
	public static final String getSpecifiedZoneTime(TimeZone tz) {
		return getSpecifiedZoneTime(Calendar.getInstance().getTime(), tz);

	}

	/**
	 * 寰楀埌鎸囧畾鏃堕棿鐨勬寚瀹氭椂鍖虹殑鏃堕棿
	 * 
	 * @param aDate
	 *            鏃堕棿鎴�Date鏄竴涓灛闂寸殑long鍨嬭窛绂诲巻骞寸殑浣嶇Щ鍋忛噺锛�
	 *            鍦ㄤ笉鍚岀殑鎸囧畾鐨凩ocale/TimeZone鐨刯vm涓紝瀹僼oString鎴愪笉鍚岀殑鏄剧ず鍊硷紝
	 *            鎵�互娌℃湁蹇呰涓哄畠鍐嶆寚瀹氫竴涓猅imeZone鍙橀噺琛ㄧず鑾峰彇瀹冩椂鐨刯vm鐨凾imeZone
	 * 
	 * @param tz
	 *            瑕佽浆鎹㈡垚timezone
	 * 
	 * @return yyyy-MM-dd HH:mm:ss 鏍煎紡
	 */
	public static final String getSpecifiedZoneTime(Date aDate, TimeZone tz) {
		if (aDate == null)
			return StringUtils.EMPTY;
		SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE_FORMAT);
		sdf.setTimeZone(tz);
		return sdf.format(aDate);
	}

	/**
	 * 妫�煡鏃堕棿鏍煎紡鏄惁涓猴細yyyyMMdd
	 * 
	 * @param date
	 * @return true:鏄�false:鍚�
	 */
	public static final Boolean checkDate(String date) {
		if (null == date || ("").equals(date)) {
			return false;
		}

		if (date.length() != 8) {
			return false;
		}

		// 涓や釜妫�獙鐨勬鍒�
		String pattern1 = "\\d{4}\\d{2}\\d{2}";
		String pattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\\\/\\s]?((((0?[13578])|(1[02]))[\\\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

		if (date != null) {
			Pattern pattern = Pattern.compile(pattern1);
			Matcher match = pattern.matcher(date);
			if (match.matches()) {
				pattern = Pattern.compile(pattern2);
				match = pattern.matcher(date);
				return match.matches();
			}
		}

		return false;
	}

	/**
	 * 鑾峰彇鏌愪釜鏃堕棿鐩稿樊d澶╃殑鏃堕棿
	 * 
	 * @param intertime
	 *            鍙傜収鏃堕棿
	 * @param d
	 *            (d)琛ㄧずd澶╀箣鍚庯紝(-d)琛ㄧずd澶╀箣鍓�
	 * @param pattern
	 *            鏃堕棿鐨勬牸寮忥紝渚嬪锛歽yyy-MM-dd HH:mm:ss鎴杫yyyMMddHHmmss绛�
	 * @return
	 */
	public static String getDuedate(String intertime, int d, String pattern) {
		String due_date = "";

		DateFormat df = new SimpleDateFormat(pattern);
		Calendar c_intertime = Calendar.getInstance();
		try {
			c_intertime.setTime(df.parse(intertime));
			c_intertime.add(Calendar.DATE, d); // 璁剧疆intertime鐩稿樊d澶╃殑鏃堕棿
			due_date = df.format(c_intertime.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return due_date;
	}

	/**
	 * 姣旇緝涓や釜鏃堕棿鐨勫ぇ灏�
	 * 
	 * @param date1
	 *            姣旇緝鏃堕棿
	 * @param date2
	 *            鍙傜収鏃堕棿
	 * @param pattern
	 *            鏃堕棿鐨勬牸寮忥紝渚嬪锛歽yyy-MM-dd HH:mm:ss鎴杫yyyMMddHHmmss绛�
	 * @return 1:date1>date2锛�-1:date1<date2锛�0:date1=date2锛�-9999:鍑虹幇寮傚父
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

	/**
	 * 涓庡綋鍓嶆椂闂存瘮杈冨ぇ灏�
	 * 
	 * @param date
	 *            姣旇緝鏃堕棿
	 * @param pattern
	 *            鏃堕棿鐨勬牸寮忥紝渚嬪锛歽yyy-MM-dd HH:mm:ss鎴杫yyyMMddHHmmss绛�
	 * @return 1:date>褰撳墠鏃堕棿锛�-1:date<褰撳墠鏃堕棿锛�0:date=褰撳墠鏃堕棿锛�-9999:鍑虹幇寮傚父
	 */
	public static int compareDate(String date, String pattern) {
		return DateUtil.compareDate(date, DateUtil.getNow(pattern), pattern);
	}

	/**
	 * <p>
	 * 瀛楃涓叉牸寮忕殑鏃ユ湡杞崲鎴愬甫鏍煎紡鐨勫瓧绗︿覆鏃ユ湡
	 * </p>
	 * <p>
	 * 鏍规嵁鏃ユ湡瀛楃涓查暱搴﹁浆鎹㈡垚鐩稿簲鐨勬牸寮忓瓧绗︿覆
	 * </p>
	 * 
	 * @param dateString
	 *            yyyyMMddHHmmss
	 * @return
	 */
	public static String formatStringToDatePattern(String dateString) {
		String retDateStr = "";
		if (dateString != null && dateString.trim().length() != 0) {
			if (dateString.trim().length() == 14) {
				// 鏄剧ず鍒扮
				retDateStr = dateString.substring(0, 4) + "-"
						+ dateString.substring(4, 6) + "-"
						+ dateString.substring(6, 8) + " "
						+ dateString.substring(8, 10) + ":"
						+ dateString.substring(10, 12) + ":"
						+ dateString.substring(12, 14);
			} else if (dateString.trim().length() == 12) {
				// 鏄剧ず鍒板垎閽�
				retDateStr = dateString.substring(0, 4) + "-"
						+ dateString.substring(4, 6) + "-"
						+ dateString.substring(6, 8) + " "
						+ dateString.substring(8, 10) + ":"
						+ dateString.substring(10, 12);
			} else if (dateString.trim().length() == 10) {
				// 鏄剧ず鍒板皬鏃�
				retDateStr = dateString.substring(0, 4) + "-"
						+ dateString.substring(4, 6) + "-"
						+ dateString.substring(6, 8) + " "
						+ dateString.substring(8, 10);
			} else if (dateString.trim().length() == 8) {
				// 鏄剧ず鍒版棩
				retDateStr = dateString.substring(0, 4) + "-"
						+ dateString.substring(4, 6) + "-"
						+ dateString.substring(6, 8);
			} else if (dateString.trim().length() == 6) {
				// 鏄剧ず鍒版湀
				retDateStr = dateString.substring(0, 4) + "-"
						+ dateString.substring(4, 6);
			}
		}
		return retDateStr;
	}

	// 鑾峰彇褰撴湀鐨勬渶鍚庝竴澶�
	public static String lastDayOfMonth(String batdate) {
		Calendar cal = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMMdd").parse(batdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		date = cal.getTime();
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	/**
	 * 鑾峰彇涓や釜鏈堜唤鐨勭浉宸湀浠�<br>
	 * 鏈堜唤鐨勬牸寮忎负yyyyMM <br>
	 * 寮傚父鎯呭喌杩斿洖0 <br>
	 * 渚嬶細start涓�01302,end涓�01304,杩斿洖3 <br>
	 * <b>浣滆�锛氭潹鍔查泟</> <br>
	 * 鍒涘缓鏃堕棿锛欰pr 18, 2013 2:22:40 PM
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getMonthRange(String start, String end) {
		int range = 0;
		try {
			String yearEnd = end.substring(0, 4);
			String monthEnd = end.substring(4);
			String yearStart = start.substring(0, 4);
			String monthStart = start.substring(4);

			range = (Integer.parseInt(yearEnd) - Integer.parseInt(yearStart))
					* 12 + Integer.parseInt(monthEnd)
					- Integer.parseInt(monthStart) + 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return range;
	}

	/**
	 * 鑾峰彇鏃堕棿鍖洪棿鐨勭涓�ぉ <br>
	 * 浼犲叆鐨勬棩鏈熷瓧绗︿覆鏍煎紡鏄痽yyyMM <br>
	 * <b>浣滆�锛氭潹鍔查泟</> <br>
	 * 鍒涘缓鏃堕棿锛欰pr 18, 2013 9:39:47 PM
	 * 
	 * @param yearMonthStr
	 * @param range
	 * @return
	 */
	public static String getFirstDay(String yearMonthStr, int range) {
		try {
			DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = sdf.parse(yearMonthStr + "01");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, -(range - 1));
			Date first = calendar.getTime();
			return sdf.format(first);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 获取某个时间相差d天的时间
	 * @param intertime
	 * @param d	(d)表示d天之后，(-d)表示d天之前
	 * @param pattern 时间的格式
	 * @return
	 */
	public static String getDatetimeByD(String intertime, int d, String pattern) {
		String due_date = "";

		DateFormat df=new SimpleDateFormat(pattern); 
		Calendar c_intertime = Calendar.getInstance();       
		try {
			c_intertime.setTime(df.parse(intertime));
			c_intertime.add(Calendar.DATE, d);    //设置intertime相差d天的时间
			due_date = df.format(c_intertime.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return due_date;
	}
}
