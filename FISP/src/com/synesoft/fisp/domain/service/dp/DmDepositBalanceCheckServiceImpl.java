package com.synesoft.fisp.domain.service.dp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.domain.model.DmDepositBalanceCheck;
import com.synesoft.fisp.domain.repository.dp.DmDepositBalanceCheckRepository;


@Service("dmDepositBalanceCheckService")
public class DmDepositBalanceCheckServiceImpl implements DmDepositBalanceCheckService {

	@Autowired
	protected DmDepositBalanceCheckRepository depositBalanceCheckRepository;
	
	@Override
	public Page<DmDepositBalanceCheck> QueryDmDepositBalanceCheckQueryList(
			Pageable pageable, DmDepositBalanceCheck depositBalanceCheck) {
		// TODO Auto-generated method stub
		return depositBalanceCheckRepository.queryList(pageable, depositBalanceCheck);
	}

}
