package com.synesoft.fisp.app.common.constants;

import com.synesoft.fisp.app.common.context.CommonResourceHelper;


/**
 * @author zhongHubo
 * @date 2013年7月9日 15:29:58
 * @version 1.0
 * @Description 用户常量类
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class UserConst {
	

	//01-登陆状态(未登录)
	public static final String STATUS_NOLOGIN = "01";
	
	//02-登陆状态(已登录)
	public static final String STATUS_LOGINED = "02";
	
	//01-用户状态(正常)
	public static final String USER_STATUS_NORMAL = "01";
	
	//02-用户状态(锁定,密码输入错误超过最大次数)
	public static final String USER_STATUS_LOCK = "02";
	
	//03-用户状态(冻结,长期未使用)
	public static final String USER_STATUS_LOGOFF = "03";
	
	//密码错误最大次数
	private static int PASSWORD_MAX_FAILURE_COUNT = 5;
	
	//密码有效天数
	private static int PASSWORD_VALID_DAYS = 90;
	
	//旧密码存储个数
	private static int PASSWORD_HISTORY_COUNT = 5;
	
	//密码到期提前提醒天数
	private static int PASSWORD_OVERDUE_WARN_DAYS = 10;
	
	//强制修改密码是否需要重新登录
	private static String PASSWORD_MODIFY_RELOGIN = "TRUE";
	
	//重复登录,是否可以踢对方下线
	private static String DUPLICATE_LOGIN_ALLOW_KICK = "TRUE";

	// 角色模式
	private static String ROLE_MODE = "0";
	
	//密码状态,当为初始值/重置值时,用户登录需要强制修改密码
	public static enum PASSWORD_STATUS {
		INITIAL,COMMITTED,RESETTED;
	}
	
	//密码初始值
	private static String PASSWORD_DEFAULT_VALUE = "111111";

	public static int getPASSWORD_MAX_FAILURE_COUNT() {
		try {
			return Integer.valueOf(CommonResourceHelper.getSysParam("0001", "001").getParamVal().trim());
		} catch (Exception e) {
			return PASSWORD_MAX_FAILURE_COUNT;
		}
	}

	public static int getPASSWORD_VALID_DAYS() {
		try {
			return Integer.valueOf(CommonResourceHelper.getSysParam("0001", "002").getParamVal().trim());
		} catch (Exception e) {
			return PASSWORD_VALID_DAYS;
		}
	}

	public static int getPASSWORD_HISTORY_COUNT() {
		try {
			return Integer.valueOf(CommonResourceHelper.getSysParam("0001", "003").getParamVal().trim());
		} catch (Exception e) {
			return PASSWORD_HISTORY_COUNT;
		}
	}

	public static int getPASSWORD_OVERDUE_WARN_DAYS() {
		try {
			return Integer.valueOf(CommonResourceHelper.getSysParam("0001", "004").getParamVal().trim());
		} catch (Exception e) {
			return PASSWORD_OVERDUE_WARN_DAYS;
		}
	}

	public static String getPASSWORD_DEFAULT_VALUE() {
		try {
			return CommonResourceHelper.getSysParam("0001", "005").getParamVal().trim();
		} catch (Exception e) {
			return PASSWORD_DEFAULT_VALUE;
		}
	}

	public static String getPASSWORD_MODIFY_RELOGIN() {
		try {
			return CommonResourceHelper.getSysParam("0001", "006").getParamVal().trim();
		} catch (Exception e) {
			return PASSWORD_MODIFY_RELOGIN;
		}
	}

	public static String getDUPLICATE_LOGIN_ALLOW_KICK() {
		try {
			return CommonResourceHelper.getSysParam("0001", "007").getParamVal().trim();
		} catch (Exception e) {
			return DUPLICATE_LOGIN_ALLOW_KICK;
		}
	}

	public static String getROLE_MODE() {
		try {
			return CommonResourceHelper.getSysParam("0003", "001").getParamVal().trim();
		} catch (Exception e) {
			return ROLE_MODE;
		}
	}


}
