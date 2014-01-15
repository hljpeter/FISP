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
import com.synesoft.fisp.domain.model.DmLoanAmount;

@Repository
public class DmLoanAmountRepositoryImpl implements DmLoanAmountRepository {

	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<DmLoanAmount> pageH;
	
	@Override
	public Page<DmLoanAmount> queryInputList(Pageable pageable,
			DmLoanAmount loanAmount) {
		return pageH.getPage(Table.DM_LOAN_AMOUNT, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, loanAmount, pageable);
	}
	
	@Override
	public Page<DmLoanAmount> queryAuthList(Pageable pageable,
			DmLoanAmount loanAmount) {
		return pageH.getPage(Table.DM_LOAN_AMOUNT, SQLMap.SELECT_AUTH_COUNTS,
				SQLMap.SELECT_AUTH_LIST, loanAmount, pageable);
	}

	@Override
	public DmLoanAmount query(DmLoanAmount loanAmount) {
		return queryDAO.executeForObject(Table.DM_LOAN_AMOUNT + "."
				+ SQLMap.SELECT_BYKEY, loanAmount, DmLoanAmount.class);
	}

	@Override
	public List<DmLoanAmount> queryList(DmLoanAmount loanAmount) {
		return  queryDAO.executeForObjectList(Table.DM_LOAN_AMOUNT + "." + SQLMap.SELECT_LIST, loanAmount);
	}

	@Override
	public int insert(DmLoanAmount loanAmount) {
		return updateDAO.execute(Table.DM_LOAN_AMOUNT + "." + SQLMap.INSERT, loanAmount);
	}

	@Override
	public int update(DmLoanAmount loanAmount) {
		return updateDAO.execute(Table.DM_LOAN_AMOUNT + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, loanAmount);
	}

	@Override
	public int delete(DmLoanAmount loanAmount) {
		return updateDAO.execute(Table.DM_LOAN_AMOUNT + "." + SQLMap.DELETE_BYKEY,
				loanAmount);
	}

	@Override
	public Page<DmLoanAmount> queryList(Pageable pageable,
			DmLoanAmount loanAmount) {
		return pageH.getPage(Table.DM_LOAN_AMOUNT, SQLMap.SELECT_QUERY_COUNTS,
				SQLMap.SELECT_QUERY_LIST, loanAmount, pageable);
	}

}
