package com.synesoft.sysrunner.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.sysrunner.domain.model.BatDimInfo;
import com.synesoft.sysrunner.domain.model.BatStepExclKey;
import com.synesoft.sysrunner.domain.model.BatStepInfo;
import com.synesoft.sysrunner.domain.model.BatStepRelyKey;
import com.synesoft.sysrunner.domain.model.BatTaskDim;
import com.synesoft.sysrunner.domain.model.BatTaskInfo;
import com.synesoft.sysrunner.domain.model.DpBatMpp;

public interface BatchManageRepository {

	// query bat task info page
	public Page<BatTaskInfo> queryBatTaskInfoByPage(Pageable pageable,
			BatTaskInfo query_BatTaskInfo);

	// del step rely info by taskId
	public int delStepRelyByTaskId(String taskId);

	// del step info by taskId
	public int delStepByTaskId(String taskId);

	// del bat task info
	public int deleteTaskInfo(BatTaskInfo del_batTaskInfo);

	public BatTaskInfo getBatTaskInfo(String taskId);

	// query bat step info page
	public Page<BatStepInfo> queryBatStepInfoByPage(Pageable pageable,
			BatStepInfo query_BatStepInfo);

	// query bat step info List
	public List<BatStepInfo> queryBatStepInfoList(BatStepInfo query_BatStepInfo);

	public List<BatTaskInfo> queryAllTaskListForMax();

	// insert bat task info
	public int insertTaskInfo(BatTaskInfo batTaskInfo);

	// modify bat task info
	public int updateTaskInfo(BatTaskInfo mod_batTaskInfo);

	// 查询某任务下所有步骤列表
	public List<BatStepInfo> queryAllTaskStepList(String taskId);

	public BatStepInfo queryBatStepInfo(String taskId, String stepId);

	public List<BatStepInfo> queryBatStepInfoList(String taskId);

	// insert bat step info
	public int insertStepInfo(BatStepInfo batStepInfo);

	// insert step rely info
	public int insertStepRelyInfo(BatStepRelyKey batStepRelyKey);

	public List<BatStepExclKey> queryAllStepExclList();

	public int insertStepExclInfo(BatStepExclKey batStepExclKey);

	// 查询该步骤依赖的所有步骤列表
	public List<BatStepRelyKey> queryBatStepRelyList(String taskId,
			String stepId);

	// 查询依赖该步骤
	public List<BatStepRelyKey> queryRelyStepList(String taskId, String stepId);

	public List<BatStepExclKey> queryStepExclListByKey(String taskId,
			String stepId);

	// del bat step rely
	public int deleteStepRelyByStepId(String taskId, String stepId);

	// del bat step info
	public int deleteStepInfo(String taskId, String stepId);

	// del bat step excl
	public int deleteStepExclByStepId(String taskId, String stepId);

	// modify Step info
	public int updateStepInfo(BatStepInfo batStepInfo);
	
	public List<DpMppCfg> queryDpMppCfgList(DpMppCfg mppCfg);
	
	public List<DpExpCfg> queryDpExpCfgList(DpExpCfg dpExpCfg);
	
	public List<DpImpCfg> queryDpImpCfgList(DpImpCfg dpImpCfg);
	
	public DpBatMpp quertDpBatMpp(String taskId,String stepId);
	
	public DpImpCfg queryDpImpCfg(String id);

	public DpMppCfg queryDpMppCfgByMppId(DpMppCfg mppCfg);
	
	public DpExpCfg queryDpExpCfgByExpId(DpExpCfg dpExpCfg);
	
	public int insertDpBatMpp(DpBatMpp dpBatMpp);
	
	public int updateDpBatMpp(DpBatMpp dpBatMpp);
	
	public Page<BatDimInfo> queryBatDimInfoByPage(Pageable pageable,
			BatDimInfo query_BatDimInfo);
	
	public int insertBatDimInfo(BatDimInfo batDimInfo);
	
	public BatDimInfo queryBatDimInfo(BatDimInfo batDimInfo);
	
	public BatDimInfo queryBatDimInfoByTypeId(BatDimInfo batDimInfo);
	
	public List<BatDimInfo> queryBatDimInfoByDimId(BatDimInfo batDimInfo);
	
	public int deleteBatDimInfo(BatDimInfo batDimInfo);
	
	public BatTaskDim queryBatTaskDim(BatTaskDim batTaskDim);
	
	public int insertBatTaskDim(BatTaskDim batTaskDim);
	
	public int updateBatTaskDim(BatTaskDim batTaskDim);
	
	public int deleteBatTaskDim(BatTaskDim batTaskDim);
	
	public List<BatTaskDim> queryBatTaskDimExcludeType(BatTaskDim batTaskDim);
	
	public List<BatTaskDim> queryBatTaskDims(BatTaskDim batTaskDim);
	
	public List<BatDimInfo> queryBatDimInfos(BatDimInfo batDimInfo);
	
	public int deleteBatTaskDimByTaskId(BatTaskDim batTaskDim);
}

