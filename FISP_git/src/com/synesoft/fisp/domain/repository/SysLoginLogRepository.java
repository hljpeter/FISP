package com.synesoft.fisp.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.SysLoginLog;

public interface SysLoginLogRepository {
	
	/**
	 * 新增记录
	 * @param sysLoginLog
	 * @return
	 */
	public int insert(SysLoginLog sysLoginLog);

	/**
	 * 查询某个用户的所有记录
	 * @param pageable
	 * @param sysLoginLog
	 * @return
	 */
	public Page<SysLoginLog> queryPage(Pageable pageable, SysLoginLog sysLoginLog);

	/**
	 * 查询一条记录
	 * @param sysLoginLog
	 * @return
	 */
	public SysLoginLog query(SysLoginLog sysLoginLog);
}
