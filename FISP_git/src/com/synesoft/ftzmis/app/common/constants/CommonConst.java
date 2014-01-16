package com.synesoft.ftzmis.app.common.constants;

/**
 * @author yyw
 * @date 2013-12-25
 */
public class CommonConst {

	public static final String I18N_PROPERTIES_NAME = "i18n.application-messages";
	public static final String SEPARATE_KEY_VALUE = ":";
	public static final String SEPARATE_TWO_FIELD = ";";

	// ///////////////////////// 动作标志 ///////////////////////////
	/** 添加批量 */
	public final static String ACTION_FLAG_ADD_MSG = "addMsg";
	/** 刷新批量 */
	public final static String ACTION_FLAG_REF_MSG = "refMsg";
	/** 更新批量 */
	public final static String ACTION_FLAG_UPT_MSG = "uptMsg";
	/** 更新批量 */
	public final static String ACTION_FLAG_DEL_MSG = "delMsg";
	/** 批量明细 */
	public final static String ACTION_FLAG_DTL_MSG = "dtlMsg";
	/** 提交批量 */
	public final static String ACTION_FLAG_SUB_MSG = "subMsg";
	/** 添加交易 */
	public final static String ACTION_FLAG_ADD_TXN = "addTxn";
	/** 更新交易 */
	public final static String ACTION_FLAG_UPT_TXN = "uptTxn";
	/** 提交交易 */
	public final static String ACTION_FLAG_SUB_TXN = "subTxn";
	/** 交易明细 */
	public final static String ACTION_FLAG_DTL_TXN = "dtlTxn";

	// ///////////////////////// 页面查询标志 ///////////////////////////
	/** 查询全部明细 */
	public final static String PAGE_SEARCH_ALL_DTL = "1";
	/** 查询待复核明细 */
	public final static String PAGE_SEARCH_NEED_CHK_DTL = "2";

	// ///////////////////////// 报文状态 ///////////////////////////
	/** 正在录入 */
	public final static String FTZ_MSG_STATUS_INPUTING = "01";
	/** 录入完成 */
	public final static String FTZ_MSG_STATUS_INPUT_COMPLETED = "02";
	/** 复核成功 */
	public final static String FTZ_MSG_STATUS_AUTH_SUCC = "03";
	/** 复核失败 */
	public final static String FTZ_MSG_STATUS_AUTH_FAIL = "04";
	/** 发送成功 */
	public final static String FTZ_MSG_STATUS_SEND_SUCC = "11";
	/** 发送失败 */
	public final static String FTZ_MSG_STATUS_SEND_FAIL = "12";
	/** 成功接收应答 */
	public final static String FTZ_MSG_STATUS_RECEIVE_PBOC_RTN_SUCC = "13";
	/** 失败接收应答 */
	public final static String FTZ_MSG_STATUS_RECEIVE_PBOC_RTN_FAIL = "14";
	/** 接收应答超时 */
	public final static String FTZ_MSG_STATUS_PBOC_RTN_TIMEOUT = "15";
	/** 失败处理应答 */
	public final static String FTZ_MSG_STATUS_PBOC_RTN_FAIL = "16";

	/** 录入可查状态 */
	public final static String[] FTZ_MSG_STATUS_INPUT_STATUS = {
			CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED,
			CommonConst.FTZ_MSG_STATUS_INPUTING,
			CommonConst.FTZ_MSG_STATUS_AUTH_FAIL,
			CommonConst.FTZ_MSG_STATUS_PBOC_RTN_FAIL };
	
	/** 审核可查状态 */
	public final static String[] FTZ_MSG_STATUS_AUTH_STATUS = {
			CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED,
			CommonConst.FTZ_MSG_STATUS_INPUTING,
			CommonConst.FTZ_MSG_STATUS_AUTH_FAIL };

	// ///////////////////////// 操作标志 ///////////////////////////
	/** 新增 */
	public final static String FTZ_MSG_EDIT_FLAG_ADD = "1";
	/** 修改 */
	public final static String FTZ_MSG_EDIT_FLAG_UPDATE = "2";
	/** 删除 */
	public final static String FTZ_MSG_EDIT_FLAG_DELETE = "3";

	// ///////////////////////// 报文编号 ///////////////////////////

	public final static String MSG_NO_210101 = "210101";// 单位存款

	public final static String MSG_NO_210102 = "210102";// FTU内部活动-金融债券

	public final static String MSG_NO_210103 = "210103";// FTU内部活动-中长期借款

	public final static String MSG_NO_210301 = "210301";// 代理发债

	public final static String MSG_NO_210104 = "210104";// 应付及暂收款

	public final static String MSG_NO_210105 = "210105";// 卖出回购资产

	public final static String MSG_NO_210106 = "210106";// 向中央银行借款

	public final static String MSG_NO_210107 = "210107";// 同业往来

	public final static String MSG_NO_210108 = "210108";// 系统内资金往来

	public final static String MSG_NO_210109 = "210109";// 外汇买卖

	public final static String MSG_NO_210110 = "210110";// FTU委托存款及委托投资基金(净)

	public final static String MSG_NO_210111 = "210111";// 代理金融机构委托贷款基金

	public final static String MSG_NO_210112 = "210112";// 各项准备

	public final static String MSG_NO_210201 = "210201";// 各项贷款

