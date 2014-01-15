package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.DmDepositBalance;

@Repository
public class DmDepositBalanceRepositoryImpl implements DmDepositBalanceRepository {

	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<DmDepositBalance> pageH;
	
	@Override
	public Page<DmDepositBalance> queryInputList(Pageable pageable,
			DmDepositBalance depositBalance) {
		return pageH.getPage(Table.DM_DEPOSIT_BALANCE, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, depositBalance, pageable);
	}
	
	@Override
	public Page<DmDepositBalance> queryAuthList(Pageable pageable,
			DmDepositBalance depositBalance) {
		return pageH.getPage(Table.DM_DEPOSIT_BALANCE, SQLMap.SELECT_AUTH_COUNTS,
				SQLMap.SELECT_AUTH_LIST, depositBalance, pageable);
	}

	@Override
	public DmDepositBalance query(DmDepositBalance depositBalance) {
		return queryDAO.executeForObject(Table.DM_DEPOSIT_BALANCE + "."
				+ SQLMap.SELECT_BYKEY, depositBalance, DmDepositBalance.class);
	}

	@Override
	public List<DmDepositBalance> queryList(DmDepositBalance depositBalance) {
		return  queryDAO.executeForObjectList(Table.DM_DEPOSIT_BALANCE + "." + SQLMap.SELECT_LIST, depositBalance);
	}

	@Override
	public int insert(DmDepositBalance depositBalance) {
		return updateDAO.execute(Table.DM_DEPOSIT_BALANCE + "." + SQLMap.INSERT, depositBalance);
	}

	@Override
	public int update(DmDepositBalance depositBalance) {
		return updateDAO.execute(Table.DM_DEPOSIT_BALANCE + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, depositBalance);
	}

	@Override
	public int delete(DmDepositBalance depositBalance) {
		return updateDAO.execute(Table.DM_DEPOSIT_BALANCE + "." + SQLMap.DELETE_BYKEY,
				depositBalance);
	}

	@Override
	public Page<DmDepositBalance> queryList(Pageable pageable,
			DmDepositBalance depositBalance) {
		return pageH.getPage(Table.DM_DEPOSIT_BALANCE, SQLMap.SELECT_QUERY_COUNTS,
				SQLMap.SELECT_QUERY_LIST, depositBalance, pageable);
	}

}
