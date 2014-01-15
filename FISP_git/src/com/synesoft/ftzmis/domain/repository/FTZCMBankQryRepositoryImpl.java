package com.synesoft.ftzmis.domain.repository; 

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;
import com.synesoft.ftzmis.domain.model.FtzBankCode;

/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-31
 */
@Repository
public class FTZCMBankQryRepositoryImpl implements FTZCMBankQryRepository{

	@Resource
	private PageHandler<FtzBankCode> pageFtzBank;
	@Override
	public Page<FtzBankCode> queryBankRegion(Pageable pageable,
			FtzBankCode ftzBankCode) {
		return pageFtzBank.getPage(Table.FTZ_BANK_CODE, SQLMap.SELECT_BANK_COUNTS,
				SQLMap.SELECT_BANK_LIST,ftzBankCode, pageable);
	}

}
 