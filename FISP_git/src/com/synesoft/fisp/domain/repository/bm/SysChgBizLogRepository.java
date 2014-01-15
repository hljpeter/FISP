package com.synesoft.fisp.domain.repository.bm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.SysChgBizLog;
import com.synesoft.fisp.domain.model.SysChgBizLogWithBLOBs;

/**
 * 操作员业务日志表操作
 * @author michelle.wang
 * 
 */

public interface SysChgBizLogRepository {

	/**
	 * 列表查询
	 * 
	 * @param pageable
	 * @param sysChgBizLog
	 * @return
	 */
	public Page<SysChgBizLog> queryList(Pageable pageable, SysChgBizLog sysChgBizLog);
	
	/**
	 * 详细查询
	 * @param sysChgBizLog
	 * @return
	 */
	public SysChgBizLogWithBLOBs query(SysChgBizLogWithBLOBs sysChgBizLog);

}
