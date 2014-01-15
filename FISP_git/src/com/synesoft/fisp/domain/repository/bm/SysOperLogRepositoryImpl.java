package com.synesoft.fisp.domain.repository.bm;


import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.SysOperLog;

/**
 * SysOperLogRepository实现类
 * @author michelle.wang
 * 
 */

@Repository
public class SysOperLogRepositoryImpl implements SysOperLogRepository {

	@Override
	public Page<SysOperLog> queryList(Pageable pageable, SysOperLog sysOperLog) {
		return pageH.getPage(Table.SYS_OPER_LOG, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, sysOperLog, pageable);
	}
	@Override
	public SysOperLog query(SysOperLog sysOperLog){
		return queryDAO.executeForObject(Table.SYS_OPER_LOG + "."
				+ SQLMap.SELECT_BYKEY, sysOperLog, SysOperLog.class);
	}
	
	/**
	 * 新增操作日志
	 * @param sysOperLog	操作日志
	 * @return
	 */
	@Override
	public int insertSysOperLog(SysOperLog sysOperLog) {
		return updateDAO.execute(Table.SYS_OPER_LOG + "." + SQLMap.INSERT, sysOperLog);
	}

	@Resource
	private QueryDAO queryDAO;
	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<SysOperLog> pageH;
}
