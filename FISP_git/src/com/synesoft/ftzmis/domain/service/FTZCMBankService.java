package com.synesoft.ftzmis.domain.service; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzBankCode;

/** 
 *银行代码查询
 * description:
 * @author wjl 
 * @version 2013-12-31
 */
public interface FTZCMBankService {

	Page<FtzBankCode> queryBankPage(Pageable pageable, FtzBankCode ftzBankCode);

}
 