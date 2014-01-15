package com.synesoft.fisp.domain.repository.dp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DmLoanAmountCheck;



public interface DmLoanAmountCheckRepository {
	public Page<DmLoanAmountCheck> queryList(Pageable pageable,DmLoanAmountCheck loanAmountCheck);
}
