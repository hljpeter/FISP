package com.synesoft.ftzmis.app.common.constants;

/**
 * @file SQLMap.java
 * @author yyw
 * @date 2013-12-25
 */
public class SQLMap {

	/** #########################Common Sqlmap Id######################### **/
	// select by primary key
	public static final String SELECT_PRIMARY_KEY = "selectByPrimaryKey";
	
	public static final String SELECT_PRIMARY_KEY_ACCOUNTNO = "selectByPrimaryKeyByAccountNo";

	// select by primary key
	public static final String SELECT_KEY = "selectByKey";

	// delete by primary key
	public static final String DELETE_PRIMARY_KEY = "deleteByPrimaryKey";

	public static final String DELETE_MSG_ID = "deleteByMsgId";

	public static final String DELETE_BY_MSG_ID = "deleteByMsgId2";
	
	public static final String DELETE_BY_PM_KEY = "deleteByPmKey";
	
	// insert save
	public static final String INSERT = "insert";
	
	//insert
	public static final String INSERT_INPUT = "insertInput";

	// get system id sequence number
	public static final String IDSEQUENCE = "selectSequenceNumber";

	// update selective by primary key
	public static final String UPDATE_PRIMARY_KEY = "updateByPrimaryKey";

	// update selective by primary key
	public static final String UPDATE_PRIMARY_KEY_SELECTIVE = "updateByPrimaryKeySelective";

	// update by condition
	public static final String UPDATE_BY_CONDITION_SELECTIVE = "updateByCondtionSelective";
	
	//update sth by pm key
	public static final String UPDATE_CONTENT_BY_PMKEY = "updateContentByPmkey";
	
	//update by pm and input
	public static final String UPDATE_PRIMARY_KEY_INPUT = "updateByPrimaryKeyInput";

	// select list
	public static final String SELECT_LIST = "selectList";

	// select list
	public static final String SELECT_NEXT = "selectNext";

	// select counts
	public static final String SELECT_COUNTS = "selectCounts";

	// select map
	public static final String SELECT_MAP = "selectMap";

	// select map
	public static final String SELECT_VALUE = "selectValue";

	// select list
	public static final String SELECT_LIST_SELECTIVE = "selectListSelective";

	// select region list
	public static final String SELECT_LIST_REGION = "selectListRegion";

	// select region list
	public static final String SELECT_REGION_COUNTS = "selectRegionCounts";

	// select Meta list
	public static final String SELECT_META_LIST = "selectMetaList";

	// select Meta list
	public static final String SELECT_META_COUNTS = "selectMetaCounts";

	// select Nation list
	public static final String SELECT_NATION_LIST = "selectNationList";

	// select Nation list
	public static final String SELECT_NATION_COUNTS = "selectNationCounts";

	// select list
	public static final String SELECT_MODEL_LIST = "selectModelList";

	// select counts
	public static final String SELECT_MODEL_COUNTS = "selectModelCounts";

	// select Input list
	public static final String SELECT_Input_LIST = "selectListInput";

	// select Input counts
	public static final String SELECT_Input_COUNTS = "selectCountsInput";

	// select seq no
	public static final String SELECT_SEQ_NO = "selectSeqNO";
	
	
	// select balance list
	public static final String SELECT_BALANCE_LIST = "selectBalanceList";
				
	// select balance counts
	public static final String SELECT_BALANCE_COUNTS = "selectBalanceCounts";
	
	// select bank list
	public static final String SELECT_BANK_LIST = "selectBankList";
					
	// select bank counts
	public static final String SELECT_BANK_COUNTS = "selectBankCounts";
	
	public static final String SELECT_SUM_BY_CDFLAG = "selectSumByCDFlag";
	
	public static final String SELECT_COUNT_BY_CDFLAG = "selectCountByCDFlag";
	
	/** #########################Common Sqlmap Id######################### **/

	public static final String SELECT_RESEND_COUNTS = "selectCountsResend";
	
	public static final String SELECT_RESEND_LIST = "selectListResend";
	
	/** 生成报文使用 */
	public static final String GEN_SELECT_MSG_LIST = "selectMsgList";
	public static final String GEN_SELECT_SUBMIT_DATE_LIST = "selectSubmitDateList";
	public static final String GEN_SELECT_TXN_LIST = "selectTxnList";
	public static final String GEN_SELECT_TXN = "selectTxn";
	public static final String GEN_INSERT_MSG = "insertMsg";
	public static final String GEN_INSERT_MAP = "insertMap";
	public static final String GEN_SELECT_MSG = "selectMsg";
	public static final String GEN_SELECT_MSG_PROC_RESULT = "selectMsgProcResult";
	public static final String GEN_SELECT_MSG_ID = "selectMsgIdSeq";
	public static final String GEN_BLANK_BATCH_PROC = "blankBatchProc";

}
