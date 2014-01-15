package com.synesoft.fisp.domain.service.dp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.domain.model.DmLoanBalanceCheck;
import com.synesoft.fisp.domain.repository.dp.DmLoanBalanceCheckRepository;


@Service("dmLoanBalanceCheckService")
public class DmLoanBalanceCheckServiceImpl implements DmLoanBalanceCheckService {

	@Autowired
	protected DmLoanBalanceCheckRepository loanBalanceCheckRepository;
	
	@Override
	public Page<DmLoanBalanceCheck> QueryDmLoanBalanceCheckQueryList(
			Pageable pageable, DmLoanBalanceCheck loanBalanceCheck) {
		// TODO Auto-generated method stub
		return loanBalanceCheckRepository.queryList(pageable, loanBalanceCheck);
	}

}
