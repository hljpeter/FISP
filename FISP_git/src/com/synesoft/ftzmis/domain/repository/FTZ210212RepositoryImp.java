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
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;

@Repository
public class FTZ210212RepositoryImp implements FTZ210212Repository {

	@Override
	public FtzInMsgCtl queryFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		return queryDAO.executeForObject(Table.FTZ_IN_MSG_CTL + "."
				+ SQLMap.SELECT_PRIMARY_KEY, ftzInMsgCtl, FtzInMsgCtl.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synesoft.ftzmis.domain.repository.FTZ210101Repository#
	 * queryFtzInMsgCtlPageInput(org.springframework.data.domain.Pageable,
	 * com.synesoft.ftzmis.domain.model.FtzInMsgCtl)
	 */
	@Override
	public Page<FtzInMsgCtl> queryFtzInMsgCtlPageInput(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl) {
		return pageFtzInMsgCtl.getPage(Table.FTZ_IN_MSG_CTL,
				SQLMap.SELECT_Input_COUNTS, SQLMap.SELECT_Input_LIST,
				ftzInMsgCtl, pageable);
	}

	@Override
	public Page<FtzInMsgCtl> queryFtzInMsgCtlPage(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl) {
		return pageFtzInMsgCtl
				.getPage(Table.FTZ_IN_MSG_CTL, SQLMap.SELECT_COUNTS,
						SQLMap.SELECT_LIST, ftzInMsgCtl, pageable);
	}

	@Override
	public Page<FtzInTxnDtl> queryFtzInTxnDtlPage(Pageable pageable,
			FtzInTxnDtl ftzInTxnDtl) {
		return pageFtzInTxnDtl
				.getPage(Table.FTZ_IN_TXN_DTL, SQLMap.SELECT_COUNTS,
						SQLMap.SELECT_LIST, ftzInTxnDtl, pageable);
	}

	@Override
	public FtzInTxnDtl queryFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		return queryDAO.executeForObject(Table.FTZ_IN_TXN_DTL + "."
				+ SQLMap.SELECT_PRIMARY_KEY, ftzInTxnDtl, FtzInTxnDtl.class);
	}

	@Override
	public List<FtzInTxnDtl> queryFtzInTxnDtlList(FtzInTxnDtl ftzInTxnDtl) {
		return queryDAO.executeForObjectList(Table.FTZ_IN_TXN_DTL + "."
				+ SQLMap.SELECT_LIST, ftzInTxnDtl);
	}

	@Override
	public int deleteFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		return updateDAO.execute(Table.FTZ_IN_MSG_CTL + "."
				+ SQLMap.DELETE_PRIMARY_KEY, ftzInMsgCtl);
	}

	@Override
	public int deleteFtzInTxnDtls(FtzInTxnDtl ftzInTxnDtl) {
		return updateDAO.execute(Table.FTZ_IN_TXN_DTL + "."
				+ SQLMap.DELETE_MSG_ID, ftzInTxnDtl);
	}

	@Override
	public int insertFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		return updateDAO.execute(Table.FTZ_IN_MSG_CTL + "." + SQLMap.INSERT,
				ftzInMsgCtl);
	}

	@Override
	public int updateFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		return updateDAO.execute(Table.FTZ_IN_MSG_CTL + "."
				+ SQLMap.UPDATE_PRIMARY_KEY_SELECTIVE, ftzInMsgCtl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synesoft.ftzmis.domain.repository.FTZ210101Repository#queryFtzActMstrs
	 * (com.synesoft.ftzmis.domain.model.FtzActMstr)
	 */
	@Override
	public List<FtzActMstr> queryFtzActMstrs(FtzActMstr ftzActMstr) {
		return queryDAO.executeForObjectList(Table.FTZ_ACT_MSTR + "."
				+ SQLMap.SELECT_LIST, ftzActMstr);
	}

	@Override
	public int deleteFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		return updateDAO.execute(Table.FTZ_IN_TXN_DTL + "."
				+ SQLMap.DELETE_PRIMARY_KEY, ftzInTxnDtl);
	}

	@Override
	public int insertFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		return updateDAO.execute(Table.FTZ_IN_TXN_DTL + "." + SQLMap.INSERT,
				ftzInTxnDtl);
	}

	@Override
	public int updateFtzInTxnDtlSelective(FtzInTxnDtl ftzInTxnDtl) {
		return updateDAO.execute(Table.FTZ_IN_TXN_DTL + "."
				+ SQLMap.UPDATE_PRIMARY_KEY_SELECTIVE, ftzInTxnDtl);
	}

	@Override
	public FtzBankCode queryFtzBankCode(FtzBankCode ftzBankCode) {
		return queryDAO.executeForObject(Table.FTZ_BANK_CODE + "."
				+ SQLMap.SELECT_PRIMARY_KEY, ftzBankCode, FtzBankCode.class);
	}

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private PageHandler<FtzInMsgCtl> pageFtzInMsgCtl;

	@Resource
	private PageHandler<FtzInTxnDtl> pageFtzInTxnDtl;

}
