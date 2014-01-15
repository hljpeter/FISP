package com.synesoft.sysrunner.domain.repository;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.sysrunner.common.constant.SQLMap;
import com.synesoft.sysrunner.common.constant.Table;
import com.synesoft.sysrunner.domain.model.BatDimInfo;
import com.synesoft.sysrunner.domain.model.BatStepExclKey;
import com.synesoft.sysrunner.domain.model.BatStepInfo;
import com.synesoft.sysrunner.domain.model.BatStepRelyKey;
import com.synesoft.sysrunner.domain.model.BatTaskDim;
import com.synesoft.sysrunner.domain.model.BatTaskInfo;
import com.synesoft.sysrunner.domain.model.DpBatMpp;

@Repository
public class BatchManageRepositoryImpl implements BatchManageRepository {

	@Override
	public Page<BatTaskInfo> queryBatTaskInfoByPage(Pageable pageable,
			BatTaskInfo query_BatTaskInfo) {
		return pageH_task.getPage(Table.BAT_TASK_INFO, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, query_BatTaskInfo, pageable);
	}

	@Override
	public int delStepByTaskId(String taskId) {
		BatStepInfo stepInfo = new BatStepInfo();
		stepInfo.setTaskId(taskId.trim());
		return updateDAO.execute(Table.BAT_STEP_INFO + "."
				+ SQLMap.DELETE_BAT_STEP_RELY_BY_TASKID, stepInfo);
	}

	@Override
	public int delStepRelyByTaskId(String taskId) {
		BatStepRelyKey batStepRelyKey = new BatStepRelyKey();
		batStepRelyKey.setTaskId(taskId.trim());
		return updateDAO.execute(Table.BAT_STEP_RELY + "."
				+ SQLMap.DELETE_BAT_STEP_RELY_BY_TASKID, batStepRelyKey);
	}

	@Override
	public int deleteTaskInfo(BatTaskInfo del_batTaskInfo) {
		return updateDAO.execute(Table.BAT_TASK_INFO + "."
				+ SQLMap.DELETE_BYKEY, del_batTaskInfo);
	}

	@Override
	public BatTaskInfo getBatTaskInfo(String taskId) {
		BatTaskInfo taskInfo = new BatTaskInfo();
		taskInfo.setTaskId(taskId.trim());
		return queryDAO.executeForObject(Table.BAT_TASK_INFO + "."
				+ SQLMap.SELECT_BYKEY, taskInfo, BatTaskInfo.class);
	}

	@Override
	public Page<BatStepInfo> queryBatStepInfoByPage(Pageable pageable,
			BatStepInfo query_BatStepInfo) {
		return pageH_step.getPage(Table.BAT_STEP_INFO, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, query_BatStepInfo, pageable);
	}

	@Override
	public List<BatTaskInfo> queryAllTaskListForMax() {
		BatTaskInfo taskInfo = new BatTaskInfo();
		taskInfo.setTaskId(null);
		return queryDAO.executeForObjectList(Table.BAT_TASK_INFO + "."
				+ SQLMap.SELECT_LIST_MAX, taskInfo);
	}

	@Override
	public int insertTaskInfo(BatTaskInfo batTaskInfo) {
		return updateDAO.execute(Table.BAT_TASK_INFO + "." + SQLMap.INSERT,
				batTaskInfo);
	}

	@Override
	public int updateTaskInfo(BatTaskInfo mod_batTaskInfo) {
		return updateDAO.execute(Table.BAT_TASK_INFO + "."
				+ SQLMap.UPDATE_BYKEY, mod_batTaskInfo);
	}

	@Override
	public List<BatStepInfo> queryBatStepInfoList(BatStepInfo query_BatStepInfo) {
		return queryDAO.executeForObjectList(Table.BAT_STEP_INFO + "."
				+ SQLMap.SELECT_LIST, query_BatStepInfo);
	}

	@Override
	public List<BatStepInfo> queryAllTaskStepList(String taskId) {
		BatStepInfo stepInfo = new BatStepInfo();
		stepInfo.setTaskId(taskId.trim());
		return queryDAO.executeForObjectList(Table.BAT_STEP_INFO + "."
				+ SQLMap.SELECT_LIST, stepInfo);
	}

	@Override
	public BatStepInfo queryBatStepInfo(String taskId, String stepId) {
		BatStepInfo stepInfo = new BatStepInfo();
		stepInfo.setTaskId(taskId.trim());
		stepInfo.setStepId(stepId.trim());
		return queryDAO.executeForObject(Table.BAT_STEP_INFO + "."
				+ SQLMap.SELECT_BYKEY, stepInfo, BatStepInfo.class);
	}

	@Override
	public List<BatStepInfo> queryBatStepInfoList(String taskId) {
		BatStepInfo stepInfo = new BatStepInfo();
		stepInfo.setTaskId(taskId.trim());
		return queryDAO.executeForObjectList(Table.BAT_STEP_INFO + "."
				+ SQLMap.SELECT_LIST, stepInfo);
	}