	public static final String MSG_NO_210202 = "210202";// 有价证券

	public static final String MSG_NO_210203 = "210203";// 股权及其他投资

	public static final String MSG_NO_210204 = "210204";// 应收及预付款

	public static final String MSG_NO_210205 = "210205";// 买入贩售资产

	public final static String MSG_NO_210206 = "210206";// 存放中央准备金存款

	public final static String MSG_NO_210207 = "210207";// 存放中央银行特种存款

	public final static String MSG_NO_210208 = "210208";// 缴存中央银行财政性存款

	public final static String MSG_NO_210209 = "210209";// 同业往来

	public final static String MSG_NO_210210 = "210210";// 系统内资金往来

	public final static String MSG_NO_210211 = "210211";// 库存现金

	public final static String MSG_NO_210212 = "210212";// 外汇买卖

	public final static String MSG_NO_210302 = "210302";// 应付信用证

	public final static String MSG_NO_210303 = "210303";// 应付保函/备用证

	public final static String MSG_NO_210304 = "210304";// 信用证保兑 - 进口应付

	public final static String MSG_NO_210305 = "210305";// 应付银行承兑汇票

	public final static String MSG_NO_210306 = "210306";// 应收信用证

	public final static String MSG_NO_210307 = "210307";// 应收保函/备用证

	public final static String MSG_NO_210308 = "210308";// 信用证保兑（出口应收）

	public final static String MSG_NO_210309 = "210309";// 应收银行承兑汇票

	public final static String MSG_NO_210310 = "210310";// 远期结售汇

	public final static String MSG_NO_210311 = "210311";// 汇率掉期业务

	public final static String MSG_NO_210401 = "210401";// 表外理财

	// ///////////////////////// 报文信息 ///////////////////////////
	/** 应用名称 */
	public final static String FTZ_MSG_APP_DEFAULT = "FTZMIS";
	/** 版本号 */
	public final static String FTZ_MSG_VER_DEFAULT = "1.0";
	/** 接收节点代码 */
	public final static String FTZ_MSG_DES_DEFAULT = "100000000000";

	// ///////////////////////// 日志 - 日志类型 ///////////////////////////
	/** A-新增 */
	public static final String DATA_LOG_OPERTYPE_ADD = "A";
	/** M-修改 */
	public static final String DATA_LOG_OPERTYPE_MODIFY = "M";
	/** D-删除 */
	public static final String DATA_LOG_OPERTYPE_DELETE = "D";
	/** C-审核 */
	public static final String DATA_LOG_OPERTYPE_CHECK = "C";
	/** R-拒绝 */
	public static final String DATA_LOG_OPERTYPE_REJECT = "R";
	/** S-授权 */
	public static final String DATA_LOG_OPERTYPE_S = "S";

	// ///////////////////////// 日志 - 功能ID ///////////////////////////
	/** 代理发债 */
	public static final String FUNCTION_FTZ_Add_210301 = "210301";
	/** 应付保函/备用证 */
	public static final String FUNCTION_FTZ_Add_210303 = "210303";
	/** 信用证保兑 - 进口应付 */
	public static final String FUNCTION_FTZ_Add_210304 = "210304";
	/** 应付银行承兑汇票 */
	public static final String FUNCTION_FTZ_Add_210305 = "210305";
	/** 应收信用证 */
	public static final String FUNCTION_FTZ_Add_210306 = "210306";
	/** 应收保函/备用证 */
	public static final String FUNCTION_FTZ_Add_210307 = "210307";
	/** 信用证保兑（出口应收） */
	public static final String FUNCTION_FTZ_Add_210308 = "210308";
	/** 应收银行承兑汇票 */
	public static final String FUNCTION_FTZ_Add_210309 = "210309";
	/** 表外及其他理财 */
	public static final String FUNCTION_FTZ_AUTH_2103 = "2103";

	// ///////////////////////// 校验默认值 ///////////////////////////
	/** 对方机构为境外机构时 */
	public static final String VALID_COUNTRY_CODE_DEFAULT = "CHN";

	public static final String FTZ_Add_210206 = "210206";

	public static final String FTZ_Add_210207 = "210207";

	public static final String FTZ_Add_210208 = "210208";

	public static final String FTZ_Add_210209 = "210209";

	public static final String FTZ_Add_210210 = "210210";

	public static final String FTZ_Add_210211 = "210211";

	public static final String FTZ_Add_210212 = "210212";

	// ///////////////////////// 报文处理结果 - 人行返回 ///////////////////////////
	public static final String MSG_PROCESS_RESULT_SUCCESS = "90000";

	// ///////////////////////// 期限条件 ///////////////////////////
	/** 远期 */
	public static final String TERM_CONDITION_FUTURE = "2";
	/** 即期 */
	public static final String TERM_CONDITION_IMMEDIATE = "1";

	// ///////////////////////// 利率类型 ///////////////////////////
	/** 固定利率 */
	public static final String INTEREST_TYPE_FIXED = "01";
	/** 浮动利率 */
	public static final String INTEREST_TYPE_FLOAT = "02";

	/** 交易明细SEQ_NO初始值 */
	public static final String TXN_SEQ_NO_INIT_VLAUE = "000000";

	/** 是否跳过审核 */
	public static final Boolean TXN_SKIP_SUBMIT = true;

}
