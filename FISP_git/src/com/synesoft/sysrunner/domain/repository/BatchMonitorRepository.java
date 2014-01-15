package com.synesoft.sysrunner.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.synesoft.fisp.domain.model.DpExpLog;
import com.synesoft.fisp.domain.model.DpImpLog;
import com.synesoft.fisp.domain.model.DpMppLog;
import com.synesoft.sysrunner.domain.model.BatCtl;
import com.synesoft.sysrunner.domain.model.BatStepRtm;
import com.synesoft.sysrunner.domain.model.BatTaskRtm;

public interface BatchMonitorRepository {
	
	public List<BatTaskRtm> queryAllBatTaskRtm(String batDate);
	
	public BatCtl getBatCtl();
	
	public List<BatStepRtm> queryBatStepRtmList(BigDecimal runtimeId);
	
	public DpImpLog queryDpImpLogByRunTimeId(DpImpLog dpImpLog);
	
	public DpMppLog queryDpMppLogByRunTimeId(DpMppLog dpMppLog);
	
	public DpExpLog queryDpExpLogByRunTimeId(DpExpLog dpExpLog);
	

}
