package com.synesoft.fisp.domain.repository.bm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.SysChgSysLog;
import com.synesoft.fisp.domain.model.SysChgSysLogWithBLOBs;

/**
 * 操作员系统维护日志表操作
 * @author michelle.wang
 * 
 */

public interface SysChgSysLogRepository {

	/**
	 * 列表查询
	 * 
	 * @param pageable
	 * @param sysChgSysLog
	 * @return
	 */
	public Page<SysChgSysLog> queryList(Pageable pageable, SysChgSysLog sysChgSysLog);
	
	/**
	 * 详细查询
	 * @param sysChgSysLog
	 * @return
	 */
	public SysChgSysLogWithBLOBs query(SysChgSysLogWithBLOBs sysChgSysLog);

}
