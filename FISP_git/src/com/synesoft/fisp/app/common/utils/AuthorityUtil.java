package com.synesoft.fisp.app.common.utils;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;

/**
 * @author zhongHubo
 * @date 2013年12月2日 19:07:07
 * @version 1.0
 * @Description 加载Authority属性文件
 * @System FISP
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class AuthorityUtil {

	/** 属性文件路径 **/
	private static final String filePath = "Authority.properties";
	private static Properties prop;
	private static String keyStart = "authorized.check.path.";
	
	static {
        try {
        	prop = new Properties();
        	InputStream in = AuthorityUtil.class.getClassLoader().getResourceAsStream(filePath);
        	if (in != null) {
        		prop.load(in);
        	}
        } catch (Exception e) {
        	throw new RuntimeException("load Authority.properties fail.");
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
	
	/**
	 * 根据url获取属性配置文件中的功能权限编号
	 * @param url
	 * @return
	 */
	public static String getAuthorityByUrl(String url) {
		return getPropertiesValue(AuthorityUtil.keyStart + url);
	}
	
	/**
	 * 判断当前用户是否有当前请求的功能权限
	 * @param funcIds	功能权限编号(可能有多个)
	 * @return			true:有；false:无
	 */
	public static boolean judgeFuncAuthority(String funcIds) {
		boolean result = false;
		// 获取当前用户的功能权限
		Object obj = ContextConst.getAttribute(CommonConst.USERFUNC_SESSION);
		if (obj != null) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map_authority = (Map<String, Object>) obj;
			String[] funcId_array = funcIds.split(CommonConst.comb_unseperator);
			for (int i = 0; i < funcId_array.length; i++) {
				// 只要当前用户有其中某一个功能权限编号的权限即可
				String funcId = funcId_array[i];
				result = map_authority.containsKey(funcId);
				if (result) {
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * 判断当前用户是否有当前请求的权限
	 * @param url		请求
	 * @return			true:有；false:无
	 */
	public static boolean judgeUrlAuthority(String url) {
		boolean result = true;
		String funcIds = AuthorityUtil.getAuthorityByUrl(url);
		// 非空才进行权限验证
		if (StringUtil.isNotTrimEmpty(funcIds)) {
			result = AuthorityUtil.judgeFuncAuthority(funcIds);
		}
		return result;
	}
	
}
