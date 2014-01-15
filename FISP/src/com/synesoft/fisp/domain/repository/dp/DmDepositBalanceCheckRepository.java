package com.synesoft.fisp.domain.repository.dp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DmDepositBalanceCheck;



public interface DmDepositBalanceCheckRepository {
	
	public Page<DmDepositBalanceCheck> queryList(Pageable pageable,DmDepositBalanceCheck depositBalanceCheck);
}
