package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.DmLoanBalance;

@Repository
public class DmLoanBalanceRepositoryImpl implements DmLoanBalanceRepository {

	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<DmLoanBalance> pageH;
	
	@Override
	public Page<DmLoanBalance> queryInputList(Pageable pageable,
			DmLoanBalance loanBalance) {
		return pageH.getPage(Table.DM_LOAN_BALANCE, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, loanBalance, pageable);
	}
	
	@Override
	public Page<DmLoanBalance> queryAuthList(Pageable pageable,
			DmLoanBalance loanBalance) {
		return pageH.getPage(Table.DM_LOAN_BALANCE, SQLMap.SELECT_AUTH_COUNTS,
				SQLMap.SELECT_AUTH_LIST, loanBalance, pageable);
	}

	@Override
	public DmLoanBalance query(DmLoanBalance loanBalance) {
		return queryDAO.executeForObject(Table.DM_LOAN_BALANCE + "."
				+ SQLMap.SELECT_BYKEY, loanBalance, DmLoanBalance.class);
	}

	@Override
	public List<DmLoanBalance> queryList(DmLoanBalance loanBalance) {
		return  queryDAO.executeForObjectList(Table.DM_LOAN_BALANCE + "." + SQLMap.SELECT_LIST, loanBalance);
	}

	@Override
	public int insert(DmLoanBalance loanBalance) {
		return updateDAO.execute(Table.DM_LOAN_BALANCE + "." + SQLMap.INSERT, loanBalance);
	}

	@Override
	public int update(DmLoanBalance loanBalance) {
		return updateDAO.execute(Table.DM_LOAN_BALANCE + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, loanBalance);
	}

	@Override
	public int delete(DmLoanBalance loanBalance) {
		return updateDAO.execute(Table.DM_LOAN_BALANCE + "." + SQLMap.DELETE_BYKEY,
				loanBalance);
	}

	@Override
	public Page<DmLoanBalance> queryList(Pageable pageable,
			DmLoanBalance loanBalance) {
		return pageH.getPage(Table.DM_LOAN_BALANCE, SQLMap.SELECT_QUERY_COUNTS,
				SQLMap.SELECT_QUERY_LIST, loanBalance, pageable);
	}

}
