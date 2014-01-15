package com.synesoft.sysrunner.domain.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.domain.model.DpExpLog;
import com.synesoft.fisp.domain.model.DpImpLog;
import com.synesoft.fisp.domain.model.DpMppLog;
import com.synesoft.sysrunner.common.constant.CommonConst;
import com.synesoft.sysrunner.domain.model.BatCtl;
import com.synesoft.sysrunner.domain.model.BatStepRtm;
import com.synesoft.sysrunner.domain.model.BatTaskInfo;
import com.synesoft.sysrunner.domain.model.BatTaskRtm;
import com.synesoft.sysrunner.domain.repository.BatchManageRepository;
import com.synesoft.sysrunner.domain.repository.BatchMonitorRepository;
import com.synesoft.sysrunner.domain.vo.BatTaskRtmVO;

@Service
public class BatchMonitorServiceImpl implements BatchMonitorService {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getBatTaskRtmList(String batDate) {
		Map map = new HashMap();
		List<BatTaskRtm> taskRtmAllList = batchMonitorRepos
				.queryAllBatTaskRtm(batDate);
		List<BatTaskRtmVO> dayTaskRtmList = new ArrayList<BatTaskRtmVO>();
		List<BatTaskRtmVO> monthTaskRtmList = new ArrayList<BatTaskRtmVO>();
		List<BatTaskRtmVO> seasonTaskRtmList = new ArrayList<BatTaskRtmVO>();
		List<BatTaskRtmVO> yearTaskRtmList = new ArrayList<BatTaskRtmVO>();
		List<BatTaskRtmVO> cusTaskRtmList = new ArrayList<BatTaskRtmVO>();
		if (null != taskRtmAllList && taskRtmAllList.size() > 0) {
			for (int i = 0; i < taskRtmAllList.size(); i++) {
				BatTaskRtm taskRtm_tmp = taskRtmAllList.get(i);
				BatTaskInfo batTaskInfo_tmp = batchManageRepos
						.getBatTaskInfo(taskRtm_tmp.getTaskId());
				if (null != batTaskInfo_tmp) {

					BatTaskRtmVO batTaskRtmVO_tmp = new BatTaskRtmVO();
					if (null != taskRtm_tmp.getStartTime()
							&& taskRtm_tmp.getStartTime().trim() != "") {
						taskRtm_tmp.setStartTime(DateUtil
								.getFormatdateAddSplit(taskRtm_tmp
										.getStartTime()));
					}
					if (null != taskRtm_tmp.getEndTime()
							&& taskRtm_tmp.getEndTime().trim() != "") {
						taskRtm_tmp
								.setEndTime(DateUtil
										.getFormatdateAddSplit(taskRtm_tmp
												.getEndTime()));
					}
					batTaskRtmVO_tmp.setBatTaskRtm(taskRtm_tmp);
					batTaskRtmVO_tmp
							.setTask_Name(batTaskInfo_tmp.getTaskName());
					batTaskRtmVO_tmp.setRuntimeId(taskRtm_tmp.getRuntimeId()
							.toString());
					if (batTaskInfo_tmp.getTaskStartType().equals(
							CommonConst.TASK_STATUS_DAY)) {
						dayTaskRtmList.add(batTaskRtmVO_tmp);
					} else if (batTaskInfo_tmp.getTaskStartType().equals(
							CommonConst.TASK_STATUS_MONTH)) {
						monthTaskRtmList.add(batTaskRtmVO_tmp);
					} else if (batTaskInfo_tmp.getTaskStartType().equals(
							CommonConst.TASK_STATUS_SEASON)) {
						seasonTaskRtmList.add(batTaskRtmVO_tmp);
					} else if (batTaskInfo_tmp.getTaskStartType().equals(
							CommonConst.TASK_STATUS_YEAR)) {
						yearTaskRtmList.add(batTaskRtmVO_tmp);
					} else if (batTaskInfo_tmp.getTaskStartType().equals(
							CommonConst.TASK_STATUS_POLLING)) {
						cusTaskRtmList.add(batTaskRtmVO_tmp);
					}
				}
			}
		}
		map.put("dayTaskRtmList", dayTaskRtmList);
		map.put("monthTaskRtmList", monthTaskRtmList);
		map.put("seasonTaskRtmList", seasonTaskRtmList);
		map.put("yearTaskRtmList", yearTaskRtmList);
		map.put("cusTaskRtmList", cusTaskRtmList);
		return map;
	}

	@Override
	public BatCtl getBatCtl() {
		return batchMonitorRepos.getBatCtl();
	}

	@Override
	public List<BatStepRtm> queryBatStepRtm(String runtimeId, String taskId) {
		List<BatStepRtm> batStepRtmList = new ArrayList<BatStepRtm>();

		batStepRtmList = batchMonitorRepos.queryBatStepRtmList(new BigDecimal(
				runtimeId));
		for (int i = 0; i < batStepRtmList.size(); i++) {
			BatStepRtm stepRtm = batStepRtmList.get(i);
			String startTime = stepRtm.getStartTime();
			String endTime = stepRtm.getEndTime();
			if (startTime != null && !startTime.trim().equals("")) {
				stepRtm.setStartTime(DateUtil.getFormatdateAddSplit(startTime));
			}
			if (endTime != null && !endTime.trim().equals("")) {
				stepRtm.setEndTime(DateUtil.getFormatdateAddSplit(endTime));
			}
		}
		return batStepRtmList;
	}

	@Override
	public DpExpLog queryDpExpLogByRunTimeId(DpExpLog dpExpLog) {
		return batchMonitorRepos.queryDpExpLogByRunTimeId(dpExpLog);
	}

	@Override
	public DpImpLog queryDpImpLogByRunTimeId(DpImpLog dpImpLog) {
		return batchMonitorRepos.queryDpImpLogByRunTimeId(dpImpLog);
	}

	@Override
	public DpMppLog queryDpMppLogByRunTimeId(DpMppLog dpMppLog) {
		return batchMonitorRepos.queryDpMppLogByRunTimeId(dpMppLog);
	}

	@Resource
	protected BatchMonitorRepository batchMonitorRepos;

	@Resource
	protected BatchManageRepository batchManageRepos;

}
