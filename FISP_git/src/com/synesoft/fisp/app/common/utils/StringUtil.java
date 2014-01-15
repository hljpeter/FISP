package com.synesoft.fisp.app.common.utils;

import java.io.UnsupportedEncodingException;
/**
 * Common String Util
 * @author zhaoliguo
 */
public class StringUtil {
	
	@SuppressWarnings("unused")
	private static LogUtil log  = new LogUtil(StringUtil.class);
	
	/**
	 * 判断一个字符串是否不为空
	 * @param param
	 * @return
	 */
	public static boolean isNotEmpty(String param){
		if(param == null || param.length() == 0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 判断一个字符串trim后是否不为空
	 * @param param
	 * @return
	 */
	public static boolean isNotTrimEmpty(String param){
		if(param == null || param.trim().length() == 0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * String trim
	 */
	public final static String trim(String string){
		if(string == null){
			return "";
		}else{
			return string.trim();
		}
	}
	
	/**
	 * string is blank 
	 * add by Jon 20130417
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return (str == null || str.trim().equals("") || str.trim().length() == 0) ? true
				: false;
	}
	
	/**
	 * fill string from left 
	 * add by Jon 20130417
	 * @param str
	 * @param length
	 * @param fillChar
	 * @return
	 */
	public static String formatFillLeft(String str, int length, char fillChar) {
		if (isBlank(str)) {
			str = "";
		}
		if (length <= 0) {
			return str;
		}
		int strLen = str.getBytes().length;
		if (strLen > length) {
			String tmp = new String(str.getBytes(), strLen - length, length);
			if (tmp.getBytes().length == length) {
				return tmp;
			}
			str = tmp;
		}
		StringBuilder sb = new StringBuilder(length);
		sb.append(str);
		while (sb.toString().getBytes().length < length) {
			sb.insert(0, fillChar);
		}
		return sb.toString();
	}
	
	/**
	 * convert from char to dest char 
	 * @param str
	 * @return
	 */
	public static String starToLike(String str, String fromChar, String toChar){
		if(isBlank(str)){
			return "";
		}
		if(str.startsWith(fromChar) && 
				!str.endsWith(fromChar)){
			str = str.substring(1);
			str = " like '" + toChar + str + "' ";
		}else if(!str.startsWith(fromChar) && 
				str.endsWith(fromChar)){
			str = str.substring(0, str.length()-1);
			str = " like '" + str + toChar + "' ";
		}else if(str.startsWith(fromChar) && 
				str.endsWith(fromChar)){
			str = str.substring(1, str.length()-1);
			str = " like '"+ toChar + str + toChar +"' ";
		}else{
			str = " = '"+str+"' ";
		}
		return str;
	}
	
	/**
	 * reback str from char to dest char
	 * @param str
	 * @param fromChar
	 * @param destChar
	 * @return
	 */
	public static String rebackStr(String str,String fromChar,String destChar){
		if(isBlank(str)){
			return "";
		}
		if(str.startsWith(fromChar) && 
				!str.endsWith(fromChar)){
			str = str.substring(1);
			str = destChar + str;
		}else if(!str.startsWith(fromChar) && 
				str.endsWith(fromChar)){
			str = str.substring(0, str.length()-1);
			str = str + destChar;
		}else if(str.startsWith(fromChar) && 
				str.endsWith(fromChar)){
			str = str.substring(1, str.length()-1);
			str = destChar + str + destChar;
		}
		return str;
	}
	
	public static String addZeroForNum(String str,int len) {
	     int strLen = str.length();
	     StringBuffer sb = null;
	     while (strLen < len) {
	           sb = new StringBuffer();
	           sb.append("0").append(str);// 左(前)补0
	        // sb.append(str).append("0");//右(后)补0
	           str = sb.toString();
	           strLen = str.length();
	     }
	     return str;
	 }	
	
	   /**
	 * 获取字符的长度
	 * @param s	字符
	 * @return	字符长度
	 */
	public static int getStrLength(String s) {
		byte[] bytes;
		int n = 0; // 表示当前的字节数
		try {
			bytes = s.getBytes("Unicode");
			int i = 2; // 要截取的字节数，从第3个字节开始
			for (; i < bytes.length; i++) {
				// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
				if (i % 2 == 1) {
					n++; // 在UCS2第二个字节时n加1
				} else {
					// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
					if (bytes[i] != 0) {
						n++;
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error(e, e);
		}
		return n;
	} 
}