	@Override
	public int insertStepInfo(BatStepInfo batStepInfo) {
		return updateDAO.execute(Table.BAT_STEP_INFO + "." + SQLMap.INSERT,
				batStepInfo);
	}

	@Override
	public int insertStepRelyInfo(BatStepRelyKey batStepRelyKey) {
		return updateDAO.execute(Table.BAT_STEP_RELY + "." + SQLMap.INSERT,
				batStepRelyKey);
	}

	@Override
	public List<BatStepExclKey> queryAllStepExclList() {
		return queryDAO.executeForObjectList(Table.BAT_STEP_EXCL + "."
				+ SQLMap.SELECT_LIST_MAX, null);
	}

	@Override
	public int insertStepExclInfo(BatStepExclKey batStepExclKey) {
		return updateDAO.execute(Table.BAT_STEP_EXCL + "." + SQLMap.INSERT,
				batStepExclKey);
	}

	@Override
	public List<BatStepRelyKey> queryBatStepRelyList(String taskId,
			String stepId) {
		BatStepRelyKey batStepRelyKey = new BatStepRelyKey();
		batStepRelyKey.setTaskId(taskId.trim());
		batStepRelyKey.setStepId(stepId.trim());
		return queryDAO.executeForObjectList(Table.BAT_STEP_RELY + "."
				+ SQLMap.SELECT_LIST, batStepRelyKey);
	}

	@Override
	public List<BatStepRelyKey> queryRelyStepList(String taskId, String stepId) {
		BatStepRelyKey batStepRelyKey = new BatStepRelyKey();
		batStepRelyKey.setTaskId(taskId.trim());
		batStepRelyKey.setRelyStepId(stepId.trim());
		return queryDAO.executeForObjectList(Table.BAT_STEP_RELY + "."
				+ SQLMap.BAT_STEP_RELY_STEP_RELY_LIST, batStepRelyKey);
	}

	@Override
	public List<BatStepExclKey> queryStepExclListByKey(String taskId,
			String stepId) {
		BatStepExclKey batStepExclKey = new BatStepExclKey();
		batStepExclKey.setTaskId(taskId.trim());
		batStepExclKey.setStepId(stepId.trim());
		return queryDAO.executeForObjectList(Table.BAT_STEP_EXCL + "."
				+ SQLMap.SELECT_LIST_EXCL, batStepExclKey);
	}

	@Override
	public int deleteStepRelyByStepId(String taskId, String stepId) {
		BatStepRelyKey batStepRelyKey = new BatStepRelyKey();
		batStepRelyKey.setTaskId(taskId.trim());
		batStepRelyKey.setStepId(stepId.trim());
		return updateDAO.execute(Table.BAT_STEP_RELY + "."
				+ SQLMap.DELETE_BAT_STEP_RELY_BY_STEPID, batStepRelyKey);
	}

	@Override
	public int deleteStepInfo(String taskId, String stepId) {
		BatStepInfo stepInfo = new BatStepInfo();
		stepInfo.setTaskId(taskId.trim());
		stepInfo.setStepId(stepId.trim());
		return updateDAO.execute(Table.BAT_STEP_INFO + "."
				+ SQLMap.DELETE_BYKEY, stepInfo);
	}

	@Override
	public int deleteStepExclByStepId(String taskId, String stepId) {
		BatStepExclKey batStepExclKey = new BatStepExclKey();
		batStepExclKey.setTaskId(taskId.trim());
		batStepExclKey.setStepId(stepId.trim());
		return updateDAO.execute(Table.BAT_STEP_EXCL + "."
				+ SQLMap.DELETE_BAT_STEP_RELY_BY_STEPID, batStepExclKey);
	}

	@Override
	public int updateStepInfo(BatStepInfo batStepInfo) {
		return updateDAO.execute(Table.BAT_STEP_INFO + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, batStepInfo);
	}

	@Override
	public List<DpExpCfg> queryDpExpCfgList(DpExpCfg dpExpCfg) {
		return queryDAO.executeForObjectList(Table.DP_EXP_CFG + "."
				+ SQLMap.SELECT_LIST, dpExpCfg);
	}

	@Override
	public List<DpImpCfg> queryDpImpCfgList(DpImpCfg dpImpCfg) {
		return queryDAO.executeForObjectList(Table.DP_IMP_CFG + "."
				+ SQLMap.SELECT_QUERY_LIST, dpImpCfg);
	}

	@Override
	public List<DpMppCfg> queryDpMppCfgList(DpMppCfg mppCfg) {
		return queryDAO.executeForObjectList(Table.DP_MPP_CFG + "."
				+ SQLMap.SELECT_LIST, mppCfg);
	}

	@Override
	public DpBatMpp quertDpBatMpp(String taskId, String stepId) {
		DpBatMpp dpBatMpp = new DpBatMpp();
		dpBatMpp.setBatTaskId(taskId.trim());
		dpBatMpp.setBatStepId(stepId.trim());
		return queryDAO.executeForObject(Table.DP_BAT_MPP + "."
				+ SQLMap.SELECT_BYKEY, dpBatMpp, DpBatMpp.class);
	}

