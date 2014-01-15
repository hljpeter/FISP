package com.synesoft.fisp.domain.repository.cm;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.TipsConn;


@Repository
public class TipsConnRepositoryImpl implements TipsConnRepository {

	@Override
	public Page<TipsConn> queryList(Pageable pageable, TipsConn tipsConn) {
		return pageH.getPage(Table.TIPS_CONN, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, tipsConn, pageable);
	}

	@Override
	public TipsConn query(String nodecode) {
		return queryDAO.executeForObject(Table.TIPS_CONN + "."
				+ SQLMap.SELECT_BYKEY, nodecode, TipsConn.class);
	}

	@Override
	public int insertTipsConn(TipsConn tipsConn) {
		return updateDAO.execute(Table.TIPS_CONN + "." + SQLMap.INSERT, tipsConn);
	}

	@Override
	public int updateTipsConn(TipsConn tipsConn) {
		return updateDAO.execute(Table.TIPS_CONN + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, tipsConn);
	}

	@Override
	public int deleteTipsConn(TipsConn tipsConn) {
		return updateDAO.execute(Table.TIPS_CONN + "." + SQLMap.DELETE_BYKEY,
				tipsConn);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<TipsConn> pageH;
}
