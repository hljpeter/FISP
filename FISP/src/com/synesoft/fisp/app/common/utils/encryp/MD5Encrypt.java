package com.synesoft.fisp.app.common.utils.encryp;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @file 	MD5Encrypt.java
 * @author 	Jon
 * @date 	2013-4-14
 * @description TODO
 * @tag 	1.0.0
 *
 */
public class MD5Encrypt {
	
	private static Logger logger = LoggerFactory.getLogger(MD5Encrypt.class);
	
	/**
	 * MD5加密主程序
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public final static String stringToMD5(String s) {
		String retStr = "";
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f','g','h','i','j','k','l','m','n',
				'o','p','q','r','s','t','u','v','w','x','y','z','A','B','C',
				'D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
				'S','T','U','V','W','X','Y','Z','~','`','!','@','#','$','%',
				'^','&','*','(',')','{','}','[',']','|','\\','"','\'',':'
				,';','<','>',',','.','?','/',' '};
		try {
			byte[] strTemp = s.getBytes();
			// 
			MessageDigest md5Temp = MessageDigest.getInstance("MD5");
			md5Temp.update(strTemp);
			byte[] md = md5Temp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				// 
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			retStr = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return retStr ;
	}
	public static void main(String[] args) {
		try {
			System.out.println(stringToMD5("wy"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
