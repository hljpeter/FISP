package com.synesoft.fisp.domain.repository.al;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.SysAlertRcpt;

public interface SysAlertRcptRepository {

	public Page<SysAlertRcpt> queryList(Pageable pageable, SysAlertRcpt sysAlertRcpt);

	public int delById(String id);

	public int insertSysAlertRcpt(SysAlertRcpt sysAlertRcpt);

	public int updateSysAlertRcpt(SysAlertRcpt sysAlertRcpt);

	public SysAlertRcpt querySysAlertRcptById(String id);


}
