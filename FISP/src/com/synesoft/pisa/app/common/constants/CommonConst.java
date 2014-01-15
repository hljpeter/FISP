/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.pisa.app.common.constants;

import com.synesoft.fisp.app.common.utils.encryp.SHAEncrypt;

/**
 * @file CommonConst.java
 * @author Jon
 * @date 2013-3-17
 * @description TODO
 * @tag 1.0.0
 * 
 */
public class CommonConst {
	// USER SESSION
	public static final String USER_SESSION = "USER_SESSION";
	// USER_OLD SESSION
	public static final String USER_OLD_SESSION = "USER_OLD_SESSION";
	// Organization SESSION
	public static final String ORG_SESSION = "ORG_SESSION";
	// HeaderMenu SESSION
	public static final String MENU_SESSION = "MENU_SESSION";
	// LeftMenu SESSION
	public static final String LEFT_MENU_SESSION = "LEFT_MENU_SESSION";
	
	//强制修改密码,用户信息
	public static final String USER_SESSION_MODPWD = "USER_SESSION_MODPWD";

	// TIPS_CONN_SESSION
	public static final String TIPS_CONN_SESSION = "TIPS_CONN_SESSION";
	// USERORGLIST_SESSION
	public static final String USERORGLIST_SESSION = "USERORGLIST_SESSION";
	
	// USERFUNC_SESSION
	public static final String USERFUNC_SESSION = "USERFUNC_SESSION";

	// Encoding
	// public static final String ENCODE_UTF8="UTF-8";

	public static final String ENCODE = "GBK";

	// language type
	public static final String ENGLISH = "en_US";
	public static final String JAPANESE = "ja_JP";
	public static final String CHINESE = "zh_CN";

	/** 操作状态：01-待审核 **/
	public static final String OPTSTATUS_WAITAUDIT = "01";
	/** 操作状态：02-正常状态 **/
	public static final String OPTSTATUS_NORMAL = "02";

	/** 分隔符 **/
	public static final String SEPERATOR = "|";

	/** 解分隔符 **/
	public static final String UNSEPERATOR = "\\|";
	
	/** 斜杠 */
	public static final String SLASH = "/";
	
	/** 点 */
	public static final String POINT = ".";
	
	/**	spring提供的Bean工厂对象名 **/
	public static final String applicationContext = "org.springframework.web.context.WebApplicationContext.ROOT";

	/**	组合分隔符	**/
	public static final String comb_seperator = "@!$";
	
	/**	解组合分隔符	**/
	public static final String comb_unseperator = "\\@\\!\\$";
	
	/** =号  **/
	public static final String equal_seperator = "=";
	
	/** + **/
	public static final String plus_seperator = "+";
	
	/** 逗号*/
	public static final String comm = ",";
	
	/** 英文状态小括号左( */
	public static final String parenth_left = "(";
	
	/** 英文状态小括号右) */
	public static final String parenth_right = ")";

	/** ============================ yyw updatd ============================ */
	// default tablespace name
	public static final String DB_DEFAULT_TABLESPACE_NAME = "USERS";
	// type 1: default tablespace
	public static final String DB_TYPE_DEFAULT_TABLESPACE = "1";
	// type 2: tablespace
	public static final String DB_TYPE_TABLESPACE = "2";
	// type 3: table name
	public static final String DB_TYPE_TABLENAME = "3";
	// type 4: tablespace and table name
	public static final String DB_TYPE_TABLESPACE_AND_TABLENAME = "4";
	
	// default sheet no
	public static final Integer DP_FILE_DEFAULT_SHEETNO = 0;

	//A-新增
	public static final String DATA_LOG_OPERTYPE_ADD = "A";
	// M-修改
	public static final String DATA_LOG_OPERTYPE_MODIFY = "M";
	// D-删除
	public static final String DATA_LOG_OPERTYPE_DELETE = "D";
	// C-审核
	public static final String DATA_LOG_OPERTYPE_CHECK = "C";
	// R-拒绝
	public static final String DATA_LOG_OPERTYPE_REJECT = "R";
	// S-授权
	public static final String DATA_LOG_OPERTYPE_S = "S";

	// DP_MAP_DICT 中的groupcode
	public static final String DP_MAP_DICT_GROUP_CODE_0001 = "0001";
	
	/** ============================ yyw updatd ============================ */
	
	public static final String I18N_PROPERTIES_NAME = "i18n.application-messages";
	public static final String SEPARATE_KEY_VALUE = ":";
	public static final String SEPARATE_TWO_FIELD = ";";

	// 0标识机构与角色相关，1标识机构与角色无关
	public static  String ORG_ROLE_REL = "0";
	
	/** 表达式参数类型-字符串 */
	public static String paramType_str = "1";
	
	/** 表达式参数类型-列 */
	public static String paramType_col = "2";
	
	/** 表达式参数类型-表达式 */
	public static String paramType_expr = "3";
	
	public static String PWD=SHAEncrypt.stringToSHA1("admin");
	
	/** 是 */
	public static final String YES = "1";
	/** 否 */
	public static final String NO = "0";
	
	/**必须是数值字符串*/
	public static final String PATTERN_NUMERICAL = "^[0-9]+\\.{0,1}[0-9]{0,2}$";
	
	/**指标数据  录入**/
	public static final String INDEX_DATA_STATUS_KEYIN = "1";
	
	/**指标数据 已审核**/
	public static final String INDEX_DATA_STATUS_AUDIT = "2";
	
}
