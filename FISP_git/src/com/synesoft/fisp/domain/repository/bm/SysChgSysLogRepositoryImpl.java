package com.synesoft.fisp.domain.repository.bm;


import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.SysChgSysLog;
import com.synesoft.fisp.domain.model.SysChgSysLogWithBLOBs;

/**
 * SysChgSysLogRepository实现类
 * @author michelle.wang
 * 
 */

@Repository
public class SysChgSysLogRepositoryImpl implements SysChgSysLogRepository {

	@Override
	public Page<SysChgSysLog> queryList(Pageable pageable, SysChgSysLog sysChgSysLog) {
		return pageH.getPage(Table.SYS_CHG_SYS_LOG, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, sysChgSysLog, pageable);
	}
	@Override
	public SysChgSysLogWithBLOBs query(SysChgSysLogWithBLOBs sysChgSysLog){
		return queryDAO.executeForObject(Table.SYS_CHG_SYS_LOG + "."
				+ SQLMap.SELECT_BYKEY, sysChgSysLog, SysChgSysLog.class);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private PageHandler<SysChgSysLog> pageH;
}
