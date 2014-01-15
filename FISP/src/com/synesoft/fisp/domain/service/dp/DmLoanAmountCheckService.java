package com.synesoft.fisp.domain.service.dp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DmLoanAmountCheck;



public interface DmLoanAmountCheckService {
	public Page<DmLoanAmountCheck> QueryDmLoanAmountCheckQueryList(Pageable pageable,DmLoanAmountCheck loanAmountCheck);
}
