package com.synesoft.ftzmis.domain.service; 

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.repository.FTZCMBankQryRepository;


/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-31
 */
@Service
public class FTZCMBankServiceImpl implements FTZCMBankService{

	@Override
	public Page<FtzBankCode> queryBankPage(Pageable pageable,
			FtzBankCode ftzBankCode) {
		
		return ftzcmbankqryrepository.queryBankRegion(pageable,ftzBankCode);
	}
	@Resource
	protected FTZCMBankQryRepository ftzcmbankqryrepository;
}
 