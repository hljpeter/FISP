package com.synesoft.sysrunner.common.util;

public class StringUtil {

	
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
}
