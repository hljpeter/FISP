/**
 * 
 */
package com.synesoft.ftzmis.domain.repository;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;
import com.synesoft.ftzmis.domain.model.FtzActMstr;

/**
 * @author Peter
 * @date 2014-1-28 上午10:33:49
 * @version 1.0
 * @description
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */

@Repository
public class FtzActMstrRepositoryImpl implements FtzActMstrRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synesoft.ftzmis.domain.repository.FtzActMstrRepository#
	 * insertFtzActMstrByKey(com.synesoft.ftzmis.domain.model.FtzActMstr)
	 */
	@Override
	public int insertFtzActMstrByKey(FtzActMstr ftzActMstr) {
		return updateDAO.execute(Table.FTZ_ACT_MSTR + "." + SQLMap.INSERT, ftzActMstr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synesoft.ftzmis.domain.repository.FtzActMstrRepository#
	 * queryFtzActMstrByKey(com.synesoft.ftzmis.domain.model.FtzActMstr)
	 */
	@Override
	public FtzActMstr queryFtzActMstrByKey(FtzActMstr ftzActMstr) {
		return queryDAO.executeForObject(Table.FTZ_ACT_MSTR + "."
				+ SQLMap.SELECT_PRIMARY_KEY, ftzActMstr,
				FtzActMstr.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synesoft.ftzmis.domain.repository.FtzActMstrRepository#
	 * updateFtzActMstrByKey(com.synesoft.ftzmis.domain.model.FtzActMstr)
	 */
	@Override
	public int updateFtzActMstrByKey(FtzActMstr ftzActMstr) {
		return updateDAO.execute(Table.FTZ_ACT_MSTR + "." + SQLMap.UPDATE_PRIMARY_KEY_SELECTIVE, ftzActMstr);
	}

	@Resource
	private UpdateDAO updateDAO;
	@Resource
	private QueryDAO queryDAO;

}
