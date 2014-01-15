package com.synesoft.fisp.app.common.utils.encryp;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @file 	SHAEncrypt.java
 * @author 	michelle.wang
 * @date 	2013-12-3
 * @description TODO
 * @tag 	1.0.0
 *
 */
public class SHAEncrypt {
	
	private static Logger logger = LoggerFactory.getLogger(SHAEncrypt.class);
	
	/**
	 * SHA1加密主程序
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public final static String stringToSHA1(String s) {
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
			MessageDigest sha1Temp = MessageDigest.getInstance("SHA-1");
			sha1Temp.update(strTemp);
			byte[] md = sha1Temp.digest();
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
			System.out.println(stringToSHA1("admin"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
