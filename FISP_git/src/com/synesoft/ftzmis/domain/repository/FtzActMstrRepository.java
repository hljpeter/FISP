/**
 * 
 */
package com.synesoft.ftzmis.domain.repository;

import com.synesoft.ftzmis.domain.model.FtzActMstr;

/**
 * @author Peter
 * @date 2014-1-28 上午10:33:30
 * @version 1.0
 * @description 
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */

public interface FtzActMstrRepository {
	
public FtzActMstr queryFtzActMstrByKey(FtzActMstr ftzActMstr);
	
	public int updateFtzActMstrByKey(FtzActMstr ftzActMstr);
	
	public int insertFtzActMstrByKey(FtzActMstr ftzActMstr);


}
