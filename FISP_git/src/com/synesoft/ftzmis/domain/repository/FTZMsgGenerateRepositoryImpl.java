package com.synesoft.ftzmis.domain.repository;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;
import com.synesoft.ftzmis.domain.model.FtzMsgBatch;
import com.synesoft.ftzmis.domain.model.FtzMsgGenerateParam;
import com.synesoft.ftzmis.domain.model.FtzMsgProc;

/**
 * 报文生成
 * @author yyw
 * @date 2014-01-07
 */
@Repository("ftzMsgGenerateRepository")
public class FTZMsgGenerateRepositoryImpl implements FTZMsgGenerateRepository {

	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private UpdateDAO updateDAO;
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzMsgGenerateParamRepository#queryList(com.synesoft.ftzmis.domain.model.FtzMsgGenerateParam)
	 */
	public List<FtzMsgGenerateParam> queryList(FtzMsgGenerateParam param) {
		return queryDAO.executeForObjectList(Table.FTZ_MSG_GENERATE + "." + SQLMap.GEN_SELECT_MSG_LIST, param);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzMsgGenerateParamRepository#querySubmitDateList(com.synesoft.ftzmis.domain.model.FtzMsgGenerateParam)
	 */
	public List<String> querySubmitDateList(FtzMsgGenerateParam param) {
		return queryDAO.executeForObjectList(Table.FTZ_MSG_GENERATE + "." + SQLMap.GEN_SELECT_SUBMIT_DATE_LIST, param);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzMsgGenerateParamRepository#queryTxnList(com.synesoft.ftzmis.domain.model.FtzMsgGenerateParam)
	 */
	public List<FtzMsgGenerateParam> queryTxnList(FtzMsgGenerateParam param) {
		return queryDAO.executeForObjectList(Table.FTZ_MSG_GENERATE + "." + SQLMap.GEN_SELECT_TXN_LIST, param);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FTZMsgGenerateRepository#queryMsg(com.synesoft.ftzmis.domain.model.FtzMsgGenerateParam)
	 */
	public Integer queryMsg(FtzMsgGenerateParam param) {
		return queryDAO.executeForObject(Table.FTZ_MSG_GENERATE + "." + SQLMap.GEN_SELECT_TXN, param, Integer.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FTZMsgGenerateRepository#insertMsgBatch(com.synesoft.ftzmis.domain.model.FtzMsgBatch)
	 */
	@Override
	public Integer insertMsgBatch(FtzMsgBatch msgBatch) {
		return updateDAO.execute(Table.FTZ_MSG_GENERATE + "." + SQLMap.GEN_INSERT_MAP, msgBatch);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FTZMsgGenerateRepository#queryMsgProc(com.synesoft.ftzmis.domain.model.FtzMsgProc)
	 */
	@Override
	public FtzMsgProc queryMsgProc(FtzMsgProc proc) {
		return queryDAO.executeForObject(Table.FTZ_MSG_GENERATE + "." + SQLMap.GEN_SELECT_MSG, proc, FtzMsgProc.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FTZMsgGenerateRepository#queryMsgProcResult(com.synesoft.ftzmis.domain.model.FtzMsgGenerateParam)
	 */
	@Override
	public List<FtzMsgGenerateParam> queryMsgProcResult(FtzMsgGenerateParam param) {
		return queryDAO.executeForObjectList(Table.FTZ_MSG_GENERATE + "." + SQLMap.GEN_SELECT_MSG_PROC_RESULT, param);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FTZMsgGenerateRepository#insertMsgProc(com.synesoft.ftzmis.domain.model.FtzMsgProc)
	 */
	@Override
	public Integer insertMsgProc(FtzMsgProc proc) {
		return updateDAO.execute(Table.FTZ_MSG_GENERATE + "." + SQLMap.GEN_INSERT_MSG, proc);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FTZMsgGenerateRepository#getMsgProcIdSeq()
	 */
	@Override
	public Long getMsgProcIdSeq() {
		return queryDAO.executeForObject(Table.FTZ_MSG_GENERATE + "." + SQLMap.GEN_SELECT_MSG_ID, null, Long.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FTZMsgGenerateRepository#callBlankBatchProc(com.synesoft.ftzmis.domain.model.FtzMsgGenerateParam)
	 */
	@Override
	public int callBlankBatchProc(FtzMsgGenerateParam param) {
		return updateDAO.execute(Table.FTZ_MSG_GENERATE + "." + SQLMap.GEN_BLANK_BATCH_PROC, param);
	}

}
