package com.synesoft.fisp.domain.repository;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.SysLoginLog;

@Repository
public class SysLoginLogRepositoryImpl implements SysLoginLogRepository{

	@Resource
	private QueryDAO queryDAO;
	@Resource
	private UpdateDAO updateDAO;
	@Resource
	private PageHandler<SysLoginLog> pageH;

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.SysLoginLogRepository#insert(com.synesoft.fisp.domain.model.SysLoginLog)
	 */
	@Override
	public int insert(SysLoginLog sysLoginLog) {
		return updateDAO.execute(Table.SYS_LOGIN_LOG + "." + SQLMap.INSERT, sysLoginLog);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.SysLoginLogRepository#queryPage(org.springframework.data.domain.Pageable, com.synesoft.fisp.domain.model.SysLoginLog)
	 */
	@Override
	public Page<SysLoginLog> queryPage(Pageable pageable, SysLoginLog sysLoginLog) {
		return pageH.getPage(Table.SYS_LOGIN_LOG, SQLMap.SELECT_COUNTS, SQLMap.SELECT_LIST, sysLoginLog, pageable);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.SysLoginLogRepository#query(com.synesoft.fisp.domain.model.SysLoginLog)
	 */
	@Override
	public SysLoginLog query(SysLoginLog sysLoginLog) {
		return queryDAO.executeForObject(Table.SYS_LOGIN_LOG + "." + SQLMap.SELECT_KEY, sysLoginLog, SysLoginLog.class);
	}

}