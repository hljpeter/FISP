package com.synesoft.fisp.app.common.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.terasoluna.fw.common.codelist.CodeList;
import org.terasoluna.fw.common.codelist.JdbcCodeList;
import org.terasoluna.fw.common.codelist.i18n.SimpleI18nCodeList;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.encryp.EncrypDES;
import com.synesoft.fisp.domain.model.DpExprParaval;
import com.synesoft.fisp.domain.model.DpMapDict;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.vo.DpExprParavalVO;
import com.synesoft.fisp.domain.repository.DpExprParavalRepository;
import com.synesoft.fisp.domain.repository.dp.DP_Exp_Repository;

/**
 * @author zhongHubo
 * @date 2013年7月23日 19:26:57
 * @version 1.0
 * @Description 公共方法类
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class CommonUtil {
	
	private static LogUtil log  = new LogUtil(CommonUtil.class);

	/** 一级菜单长度 **/
	public static int len_fisrtMenu = 2;
	/** 二级菜单长度 **/
	public static int len_secondMenu = 5;
	/** 三级菜单长度 **/
	public static int len_thirdMenu = 8;
	
	/** 是否需要防盗链 **/
	public static String filterChain = "false";

	/** 存储系统所有的机构信息 **/
	public static Map<String, OrgInf> map_orginf = new HashMap<String, OrgInf>();
	
	/** 存储数字0123456789 **/
	public static String numbers = "0123456789";
	
	/** 存储字母abcdefghij **/
	public static String letters = "poiuytrewq";
	
	/** 方法key **/
	public static String expr_method = "method";
	
	/** 参数key **/
	public static String expr_param = "param";
	
	protected static DP_Exp_Repository dP_Exp_Repository = (DP_Exp_Repository) ContextUtil.getCtx().getBean("dP_Exp_Repository");
	
	private static DpExprParavalRepository dpExprParavalRepository = (DpExprParavalRepository) ContextUtil.getCtx().getBean("dpExprParavalRepository");
	
	/**
	 * 根据三级菜单拼接一级菜单、二级菜单
	 * 
	 * @param menuStr
	 *            需要处理的三级菜单
	 * @return
	 */
	public static String increaseMenu(String menuStr) {
		String menu_new = menuStr;

		Map<String, Boolean> map = new HashMap<String, Boolean>();

		if (menuStr != null && menuStr.length() > 0) {
			if (menu_new.lastIndexOf(CommonConst.SEPERATOR) != (menu_new
					.length() - 1)) {
				menu_new += CommonConst.SEPERATOR;
			}
			String[] menus = menuStr.split(CommonConst.UNSEPERATOR);
			if (menus != null && menus.length > 0) {
				for (int i = 0; i < menus.length; i++) {
					if (menus[i].length() >= CommonUtil.len_thirdMenu) {
						String firstMenu = menus[i].substring(0,
								CommonUtil.len_fisrtMenu);
						String secondMenu = menus[i].substring(0,
								CommonUtil.len_secondMenu);
						if (!map.containsKey(firstMenu)) {
							menu_new += firstMenu + CommonConst.SEPERATOR;
							map.put(firstMenu, true);
						}
						if (!map.containsValue(secondMenu)) {
							menu_new += secondMenu + CommonConst.SEPERATOR;
							map.put(secondMenu, true);
						}
					}
				}
			}
		}

		return menu_new;
	}

	/**
	 * 排除掉一级菜单、二级菜单
	 * 
	 * @param menuStr
	 *            需要处理的菜单
	 * @return
	 */
	public static String excludeMenu(String menuStr) {
		String menu_new = "";

		if (menuStr != null && menuStr.length() > 0) {
			String[] menus = menuStr.split(CommonConst.UNSEPERATOR);
			if (menus != null && menus.length > 0) {
				for (int i = 0; i < menus.length; i++) {
					if (menus[i].length() >= CommonUtil.len_thirdMenu) {
						menu_new += menus[i] + CommonConst.SEPERATOR;
					}
				}
			}
		}

		return menu_new;
	}

	/**
	 * 比较录入操作员与授权/拒绝操作员是否为同一人
	 * 
	 * @param creater
	 * @param checker
	 * @return
	 */
	public static int compareTlr(String creater) {
		String checker=ContextConst.getCurrentUser().getUserid();
		//相同
		if (StringUtil.trim(creater).equals(checker)) {
			return 0;
		//不同
		} else {
			return 1;
		}
	}
	
	/**
	 * 获取防盗链的用户密文
	 * @param content
	 * @return
	 */
	public static String encrytorUserKey(String content) {
		String cipher = ""; 
		// 非空判断
		if(StringUtil.isNotEmpty(content)) {
			
			// 获取当前时间
			String nowDateTime = DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
			// 获取当前用户
			UserInf userInfo = ContextConst.getCurrentUser();
			
			if(userInfo != null) {
				// 组装待加密的密文
				String content_cipher = "encrypTime=" + nowDateTime + CommonConst.comb_seperator + "encrypUser=" + userInfo.getUserid();
				cipher = content + CommonUtil.encrytor(content_cipher);
			} else {
				return "";
			}
			
		} else {
			return "";
		}
		return cipher;
	}
	
	/**
	 * 对字符串加密
	 * @param str
	 * @return
	 */
	public static String encrytor(String content) {
		String cipher = "";
		
		try {
			EncrypDES encryp = new EncrypDES();
			byte[] cipherByte = encryp.Encrytor(content);
			cipher = CommonUtil.byteToString(cipherByte);
		} catch (NoSuchAlgorithmException e) {
			log.error(e, e);
		} catch (NoSuchPaddingException e) {
			log.error(e, e);
		} catch (InvalidKeyException e) {
			log.error(e, e);
		} catch (IllegalBlockSizeException e) {
			log.error(e, e);
		} catch (BadPaddingException e) {
			log.error(e, e);
		} catch (Exception e) {
			log.error(e, e);
		}
		return cipher;
	}
	
	/**
	 * 对字符串解密
	 * @param cipher
	 * @return
	 */
	public static String decryptor(String cipher) {
		String content = "";
		
		EncrypDES encryp;
		try {
			byte[] cipherByte = CommonUtil.stringToByte(cipher);
			encryp = new EncrypDES();
			byte[] contentByte = encryp.Decryptor(cipherByte);
			content = new String(contentByte);
		} catch (NoSuchAlgorithmException e) {
			log.error(e, e);
		} catch (NoSuchPaddingException e) {
			log.error(e, e);
		} catch (InvalidKeyException e) {
			log.error(e, e);
		} catch (IllegalBlockSizeException e) {
			log.error(e, e);
		} catch (BadPaddingException e) {
			log.error(e, e);
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return content;
	}
	
	/**
	 * 将byte[]转换成String
	 * @param bytes
	 * @return
	 */
	public static String byteToString(byte[] bytes) {
		String str = "";
		String str_end = "";
		
		if(bytes != null && bytes.length > 0) {
			for (int i = 0; i < bytes.length; i++) {
				str += bytes[i] + CommonConst.comb_seperator;
			}
		}
		
		// 将数字转为字母
		for (int i = 0; i < str.length(); i++) {
			String s = str.substring(i, i+1);
			int idx_s = CommonUtil.numbers.indexOf(s);
			if(0<=idx_s && idx_s<=9) {
				str_end += CommonUtil.letters.substring(idx_s, idx_s+1);
			} else {
				str_end += s;
			}
		}
		
		return str_end;
	}
	
	/**
	 * 将String转换成byte[]
	 * @param str
	 * @return
	 */
	public static byte[] stringToByte(String str) {
		
		String str_new = "";
		
		// 将字母转为数字
		for (int i = 0; i < str.length(); i++) {
			String s = str.substring(i, i+1);
			int idx_s = CommonUtil.letters.indexOf(s);
			if(0<=idx_s && idx_s<=9) {
				str_new += CommonUtil.numbers.substring(idx_s, idx_s+1);
			} else {
				str_new += s;
			}
		}
		
		String[] strs = str_new.split(CommonConst.comb_unseperator);
		byte[] bytes = new byte[strs.length];
		
		for (int i = 0; i < strs.length; i++) {
			bytes[i] = Byte.parseByte(strs[i]);
		}
		
		return bytes;
	}
	
	/**
	 * 获取数据库类型转换map
	 * @param 
	 * @return map
	 */
	public static List<String> getTypeMap(){
		List<DpMapDict> dpMapDicts = dP_Exp_Repository.queryDpMapDicts();
		List<String> strings = new ArrayList<String>();
		
		for(DpMapDict dpMapDict :dpMapDicts){
			StringBuffer sb = new StringBuffer();
			Locale loc = Locale.US; 
			sb.append(dpMapDict.getInVal().toUpperCase(loc)+"|"+dpMapDict.getOutVal());
			strings.add(sb.toString());
		}
		
		return strings;
	}
	
	/**
	 * 解析表达式
	 * @param seqNo	表达式序列号
	 * @return
	 */
	public static Map<String, Object> analyticalExpression(String seqNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 非空判断
		if (!StringUtil.isNotTrimEmpty(seqNo)) {
			return map;
		}
		DpExprParaval dpExprParaval = new DpExprParaval();
		dpExprParaval.setSeqNo(seqNo);
		// 获取第一层参数值
		DpExprParaval dep_method = dpExprParavalRepository.queryDpExprParavalByExpId(dpExprParaval);
		if (dep_method != null) {
			String seqNo_method = dep_method.getSeqNo();
			String paramType = dep_method.getParamType();
			String paramValue = dep_method.getParamValue();
			DpExprParavalVO vo_method = new DpExprParavalVO();
			vo_method.setSeqNo(seqNo_method);
			vo_method.setNoAnalyValue(paramValue);
			vo_method.setParamType(paramType);
			// 申明表达式参数集合list
			List<DpExprParavalVO> list = new ArrayList<DpExprParavalVO>();
			if (CommonConst.paramType_expr.equals(paramType)) {
				// 参数值为表达式类型
				
				// 截取方法名、获取参数
				String method = cutMethodName(paramValue, list);
				vo_method.setMethodName(method);
				// 合并参数
				String analyValue = combParam(method, list);
				vo_method.setAnalyValue(analyValue);
			} else {
				// 参数值非表达式类型
				vo_method.setAnalyValue(paramValue);
			}
			map.put(CommonUtil.expr_method, vo_method);
			map.put(CommonUtil.expr_param, list);
		}
		
		return map;
	}
	
	/**
	 * 合并参数
	 * @param method	方法名
	 * @param list		待组合的参数
	 * @return
	 */
	public static String combParam(String method, List<DpExprParavalVO> list) {
		method += CommonConst.parenth_left;
		if (list != null && list.size() > 0) {
			for (DpExprParavalVO dpExprParavalVO : list) {
				method += dpExprParavalVO.getAnalyValue() + CommonConst.comm;
			}
			// 剪切最后多余的字符
			method = cutComma(method, CommonConst.comm);
		}
		method += CommonConst.parenth_right;
		return method;
	}

	/**
	 * 截取方法名、获取参数
	 * @param paramValue	参数值
	 * @param list			用来存储参数
	 * @return
	 */
	public static String cutMethodName(String paramValue, List<DpExprParavalVO> list) {
		String methodName = null;
		if (StringUtil.isNotTrimEmpty(paramValue)) {
			// 获取方法的括号()位置
			int idx_left = paramValue.indexOf(CommonConst.parenth_left);
			int idx_right = paramValue.lastIndexOf(CommonConst.parenth_right);
			if (idx_left > 0 && idx_right > idx_left) {
				methodName = paramValue.substring(0, idx_left);
				// 判断是否有参数
				if (idx_right > (idx_left+1)) {
					String str_params = paramValue.substring((idx_left+1), idx_right);
					analyticalParam(str_params, list);
				}
			}
		}
		return methodName;
	}

	/**
	 * 解析参数
	 * @param str_params	参数字符串
	 * @param list			存放解析后的参数
	 */
	public static void analyticalParam(String str_params, List<DpExprParavalVO> list) {
		String[] params =  str_params.split(CommonConst.comm);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				String seqNo = params[i].trim();
				DpExprParavalVO vo = new DpExprParavalVO(seqNo);
				DpExprParaval dpExprParaval = new DpExprParaval();
				dpExprParaval.setSeqNo(seqNo);
				analyticalParam(dpExprParaval, vo);
				list.add(vo);
			}
		}
	}
	
	/**
	 * 解析参数
	 * @param dpExprParaval		sql参数
	 * @param vo				存放解析后的参数
	 */
	public static void analyticalParam(DpExprParaval dpExprParaval, DpExprParavalVO vo) {
		DpExprParaval dep_value = dpExprParavalRepository.queryDpExprParavalByExpId(dpExprParaval);
		if (dep_value != null) {
			String seqNo = dep_value.getSeqNo();
			String paramType = dep_value.getParamType();
			String paramValue = dep_value.getParamValue();
			
			vo.setSeqNo(seqNo);
			vo.setNoAnalyValue(paramValue);
			vo.setParamType(paramType);
			
			if (CommonConst.paramType_expr.equals(paramType)) {
				// 参数值为表达式类型
				
				// 申明表达式参数集合list
				List<DpExprParavalVO> list = new ArrayList<DpExprParavalVO>();
				// 截取方法名、获取参数
				String method = cutMethodName(paramValue, list);
				vo.setMethodName(method);
				// 合并参数
				String analyValue = combParam(method, list);
				vo.setAnalyValue(analyValue);
			} else {
				// 参数值非表达式类型
				vo.setAnalyValue(paramValue);
			}
		}
	}
	
	/**
	 * 删除最后一个,号
	 * @param sbf
	 */
	public static String cutComma(StringBuffer sbf) {
		return CommonUtil.cutComma(sbf, CommonConst.comm);
	}
	
	/**
	 * 删除最后一个符合号
	 * @param sbf		待处理的字符串
	 * @param delimiter 分隔符
	 */
	public static String cutComma(StringBuffer sbf, String delimiter) {
		int len = sbf.length();
		int len_lastComma = sbf.lastIndexOf(delimiter);
		if(len_lastComma == (len-delimiter.length())) {
			sbf.delete(len_lastComma, len);
		}
		return sbf.toString();
	}
	
	/**
	 * 删除最后一个符合号
	 * @param sbf		待处理的字符串
	 * @param delimiter 分隔符
	 */
	public static String cutComma(String sbf, String delimiter) {
		return CommonUtil.cutComma(new StringBuffer(sbf), delimiter);
	}
	
	/**
	 * 根据seqNo获取所有的表达式参数值对象，包括递归获取的。 
	 * @param seqNo
	 * @return
	 */
	public static List<DpExprParaval> getAllDepBySeqNo(String seqNo) {
		List<DpExprParaval> list = new ArrayList<DpExprParaval>();
		// 调用重载方法
		getAllDepBySeqNo(seqNo, list);
		
		return list;
	}
	
	/**
	 * 根据seqNo获取所有的表达式参数值对象，包括递归获取的。 
	 * @param seqNo
	 * @param list	
	 * @return
	 */
	public static void getAllDepBySeqNo(String seqNo, List<DpExprParaval> list) {
		
		DpExprParaval dpExprParaval = new DpExprParaval();
		dpExprParaval.setSeqNo(seqNo);
		DpExprParaval dep_value = dpExprParavalRepository.queryDpExprParavalByExpId(dpExprParaval);
		if (dep_value != null) {
			// 将获取的新的参数值对象添加到list
			list.add(dep_value);
			String paramType = dep_value.getParamType();
			String paramValue = dep_value.getParamValue();
			
			if (CommonConst.paramType_expr.equals(paramType)) {
				// 参数值为表达式类型-递归获取下一级所有参数
				getAllDepByParamValue(paramValue, list);
			}
		}
	}
	
	/**
	 * 解析参数，获取参数对象
	 * @param paramValue	参数值
	 * @param list			用来存储参数
	 * @return
	 */
	public static void getAllDepByParamValue(String paramValue, List<DpExprParaval> list) {
		if (StringUtil.isNotTrimEmpty(paramValue)) {
			// 获取方法的括号()位置
			int idx_left = paramValue.indexOf(CommonConst.parenth_left);
			int idx_right = paramValue.lastIndexOf(CommonConst.parenth_right);
			if (idx_left > 0 && idx_right > idx_left) {
				// 判断是否有参数
				if (idx_right > (idx_left+1)) {
					String str_params = paramValue.substring((idx_left+1), idx_right);
					String[] params =  str_params.split(CommonConst.comm);
					if (params != null) {
						for (int i = 0; i < params.length; i++) {
							String seqNo = params[i].trim();
							getAllDepBySeqNo(seqNo, list);
						}
					}
				}
			}
		}
	}
	
	public static void reflashChangedCodelist(String codeId,HttpServletRequest request){
		ApplicationContext ac_new = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/applicationContext*.xml");
		JdbcCodeList codeList_en_US = (JdbcCodeList) ac_new.getBean("CL_WEEK_EN");
		JdbcCodeList codeList_zh_CN = (JdbcCodeList) ac_new.getBean("CL_WEEK_JA");
		
		codeList_zh_CN.refresh();
		
		SimpleI18nCodeList codeList_old = (SimpleI18nCodeList) ContextUtil.getCtx().getBean(codeId);
		
		
		Locale locale_en_US = new Locale("en_US");
		Locale locale_zh_CN = RequestContextUtils.getLocale(request);
		
		Map<Locale, CodeList> map = new HashMap<Locale, CodeList>();
		map.put(locale_en_US, codeList_en_US);
		map.put(locale_zh_CN, codeList_zh_CN);
		
		codeList_old.setRowsByCodeList(map);
	}
	
	public static void reflashChangedSimgleCodelist(String codeId,HttpServletRequest request){
		
		JdbcCodeList codeList_old = (JdbcCodeList) ContextUtil.getCtx().getBean(codeId);
		codeList_old.refresh();
	}
	
}
