package com.synesoft.ftzmis.domain.repository; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzBalanceCode;

/** 
 *
 * description:资产负债指标代码查询
 * @author wjl 
 * @version 2013-12-30
 */
public interface FTZCMBalanceQryRepository {

	/**
	 * 资产负债查询
	 * @param pageable
	 * @param ftzBalanceCode
	 * @return
	 */
	Page<FtzBalanceCode> queryBalance(Pageable pageable,FtzBalanceCode ftzBalanceCode);

}
 