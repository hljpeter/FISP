package com.synesoft.fisp.domain.service.al;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.al.model.SysAlertRcptForm;
import com.synesoft.fisp.domain.model.SysAlertRcpt;

public interface SysAlertRcptService {

	public Page<SysAlertRcpt> transQuerySysAlertRcptList(Pageable pageable,
			SysAlertRcpt sysAlertRcpt);

	public void transDel(String id);

	public void transAdd(SysAlertRcptForm form);

	public SysAlertRcptForm transQuerySysAlertRcpt(String id);

	public void transUpdate(SysAlertRcptForm form);

}
