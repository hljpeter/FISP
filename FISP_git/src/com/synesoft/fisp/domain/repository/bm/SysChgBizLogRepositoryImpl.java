package com.synesoft.fisp.domain.repository.bm;


import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.SysChgBizLog;
import com.synesoft.fisp.domain.model.SysChgBizLogWithBLOBs;

/**
 * SysChgBizLogRepository实现类
 * @author michelle.wang
 * 
 */

@Repository
public class SysChgBizLogRepositoryImpl implements SysChgBizLogRepository {

	@Override
	public Page<SysChgBizLog> queryList(Pageable pageable, SysChgBizLog sysChgBizLog) {
		return pageH.getPage(Table.SYS_CHG_BIZ_LOG, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, sysChgBizLog, pageable);
	}
	@Override
	public SysChgBizLogWithBLOBs query(SysChgBizLogWithBLOBs sysChgBizLog){
		return queryDAO.executeForObject(Table.SYS_CHG_BIZ_LOG + "."
				+ SQLMap.SELECT_BYKEY, sysChgBizLog, SysChgBizLog.class);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private PageHandler<SysChgBizLog> pageH;
}
