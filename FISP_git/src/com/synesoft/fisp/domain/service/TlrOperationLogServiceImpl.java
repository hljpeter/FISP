package com.synesoft.fisp.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesoft.dataproc.jdbc.JdbcOperation;
import com.synesoft.fisp.domain.model.SysChgBizLogWithBLOBs;
import com.synesoft.fisp.domain.model.SysChgSysLogWithBLOBs;

@Service("tlrOperationLogService")
public class TlrOperationLogServiceImpl implements TlrOperationLogService {

	@Autowired
	protected JdbcOperation jdbcOperation;
	@Autowired
	protected NumberService numberService;

	@Override
	@Transactional
	public int insertSysChgBizLog(String funcId, String branchId,
			String userId, String userName, String operType,String operDate, String operTime,
			String dataBefore, String dataAfter) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into SYS_CHG_BIZ_LOG ");
		sb.append("(SYS_DATA_ID, BRANCH_ID, FUNC_ID, USER_ID,USER_NAME_CN, OPER_DATE,OPER_TIME, OPER_TYPE,DATA_BEFORE, DATA_AFTER)");
		sb.append("values (:sysDataId,:branchId,:funcId,:userId,:userNameCn,:operDate,:operTime,:operType,:dataBefore,:dataAfter)");
		SysChgBizLogWithBLOBs log = new SysChgBizLogWithBLOBs();
		log.setSysDataId(numberService.getSysIDSequence(32));
		log.setBranchId(branchId);
		log.setFuncId(funcId);
		log.setUserId(userId);
		log.setUserNameCn(userName);
		log.setOperDate(operDate);
		log.setOperTime(operTime);
		log.setOperType(operType);
		log.setDataBefore(dataBefore);
		log.setDataAfter(dataAfter);
		
		String sql = sb.toString();
		return jdbcOperation.updateNamed(sql, log);
	}

	@Override
	@Transactional
	public int insertSysChgSysLog(String funcId, String branchId,
			String userId, String userName, String operType,String operDate, String operTime,
			String dataBefore, String dataAfter) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into SYS_CHG_SYS_LOG ");
		sb.append("(SYS_DATA_ID, BRANCH_ID, FUNC_ID, USER_ID,USER_NAME_CN,OPER_DATE, OPER_TIME, OPER_TYPE,DATA_BEFORE, DATA_AFTER)");
		sb.append("values (:sysDataId,:branchId,:funcId,:userId,:userNameCn,:operDate,:operTime,:operType,:dataBefore,:dataAfter)");
		
		SysChgSysLogWithBLOBs log = new SysChgSysLogWithBLOBs();
		log.setSysDataId(numberService.getSysIDSequence(32));
		log.setBranchId(branchId);
		log.setFuncId(funcId);
		log.setUserId(userId);
		log.setUserNameCn(userName);
		log.setOperDate(operDate);
		log.setOperTime(operTime);
		log.setOperType(operType);
		log.setDataBefore(dataBefore);
		log.setDataAfter(dataAfter);
		
		String sql = sb.toString();
		return jdbcOperation.updateNamed(sql, log);
	}

}
