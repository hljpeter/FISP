package com.synesoft.sysrunner.domain.service;

import java.util.List;
import java.util.Map;

import com.synesoft.fisp.domain.model.DpExpLog;
import com.synesoft.fisp.domain.model.DpImpLog;
import com.synesoft.fisp.domain.model.DpMppLog;
import com.synesoft.sysrunner.domain.model.BatCtl;
import com.synesoft.sysrunner.domain.model.BatStepRtm;

public interface BatchMonitorService {

	@SuppressWarnings("rawtypes")
	public Map getBatTaskRtmList(String batDate);

	public BatCtl getBatCtl();

	public List<BatStepRtm> queryBatStepRtm(String runtimeId, String taskId);

	public DpImpLog queryDpImpLogByRunTimeId(DpImpLog dpImpLog);

	public DpMppLog queryDpMppLogByRunTimeId(DpMppLog dpMppLog);

	public DpExpLog queryDpExpLogByRunTimeId(DpExpLog dpExpLog);

}
