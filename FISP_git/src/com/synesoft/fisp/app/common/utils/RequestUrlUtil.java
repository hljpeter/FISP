package com.synesoft.fisp.app.common.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.SysOperLog;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.bm.OperatorLogService;

/**
 * @author zhongHubo
 * @date 2013年12月10日 20:49:53
 * @version 1.0
 * @Description 加载RequestUrl属性文件
 * @System FISP
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class RequestUrlUtil {

	/** 属性文件路径 **/
	private static final String filePath = "RequestUrl.properties";
	private static Properties prop;
	// 操作类型，O-打开菜单，Q-查询，A-新增，M-更新，D-删除，C-审核，R-拒绝，S-授权
	public static Map<String, String> map_keyStart = new HashMap<String, String>();
	
	private static OperatorLogService operatorLogService = (OperatorLogService) ContextUtil.getCtx().getBean("operatorLogService");
	
	static {
        try {
        	prop = new Properties();
        	InputStream in = RequestUrlUtil.class.getClassLoader().getResourceAsStream(filePath);
        	if (in != null) {
        		prop.load(in);
        	}
        } catch (Exception e) {
        	throw new RuntimeException("load RequestUrl.properties fail.");
        }
        
        map_keyStart.put("O", "打开菜单");
        map_keyStart.put("Q", "查询");
        map_keyStart.put("A", "新增");
        map_keyStart.put("M", "更新");
        map_keyStart.put("D", "删除");
        map_keyStart.put("C", "审核");
        map_keyStart.put("R", "拒绝");
        map_keyStart.put("S", "授权");
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
	 * 记录操作日志
	 * @param result		当前权限验证是否通过
	 * @param authority		当前请求url
	 */
	public static void recordOperLog(boolean result, String requestUrl) {
		SysOperLog sysOperLog = new SysOperLog();
		// 获取当前用户
		UserInf user = ContextConst.getCurrentUser();
		// 获取当前用户登录的机构信息
		OrgInf orgInf = ContextConst.getOrgInfByUser();
		sysOperLog.setSysOperId(GenRdmGUID.getGUID());
		sysOperLog.setFuncUrl(requestUrl);
		sysOperLog.setUserId(user.getUserid().trim());
		sysOperLog.setUserIp(user.getIpaddress());
		sysOperLog.setUserNameCn(user.getUsername());
		sysOperLog.setUserNameEn(user.getUsername());
		sysOperLog.setBranchId(orgInf.getOrgid().trim());
		// 获取请求日期
		String operDate = DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDD);
		// 获取请求时间
		String operTime = DateUtil.getNow(DateUtil.DATE_FORMAT_HHMMSS);
		sysOperLog.setOperDate(operDate);
		sysOperLog.setOperTime(operTime);
		sysOperLog.setComments(result ? "当前请求的权限验证通过。" : "当前请求的权限验证未通过！");
		// 获取操作类型和请求描述
		String[] params = getOperTypeAndDesc(requestUrl);
		sysOperLog.setOperType(params[0]);
		sysOperLog.setFuncName(params[1]);
		
		// 插入数据库
		operatorLogService.insertSysOperLog(sysOperLog);
	}

	/**
	 * 获取操作类型和请求描述
	 * @param requestUrl
	 * @return
	 */
	private static String[] getOperTypeAndDesc(String requestUrl) {
		String[] params = new String[2];
		
		// 获取所有的操作类型
		Set<String> set = map_keyStart.keySet();
		for (String keyStart : set) {
			String prpt_key = keyStart + CommonConst.POINT + requestUrl;
			String prpt_value = getPropertiesValue(prpt_key);
			if (StringUtil.isNotTrimEmpty(prpt_value)) {
				// 操作类型
				params[0] = keyStart;
				// 请求描述
				params[1] = prpt_value;
			}
		}
		
		return params;
	}
	
}
