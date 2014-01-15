package com.synesoft.fisp.domain.service.bm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.SysChgBizLog;
import com.synesoft.fisp.domain.model.SysChgBizLogWithBLOBs;
import com.synesoft.fisp.domain.model.SysChgSysLog;
import com.synesoft.fisp.domain.model.SysChgSysLogWithBLOBs;
import com.synesoft.fisp.domain.model.SysOperLog;
import com.synesoft.fisp.domain.repository.bm.SysChgBizLogRepository;
import com.synesoft.fisp.domain.repository.bm.SysChgSysLogRepository;
import com.synesoft.fisp.domain.repository.bm.SysOperLogRepository;

@Service("operatorLogService")
public class OperatorLogServiceImpl implements OperatorLogService {

	@Autowired
	private SysChgBizLogRepository sysChgBizLogRepository;
	
	@Autowired
	private SysChgSysLogRepository sysChgSysLogRepository;
	
	@Autowired
	private SysOperLogRepository sysOperLogRepository;
	
	@Override
	public Page<SysChgBizLog> transQuerySysChgBizLogList(Pageable pageable,
			SysChgBizLog sysChgBizLog) {
		sysChgBizLog.setBranchId(StringUtil.trim(sysChgBizLog.getBranchId()));
		return sysChgBizLogRepository.queryList(pageable, sysChgBizLog);
	}

	@Override
	public Page<SysChgSysLog> transQuerySysChgSysLogList(Pageable pageable,
			SysChgSysLog sysChgSysLog) {
		sysChgSysLog.setBranchId(StringUtil.trim(sysChgSysLog.getBranchId()));
		return sysChgSysLogRepository.queryList(pageable, sysChgSysLog);
	}

	@Override
	public Page<SysOperLog> transQuerySysOperLogList(Pageable pageable,
			SysOperLog sysOperLog) {
		sysOperLog.setBranchId(StringUtil.trim(sysOperLog.getBranchId()));
		return sysOperLogRepository.queryList(pageable, sysOperLog);
	}

	@Override
	public SysChgBizLogWithBLOBs transQuerySysChgBizLog(SysChgBizLogWithBLOBs sysChgBizLog) {
		return sysChgBizLogRepository.query(sysChgBizLog);
	}

	@Override
	public SysChgSysLogWithBLOBs transQuerySysChgSysLog(SysChgSysLogWithBLOBs sysChgSysLog) {
		return sysChgSysLogRepository.query(sysChgSysLog);
	}

	@Override
	public SysOperLog transQuerySysOperLog(SysOperLog sysOperLog) {
		return sysOperLogRepository.query(sysOperLog);
	}
	
	/**
	 * 新增操作日志
	 * @param sysOperLog	操作日志
	 * @return
	 */
	@Override
	@Transactional
	public int insertSysOperLog(SysOperLog sysOperLog) {
		return sysOperLogRepository.insertSysOperLog(sysOperLog);
	}


}
