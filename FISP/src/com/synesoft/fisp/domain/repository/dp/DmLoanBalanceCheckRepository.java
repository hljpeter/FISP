package com.synesoft.fisp.domain.repository.dp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DmLoanBalanceCheck;



public interface DmLoanBalanceCheckRepository {
	public Page<DmLoanBalanceCheck> queryList(Pageable pageable,DmLoanBalanceCheck loanBalanceCheck);
}
