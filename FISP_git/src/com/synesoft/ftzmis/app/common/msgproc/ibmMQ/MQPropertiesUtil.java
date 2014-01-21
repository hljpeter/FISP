package com.synesoft.ftzmis.app.common.msgproc.ibmMQ;

import java.io.InputStream;
import java.util.Properties;

import com.synesoft.fisp.app.common.utils.StringUtil;

public class MQPropertiesUtil {

	/** 属性文件路径 **/
	private static final String filePath = "MQComm.properties";
	private static Properties prop;
	
	static {
        try {
        	prop = new Properties();
        	InputStream in = MQPropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
        	if (in != null) {
        		prop.load(in);
        	}
        } catch (Exception e) {
        	throw new RuntimeException("load DataProcResources.properties fail.");
        }
    }
	
	/**
	 * 根据key获取属性配置文件中的value
	 * @param key
	 * @return
	 */
	public static String getPropertiesValue(String key) {
		String value = null;
		
		// 根据key获取value
		if (StringUtil.isNotTrimEmpty(key)) {
			if (prop != null) {
				value = prop.getProperty(key.trim());
				if (StringUtil.isNotTrimEmpty(value)) {
					value = value.trim();
				}
			}
		}
		return value;
	}
}
