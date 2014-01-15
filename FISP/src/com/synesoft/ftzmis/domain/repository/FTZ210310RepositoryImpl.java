package com.synesoft.ftzmis.domain.repository;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;

/**
 * @author hb_huang
 * @system FTZMIS
 * @date 2014-1-3上午10:09:32
 */
@Repository
public class FTZ210310RepositoryImpl implements FTZ210310Repository {

	@Resource
	protected QueryDAO queryDAO;
	
	@Resource
	protected UpdateDAO updateDAO;
	
	@Resource
	private PageHandler<FtzOffMsgCtl> pageFtzInMsgCtl;
	
	@Resource
	private PageHandler<FtzOffTxnDtl> pageFtzInTxnDtl;

	@Override
	public Page<FtzOffMsgCtl> queryFtzOffMsgCtlPage(Pageable pageable,
			FtzOffMsgCtl query_FtzOffMsgCtl) {
		return pageFtzInMsgCtl.getPage(Table.FTZ_OFF_MSG_CTL, SQLMap.SELECT_MODEL_COUNTS,
				SQLMap.SELECT_MODEL_LIST, query_FtzOffMsgCtl, pageable);
	}

	@Override
	public int insertFtzOffMsgCtl(FtzOffMsgCtl insert_FtzOffMsgCtl) {
		return updateDAO.execute(Table.FTZ_OFF_MSG_CTL + "." + SQLMap.INSERT_INPUT, insert_FtzOffMsgCtl);
	}

	@Override
	public int insertFtzOffTxnDtl(FtzOffTxnDtl ftzOffTxnDtl) {
		return updateDAO.execute(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.INSERT_INPUT, ftzOffTxnDtl);
	}

	@Override
	public int getSeqNo(FtzOffTxnDtl ftzOffTxnDtl) {
		return queryDAO.executeForObject(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.SELECT_SEQ_NO, ftzOffTxnDtl, Integer.class);
	}

	@Override
	public FtzOffMsgCtl queryFtzOffMsgCtl(FtzOffMsgCtl ftzOffMsgCtl) {
		return queryDAO.executeForObject(Table.FTZ_OFF_MSG_CTL + "."
				+ SQLMap.SELECT_PRIMARY_KEY, ftzOffMsgCtl, FtzOffMsgCtl.class);
	}

	@Override
	public Page<FtzOffTxnDtl> queryFtzOffTxnDtlPage(Pageable pageable,
			FtzOffTxnDtl ftzOffTxnDtl) {
		return pageFtzInTxnDtl.getPage(Table.FTZ_OFF_TXN_DTL, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, ftzOffTxnDtl, pageable);
	}

	@Override
	public FtzOffTxnDtl queryFtzOffTxnDtl(FtzOffTxnDtl ftzOffTxnDtl) {
		return queryDAO.executeForObject(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.SELECT_PRIMARY_KEY, ftzOffTxnDtl, FtzOffTxnDtl.class);
	}

	@Override
	public int updateFtzOffMsgCtl(FtzOffMsgCtl update_FtzOffMsgCtl) {
		return updateDAO.execute(Table.FTZ_OFF_MSG_CTL + "." + SQLMap.UPDATE_PRIMARY_KEY_INPUT, update_FtzOffMsgCtl);
	}

	@Override
	public int updateFtzOffTxnDtlSelective(FtzOffTxnDtl ftzOffTxnDtl) {
		return updateDAO.execute(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.UPDATE_PRIMARY_KEY_INPUT, ftzOffTxnDtl);
	}

	@Override
	public int deleteFtzOffTxnDtl(FtzOffTxnDtl del_FtzOffTxnDtl) {
		return updateDAO.execute(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.DELETE_BY_PM_KEY, del_FtzOffTxnDtl);
	}

	@Override
	public int deleteFtzOffMsgCtl(FtzOffMsgCtl del_FtzOffMsgCtl) {
		return updateDAO.execute(Table.FTZ_OFF_MSG_CTL + "." + SQLMap.DELETE_MSG_ID, del_FtzOffMsgCtl);
	}

	@Override
	public int deleteFtzOffTxnDtls(FtzOffTxnDtl del_FtzOffTxnDtl) {
		return updateDAO.execute(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.DELETE_MSG_ID, del_FtzOffTxnDtl);
	}

	@Override
	public List<FtzOffTxnDtl> queryFtzOffTxnDtlList(
			FtzOffTxnDtl query_FtzOffTxnDtl) {
		return queryDAO.executeForObjectList(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.SELECT_LIST, query_FtzOffTxnDtl);
	}

	@Override
	public FtzBankCode queryFtzBankCode(FtzBankCode ftzBankCode) {
		return queryDAO.executeForObject(Table.FTZ_BANK_CODE + "."
				+ SQLMap.SELECT_PRIMARY_KEY, ftzBankCode, FtzBankCode.class);
	}
	
}
