package com.synesoft.ftzmis.domain.repository; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzBankCode;

/** 
 *银行代码查询
 * description:
 * @author wjl 
 * @version 2013-12-31
 */
public interface FTZCMBankQryRepository {

	Page<FtzBankCode> queryBankRegion(Pageable pageable, FtzBankCode ftzBankCode);

}
 