package com.synesoft.fisp.domain.service.dp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.synesoft.fisp.domain.model.DmDepositBalanceCheck;


public interface DmDepositBalanceCheckService {
	public Page<DmDepositBalanceCheck> QueryDmDepositBalanceCheckQueryList(Pageable pageable,DmDepositBalanceCheck depositBalanceCheck);
}
