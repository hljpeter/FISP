package com.synesoft.fisp.domain.service;

public interface TlrOperationLogService {

	public int insertSysChgBizLog(String funcName, String branchId,
			String userId, String userName, String operType, String operDate,String operTime,
			String beforeData, String afterData);

	public int insertSysChgSysLog(String funcName, String branchId,
			String userId, String userName, String operType, String operDate,String operTime,
			String beforeData, String afterData);
}
