package com.synesoft.fisp.domain.repository.dp;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.DmLoanAmountCheck;

@Repository
public class DmLoanAmountCheckRepositoryImpl implements DmLoanAmountCheckRepository {
	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<DmLoanAmountCheck> pageH;

	@Override
	public Page<DmLoanAmountCheck> queryList(Pageable pageable,
			DmLoanAmountCheck loanAmountCheck) {
		// TODO Auto-generated method stub
		return pageH.getPage(Table.DM_LOAN_Amount_CHECK, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, loanAmountCheck, pageable);
	}

}
