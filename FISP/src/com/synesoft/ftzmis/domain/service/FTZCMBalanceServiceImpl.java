package com.synesoft.ftzmis.domain.service; 

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.ftzmis.domain.model.FtzBalanceCode;
import com.synesoft.ftzmis.domain.repository.FTZCMBalanceQryRepository;

/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-30
 */
@Service
public class FTZCMBalanceServiceImpl implements FTZCMBalanceService {

	@Resource
	protected FTZCMBalanceQryRepository ftzcmbalanceqryrepository;

	@Override
	public Page<FtzBalanceCode> queryBalancePage(Pageable pageable,
			FtzBalanceCode ftzBalanceCode) {
		return ftzcmbalanceqryrepository.queryBalance(pageable,ftzBalanceCode);
	}
}
 