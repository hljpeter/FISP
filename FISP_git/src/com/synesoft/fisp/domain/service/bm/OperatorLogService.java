package com.synesoft.fisp.domain.service.bm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.SysChgBizLog;
import com.synesoft.fisp.domain.model.SysChgBizLogWithBLOBs;
import com.synesoft.fisp.domain.model.SysChgSysLog;
import com.synesoft.fisp.domain.model.SysChgSysLogWithBLOBs;
import com.synesoft.fisp.domain.model.SysOperLog;

/**
 * 操作员日志查询接口
 * @author michelle.wang
 *
 */
public interface OperatorLogService {

	/**
	 * 操作员业务日志查询
	 * @param pageable
	 * @param sysChgBizLog
	 * @return
	 */
	public Page<SysChgBizLog> transQuerySysChgBizLogList(Pageable pageable, SysChgBizLog sysChgBizLog);
	
	/**
	 * 操作员系统维护日志查询
	 * @param pageable
	 * @param sysChgSysLog
	 * @return
	 */
	public Page<SysChgSysLog> transQuerySysChgSysLogList(Pageable pageable, SysChgSysLog sysChgSysLog);
	
	/**
	 * 操作员系统操作日志查询
	 * @param pageable
	 * @param sysOperLog
	 * @return
	 */
	public Page<SysOperLog> transQuerySysOperLogList(Pageable pageable, SysOperLog sysOperLog);
	
	/**
	 *  操作员业务日志详细查询
	 * @param sysChgBizLog
	 * @return
	 */
	public SysChgBizLogWithBLOBs transQuerySysChgBizLog(SysChgBizLogWithBLOBs sysChgBizLog);
	
	/**
	 * 操作员系统维护日志详细查询
	 * @param sysChgSysLog
	 * @return
	 */
	public SysChgSysLogWithBLOBs transQuerySysChgSysLog(SysChgSysLogWithBLOBs sysChgSysLog);
	
	/**
	 * 操作员系统操作日志详细查询
	 * @param sysOperLog
	 * @return
	 */
	public SysOperLog transQuerySysOperLog(SysOperLog sysOperLog);
	
	/**
	 * 新增操作日志
	 * @param sysOperLog	操作日志
	 * @return
	 */
	public int insertSysOperLog(SysOperLog sysOperLog);
}
