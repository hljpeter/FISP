/**
 * 
 */
package com.synesoft.fisp.app.common.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 文件名规则校验
 * @author yyw
 *
 */
public class FileNameRuleValidator {

	private static final Log log = LogFactory.getLog(FileNameRuleValidator.class);
	
	private static final char CHAR_PARCENT = '%';
	
	private static final char CHAR_QUESTION = '?';

	private static final char CHAR_ASTERISK = '*';
	
	public static boolean checkName(String fileName) {
		log.info("[valid1101] To verify the file name [" + fileName + "].");

		//String fileName = "cust_%yyyy%%mmdd%_%yyyy-mm-dd%%yyyymmdd%%YYYYMMDD%%mm%%mmd%%ymd%%dd%%yyyydd%%yyyymm-dd%";
		
		if (fileName.indexOf(CHAR_PARCENT) < 0 && fileName.indexOf(CHAR_QUESTION) < 0 
				&& fileName.indexOf(CHAR_ASTERISK) < 0 ) {
			log.info("[valid1102] The file name cannot contain three wildcards.");
			return true;
		} else {
			// 文件不能以通配符开头
			if (fileName.indexOf(CHAR_PARCENT) == 0 || fileName.indexOf(CHAR_QUESTION) == 0 
					|| fileName.indexOf(CHAR_ASTERISK) == 0 ) {
				log.error("[valid1001] The file name cannot be beginning with wildcards.");
				return false;
			}
			
			// 如果存在%，必须前后两个,不能连续两个
			char[] cs = fileName.toCharArray();
			int j = 0;
			for (int i = 0; i < cs.length; i++) {
				if (cs[i] == CHAR_PARCENT) {
					j++;
				}
			}
			if (j % 2 != 0) {
				log.error("[valid1002] The file name cannot be contain many couple of parcent when using parcent.");
				return false;
			}
			
			if (j > 0) {
				log.info("[valid1103] To verify wildcard including the file name.");
				//String regx = "yyyy?-?((mm)?-?(dd)?)?|(mm)?(dd)?";
				String regx = "yyyymmdd|yyyy-mm-dd|yyyymm|yyyy-mm|mmdd|mm-dd|yyyy|mm|dd|n";
				Pattern p = Pattern.compile(regx);      
				Matcher m = null;
				
				// 获得两个%间的字符串
				String[] rs = getFormatString(fileName, CHAR_PARCENT, j);
				for (int i = 0; i < rs.length; i++) {
					if (null == rs[i] || ("").equals(rs[i].trim())) {
						continue;
					}
					m = p.matcher(rs[i].toLowerCase());
					// %间不能包含*和？
					if (rs[i].indexOf(CHAR_ASTERISK) >= 0 || rs[i].indexOf(CHAR_QUESTION) >= 0) {
						log.error("[valid1003] Wildcards cannot contain asterisk(*) or question(?).");
						return false;
					}
					if (!m.matches()) {
						log.error("[valid1004] Wildcards cannot be match the rule.");
						return false;
					}
				}
			}
		}
		
		log.info("[valid1104] The verification for file name was completed and success.");
		return true;
	}

	private static String[] getFormatString(String arg, char delimiter, int total) {
		String[] result = new String[total / 2];
		String temp = arg;
		int idx = temp.indexOf(delimiter);
		String subStr = null;
		int i = 0;
		do {
			subStr = temp.substring(idx + 1);
			String rstr = temp.substring(0, idx);
			if (i > 0 && i % 2 != 0) {
				result[i / 2] = rstr;
			}
			temp = subStr;
			idx = temp.indexOf(delimiter);
			i++;
		} while(idx >= 0);
		
		return result;
	}
}
