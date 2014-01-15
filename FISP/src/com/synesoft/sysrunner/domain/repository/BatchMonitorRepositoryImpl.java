package com.synesoft.sysrunner.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.model.DpExpLog;
import com.synesoft.fisp.domain.model.DpImpLog;
import com.synesoft.fisp.domain.model.DpMppLog;
import com.synesoft.sysrunner.common.constant.SQLMap;
import com.synesoft.sysrunner.common.constant.Table;
import com.synesoft.sysrunner.domain.model.BatCtl;
import com.synesoft.sysrunner.domain.model.BatStepRtm;
import com.synesoft.sysrunner.domain.model.BatTaskRtm;

@Repository
public class BatchMonitorRepositoryImpl implements BatchMonitorRepository {
	
	@Override
	public List<BatTaskRtm> queryAllBatTaskRtm(String batDate) {
		BatTaskRtm taskRtm = new BatTaskRtm();
		taskRtm.setBatDate(batDate);
		return queryDAO.executeForObjectList(Table.BAT_TASK_RTM + "."
				+ SQLMap.SELECT_LIST, taskRtm);
	}
	
	@Override
	public BatCtl getBatCtl() {
		BatCtl batCtl = new BatCtl();
		batCtl.setId(new BigDecimal(0));
		return queryDAO.executeForObject(Table.BAT_CTL + "."
				+ SQLMap.SELECT_BYKEY, batCtl, BatCtl.class);
	}
	
	@Override
	public List<BatStepRtm> queryBatStepRtmList(BigDecimal runtimeId) {
		BatStepRtm stepRtm = new BatStepRtm();
		stepRtm.setRuntimeId(runtimeId);
		return queryDAO.executeForObjectList(Table.BAT_STEP_RTM + "." 
				+ SQLMap.SELECT_LIST, stepRtm);
	}
	
	@Override
	public DpExpLog queryDpExpLogByRunTimeId(DpExpLog dpExpLog) {
		return queryDAO.executeForObject(Table.DP_EXP_LOG + "."
				+ SQLMap.SELECT_BYKEY, dpExpLog, DpExpLog.class);
	}
	
	@Override
	public DpImpLog queryDpImpLogByRunTimeId(DpImpLog dpImpLog) {
		return queryDAO.executeForObject(Table.DP_IMP_LOG + "."
				+ SQLMap.SELECT_BYKEY, dpImpLog, DpImpLog.class);
	}
	
	@Override
	public DpMppLog queryDpMppLogByRunTimeId(DpMppLog dpMppLog) {
		return queryDAO.executeForObject(Table.DP_MPP_LOG + "."
				+ SQLMap.SELECT_BYKEY, dpMppLog, DpMppLog.class);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

}
