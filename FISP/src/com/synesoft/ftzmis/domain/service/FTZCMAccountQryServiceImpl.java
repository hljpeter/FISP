package com.synesoft.ftzmis.domain.service;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.repository.FTZCMAccountQryRepository;

@Service
public class FTZCMAccountQryServiceImpl implements FTZCMAccountQryService {

	@Override
	public Page<FtzActMstr> queryFtzActMstrPage(Pageable pageable,
			FtzActMstr ftzActMstr) {
		return ftzcmactQryRepos.queryFtzActMstrPage(pageable, ftzActMstr);
	}

	@Override
	public FtzActMstr queryFtzActMstr(FtzActMstr ftzActMstr) {
		FtzActMstr new_FtzActMstr = ftzcmactQryRepos.queryFtzActMstr(ftzActMstr);
		if(new_FtzActMstr.getDeptType() != null && !"".equals(new_FtzActMstr.getDeptType())) {
			new_FtzActMstr.setDeptType(new_FtzActMstr.getDeptType().trim());
		}
		if (new_FtzActMstr.getBalanceCode() != null && !"".equals(new_FtzActMstr.getBalanceCode())) {
			new_FtzActMstr.setBalanceCode(new_FtzActMstr.getBalanceCode().trim());
		}
		return new_FtzActMstr;
	}
	
	@Resource
	protected FTZCMAccountQryRepository ftzcmactQryRepos;

}
