package com.synesoft.ftzmis.domain.service; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzBalanceCode;

/** 
 *
 * description: 资产负债代码查询
 * @author wjl 
 * @version 2013-12-30
 */
public interface FTZCMBalanceService {

	/**
	 * 资产负债代码查询
	 * @param pageable
	 * @param ftzBalanceCode
	 * @return
	 */
	Page<FtzBalanceCode> queryBalancePage(Pageable pageable,FtzBalanceCode ftzBalanceCode);

}
 