	@Override
	public DpExpCfg queryDpExpCfgByExpId(DpExpCfg dpExpCfg) {
		return queryDAO.executeForObject(Table.DP_EXP_CFG + "."
				+ SQLMap.SELECT_BYKEY, dpExpCfg, DpExpCfg.class);
	}

	@Override
	public DpImpCfg queryDpImpCfg(String id) {
		return queryDAO.executeForObject(Table.DP_IMP_CFG + "."
				+ SQLMap.SELECT_BYKEY, id, DpImpCfg.class);
	}

	@Override
	public DpMppCfg queryDpMppCfgByMppId(DpMppCfg mppCfg) {
		return queryDAO.executeForObject(Table.DP_MPP_CFG + "."
				+ SQLMap.SELECT_BYKEY, mppCfg, DpMppCfg.class);
	}

	@Override
	public int insertDpBatMpp(DpBatMpp dpBatMpp) {
		return updateDAO.execute(Table.DP_BAT_MPP + "." + SQLMap.INSERT,
				dpBatMpp);
	}

	@Override
	public int updateDpBatMpp(DpBatMpp dpBatMpp) {
		return updateDAO.execute(Table.DP_BAT_MPP + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, dpBatMpp);
	}

	@Override
	public Page<BatDimInfo> queryBatDimInfoByPage(Pageable pageable,
			BatDimInfo query_BatDimInfo) {
		return pageH_dim.getPage(Table.BAT_DIM_INFO, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, query_BatDimInfo, pageable);
	}

	@Override
	public int insertBatDimInfo(BatDimInfo batDimInfo) {
		return updateDAO.execute(Table.BAT_DIM_INFO + "." + SQLMap.INSERT,
				batDimInfo);
	}

	@Override
	public BatDimInfo queryBatDimInfo(BatDimInfo batDimInfo) {
		return queryDAO.executeForObject(Table.BAT_DIM_INFO + "."
				+ SQLMap.SELECT_BYKEY, batDimInfo, BatDimInfo.class);
	}

	@Override
	public BatDimInfo queryBatDimInfoByTypeId(BatDimInfo batDimInfo) {
		return queryDAO.executeForObject(Table.BAT_DIM_INFO + "."
				+ SQLMap.SELECT_DIM_TYPE_ID, batDimInfo, BatDimInfo.class);
	}

	@Override
	public List<BatDimInfo> queryBatDimInfoByDimId(BatDimInfo batDimInfo) {
		return queryDAO.executeForObjectList(Table.BAT_DIM_INFO + "."
				+ SQLMap.SELECT_DIM_ID, batDimInfo);
	}

	@Override
	public int deleteBatDimInfo(BatDimInfo batDimInfo) {
		return updateDAO.execute(
				Table.BAT_DIM_INFO + "." + SQLMap.DELETE_BYKEY, batDimInfo);
	}

	@Override
	public BatTaskDim queryBatTaskDim(BatTaskDim batTaskDim) {
		return queryDAO.executeForObject(Table.BAT_TASK_DIM + "."
				+ SQLMap.SELECT_BYKEY, batTaskDim, BatTaskDim.class);
	}

	@Override
	public int insertBatTaskDim(BatTaskDim batTaskDim) {
		return updateDAO.execute(Table.BAT_TASK_DIM + "." + SQLMap.INSERT,
				batTaskDim);
	}

	@Override
	public int updateBatTaskDim(BatTaskDim batTaskDim) {
		return updateDAO.execute(
				Table.BAT_TASK_DIM + "." + SQLMap.UPDATE_BYKEY, batTaskDim);
	}

	@Override
	public List<BatTaskDim> queryBatTaskDimExcludeType(BatTaskDim batTaskDim) {
		return queryDAO.executeForObjectList(Table.BAT_TASK_DIM + "."
				+ SQLMap.SELECT_LIST_EXCLUDE_THIS_TYPE, batTaskDim);
	}
	
	@Override
	public int deleteBatTaskDim(BatTaskDim batTaskDim) {
		return updateDAO.execute(
				Table.BAT_TASK_DIM + "." + SQLMap.DELETE_BYKEY, batTaskDim);
	}
	
	@Override
	public List<BatTaskDim> queryBatTaskDims(BatTaskDim batTaskDim) {
		return queryDAO.executeForObjectList(Table.BAT_TASK_DIM + "."
				+ SQLMap.SELECT_LIST, batTaskDim);
	}
	
	@Override
	public List<BatDimInfo> queryBatDimInfos(BatDimInfo batDimInfo) {
		return queryDAO.executeForObjectList(Table.BAT_DIM_INFO + "."
				+ SQLMap.SELECT_LIST, batDimInfo);
	}
	
	@Override
	public int deleteBatTaskDimByTaskId(BatTaskDim batTaskDim) {
		return updateDAO.execute(
				Table.BAT_TASK_DIM + "." + SQLMap.DELETE_BAT_STEP_RELY_BY_TASKID, batTaskDim);
	}
	

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<BatTaskInfo> pageH_task;

	@Resource
	private PageHandler<BatStepInfo> pageH_step;

	@Resource
	private PageHandler<BatDimInfo> pageH_dim;

}
