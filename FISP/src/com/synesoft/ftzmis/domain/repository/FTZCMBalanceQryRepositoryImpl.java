package com.synesoft.ftzmis.domain.repository; 

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;
import com.synesoft.ftzmis.domain.model.FtzBalanceCode;

/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-30
 */
@Repository
public class FTZCMBalanceQryRepositoryImpl implements FTZCMBalanceQryRepository{

	
	@Resource
	private PageHandler<FtzBalanceCode> pageFtzBal;

	@Override
	public Page<FtzBalanceCode> queryBalance(Pageable pageable,
			FtzBalanceCode ftzBalanceCode) {
		return pageFtzBal.getPage(Table.FTZ_BALANCE_CODE, SQLMap.SELECT_BALANCE_COUNTS,
				SQLMap.SELECT_BALANCE_LIST,ftzBalanceCode, pageable);
	}
}
 