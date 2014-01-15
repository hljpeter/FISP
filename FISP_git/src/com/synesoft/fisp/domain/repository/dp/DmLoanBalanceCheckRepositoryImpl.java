package com.synesoft.fisp.domain.repository.dp;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.DmLoanBalanceCheck;


@Repository
public class DmLoanBalanceCheckRepositoryImpl implements DmLoanBalanceCheckRepository {

	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<DmLoanBalanceCheck> pageH;

	@Override
	public Page<DmLoanBalanceCheck> queryList(Pageable pageable,
			DmLoanBalanceCheck loanBalanceCheck) {
		return pageH.getPage(Table.DM_LOAN_BALANCE_CHECK, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, loanBalanceCheck, pageable);
	}
	
	

}
