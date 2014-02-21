package com.synesoft.fisp.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.SysLoginLog;

/**
 * 用户登录/登出日志
 * @author yyw
 */
public interface SysLoginLogService {

	/**
	 * 新增记录
	 * @param sysLoginLog
	 */
	public void insert(SysLoginLog sysLoginLog);
	
	/**
	 * 查询某个用户的所有记录
	 * @param pageable
	 * @param sysLoginLog
	 * @return
	 */
	public Page<SysLoginLog> getPage(Pageable pageable, SysLoginLog sysLoginLog);
	
	/**
	 * 查询某个用户的上一次成功登录的记录
	 * @param sysLoginLog
	 * @return
	 */
	public SysLoginLog getLastSuccLogin(SysLoginLog sysLoginLog);
}
