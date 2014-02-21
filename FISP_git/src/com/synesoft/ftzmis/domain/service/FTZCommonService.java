/**
 * 
 */
package com.synesoft.ftzmis.domain.service;

import java.util.Map;

import com.synesoft.ftzmis.domain.model.FtzActMstrHistory;

/**
 * @author Peter
 * @date 2014-1-27 上午10:02:06
 * @version 1.0
 * @description 
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */

public interface FTZCommonService {
	
	@SuppressWarnings("rawtypes")
	public Map BalanceCheck(String submitDate,String accountNo,String subAccountNo);
	
	public FtzActMstrHistory queryFtzActMstrHistory(FtzActMstrHistory ftzActMstrHistory) ;
}
