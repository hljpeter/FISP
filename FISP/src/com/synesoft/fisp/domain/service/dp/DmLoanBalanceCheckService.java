package com.synesoft.fisp.domain.service.dp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DmLoanBalanceCheck;



public interface DmLoanBalanceCheckService {
	public Page<DmLoanBalanceCheck> QueryDmLoanBalanceCheckQueryList(Pageable pageable,DmLoanBalanceCheck loanBalanceCheck);
}
