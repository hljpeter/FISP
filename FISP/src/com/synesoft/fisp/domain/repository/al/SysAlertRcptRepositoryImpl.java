package com.synesoft.fisp.domain.repository.al;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.SysAlertRcpt;
@Repository
public class SysAlertRcptRepositoryImpl implements SysAlertRcptRepository {

	@Autowired
	private UpdateDAO updateDAO;
	
	@Autowired
	private QueryDAO queryDAO;
	
	@Autowired
	private PageHandler<SysAlertRcpt> pageH;
	
	@Override
	public Page<SysAlertRcpt> queryList(Pageable pageable,
			SysAlertRcpt sysAlertRcpt) {
		return pageH.getPage(Table.SYS_ALERT_RCPT,SQLMap.SELECT_COUNTS, SQLMap.SELECT_LIST, sysAlertRcpt, pageable);
	}

	@Override
	public int delById(String id) {
		return updateDAO.execute(Table.SYS_ALERT_RCPT+"."+SQLMap.DELETE_BYKEY, id);
	}

	@Override
	public int insertSysAlertRcpt(SysAlertRcpt sysAlertRcpt) {
		return updateDAO.execute(Table.SYS_ALERT_RCPT+"."+SQLMap.INSERT, sysAlertRcpt);
	}

	@Override
	public int  updateSysAlertRcpt(SysAlertRcpt sysAlertRcpt) {
		return updateDAO.execute(Table.SYS_ALERT_RCPT+"."+SQLMap.UPDATE_BYKEY_SELECTIVE,sysAlertRcpt);
	}

	@Override
	public SysAlertRcpt querySysAlertRcptById(String id) {
		return queryDAO.executeForObject(Table.SYS_ALERT_RCPT+"."+SQLMap.SELECT_BYKEY, id, SysAlertRcpt.class);
	}


}
