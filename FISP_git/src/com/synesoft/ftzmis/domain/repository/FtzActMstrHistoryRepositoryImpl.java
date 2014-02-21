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
import com.synesoft.ftzmis.domain.model.FtzActMstrHistory;

/**
 * @author Peter
 * @date 2014-1-28 上午10:22:34
 * @version 1.0
 * @description
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */

@Repository
public class FtzActMstrHistoryRepositoryImpl implements
		FtzActMstrHistoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synesoft.ftzmis.domain.repository.FtzActMstrHistoryRepository#
	 * queryFtzActMstrHistoryByKey
	 * (com.synesoft.ftzmis.domain.model.FtzActMstrHistory)
	 */
	@Override
	public FtzActMstrHistory queryFtzActMstrHistoryByKey(
			FtzActMstrHistory ftzActMstrHistory) {
		return queryDAO.executeForObject(Table.FTZ_ACT_MSTR_HISTORY + "."
				+ SQLMap.SELECT_PRIMARY_KEY, ftzActMstrHistory,
				FtzActMstrHistory.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synesoft.ftzmis.domain.repository.FtzActMstrHistoryRepository#
	 * insertupdateFtzActMstrHistoryByKey
	 * (com.synesoft.ftzmis.domain.model.FtzActMstrHistory)
	 */
	@Override
	public int insertFtzActMstrHistoryByKey(
			FtzActMstrHistory ftzActMstrHistory) {
		return updateDAO.execute(Table.FTZ_ACT_MSTR_HISTORY + "." + SQLMap.INSERT, ftzActMstrHistory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synesoft.ftzmis.domain.repository.FtzActMstrHistoryRepository#
	 * updateFtzActMstrHistoryByKey
	 * (com.synesoft.ftzmis.domain.model.FtzActMstrHistory)
	 */
	@Override
	public int updateFtzActMstrHistoryByKey(FtzActMstrHistory ftzActMstrHistory) {
		return updateDAO.execute(Table.FTZ_ACT_MSTR_HISTORY + "." + SQLMap.UPDATE_PRIMARY_KEY_SELECTIVE, ftzActMstrHistory);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;
}
