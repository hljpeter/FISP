package com.synesoft.fisp.domain.repository.bm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.SysOperLog;

/**
 * 操作员操作日志表操作
 * @author michelle.wang
 * 
 */

public interface SysOperLogRepository {

	/**
	 * 列表查询
	 * 
	 * @param pageable
	 * @param sysOperLog
	 * @return
	 */
	public Page<SysOperLog> queryList(Pageable pageable, SysOperLog sysOperLog);
	
	/**
	 * 详细查询
	 * @param sysOperLog
	 * @return
	 */
	public SysOperLog query(SysOperLog sysOperLog);
	
	/**
	 * 新增操作日志
	 * @param sysOperLog	操作日志
	 * @return
	 */
	public int insertSysOperLog(SysOperLog sysOperLog);

}
