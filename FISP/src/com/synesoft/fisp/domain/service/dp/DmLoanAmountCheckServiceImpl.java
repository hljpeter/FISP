package com.synesoft.fisp.domain.service.dp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.domain.model.DmLoanAmountCheck;
import com.synesoft.fisp.domain.repository.dp.DmLoanAmountCheckRepository;


@Service("dmLoanAmountCheckService")
public class DmLoanAmountCheckServiceImpl implements DmLoanAmountCheckService {

	@Autowired
	protected DmLoanAmountCheckRepository loanAmountCheckRepository;
	
	@Override
	public Page<DmLoanAmountCheck> QueryDmLoanAmountCheckQueryList(
			Pageable pageable, DmLoanAmountCheck loanAmountCheck) {
		// TODO Auto-generated method stub
		return loanAmountCheckRepository.queryList(pageable, loanAmountCheck);
	}

}
