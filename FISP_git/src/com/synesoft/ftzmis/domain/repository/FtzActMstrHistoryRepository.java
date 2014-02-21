/**
 * 
 */
package com.synesoft.ftzmis.domain.repository;

import com.synesoft.ftzmis.domain.model.FtzActMstrHistory;

/**
 * @author Peter
 * @date 2014-1-28 上午10:22:16
 * @version 1.0
 * @description 
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */

public interface FtzActMstrHistoryRepository {
	
	public FtzActMstrHistory queryFtzActMstrHistoryByKey(FtzActMstrHistory ftzActMstrHistory);
	
	public int updateFtzActMstrHistoryByKey(FtzActMstrHistory ftzActMstrHistory);
	
	public int insertFtzActMstrHistoryByKey(FtzActMstrHistory ftzActMstrHistory);

}
