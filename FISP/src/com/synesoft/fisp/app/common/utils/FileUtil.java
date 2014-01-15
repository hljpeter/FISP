/**
 * 
 */
package com.synesoft.fisp.app.common.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Huanglejun
 * @date 2013-4-18
 * @description delete the file 
 * 
 */
public class FileUtil {
	
	private static Logger logger  = LoggerFactory.getLogger(FileUtil.class);
	
	public static boolean delFile(String fileName) {
		boolean flag = false;
		if (fileName == null) {
			logger.error("Input fileName is null!");
			return flag;
		}
		File file = new File(fileName);
		if (!file.exists()) {
			logger.error("File does not exist!");
			return flag;
		}
		if (file.delete()) {
			flag = true;
		}
		return flag;
	}
	
}
