package com.synesoft.sysrunner.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.fw.common.exception.BusinessException;

import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm;
import com.synesoft.sysrunner.domain.model.BatDimInfo;
import com.synesoft.sysrunner.domain.model.BatStepInfo;
import com.synesoft.sysrunner.domain.model.BatTaskDim;
import com.synesoft.sysrunner.domain.model.BatTaskInfo;
import com.synesoft.sysrunner.domain.model.DpBatMpp;
import com.synesoft.sysrunner.domain.vo.BatStepInfoVO;

public interface BatchManageService {

	// query bat task info page
	public Page<BatTaskInfo> queryBatTaskInfoByPage(Pageable pageable,
			BatTaskInfo query_BatTaskInfo);

	// del bat task info
	public int deleteTaskInfo(BatTaskInfo del_batTaskInfo);

	public BatTaskInfo getBatTaskInfo(String taskId);

	// query bat step info page
	public Page<BatStepInfo> queryBatStepInfoByPage(Pageable pageable,
			BatStepInfo query_BatStepInfo);

	// query bat step info List
	public List<BatStepInfo> queryBatStepInfoList(BatStepInfo query_BatStepInfo);

	public BatchManageForm transTimeFmtRemove(BatchManageForm form);

	public BatchManageForm transTimeFmtAdd(BatchManageForm form);

	public List<BatTaskInfo> queryAllTaskListForMax();

	// insert bat task info
	public int insertTaskInfo(BatTaskInfo batTaskInfo);

	// modify bat task info
	public int updateTaskInfo(BatTaskInfo mod_batTaskInfo);

	// query step rely info
	public BatStepInfoVO queryBatStepRelyExclInfo(String taskId);

	public BatStepInfo queryBatStepInfo(String taskId, String stepId);

	public List<BatStepInfo> queryBatStepInfoList(String taskId);

	// insert bat step info
	public int insertStepInfo(BatStepInfoVO batStepInfoVO)
			throws BusinessException;

	// insert bat new step rely info
	public int insertNewStepRelyInfo(BatStepInfoVO batStepInfoVO);

	// insert bat new step excl info
	public int insertNewStepExclInfo(BatStepInfoVO batStepInfoVO);

	// query step info
	public BatStepInfoVO queryBatStepVOInfo(String taskId, String stepId);

	// del bat step info
	public int deleteStepInfo(String taskId, String stepId);

	// modify Step info
	public int updateStepInfo(BatStepInfoVO batStepInfoVO);

	public List<DpMppCfg> queryDpMppCfgList(DpMppCfg mppCfg);

	public List<DpExpCfg> queryDpExpCfgList(DpExpCfg dpExpCfg);

	public List<DpImpCfg> queryDpImpCfgList(DpImpCfg dpImpCfg);

	public DpBatMpp quertDpBatMpp(String taskId, String stepId);

	public DpImpCfg queryDpImpCfg(String id);

	public DpMppCfg queryDpMppCfgByMppId(String id);

	public DpExpCfg queryDpExpCfgByExpId(String id);

	public Page<BatDimInfo> queryBatDimInfoByPage(Pageable pageable,
			BatDimInfo query_BatDimInfo);

	public BatDimInfo queryBatDimInfo(BatDimInfo batDimInfo);

	public BatTaskDim queryBatTaskDim(BatTaskDim batTaskDim);

	public int insertBatTaskDim(BatTaskDim batTaskDim);

	public int updateBatTaskDim(BatTaskDim batTaskDim);
	
	public BatDimInfo queryBatDimInfoByTypeId(BatDimInfo batDimInfo);
	
	public int deleteBatDimInfo(BatDimInfo batDimInfo);
	
	public int insertBatDimInfo(BatDimInfo batDimInfo);
	
	public List<BatTaskDim> queryBatTaskDims(BatTaskDim batTaskDim);
	
	public List<BatDimInfo> queryBatDimInfos(BatDimInfo batDimInfo);
	
	public int addBatTaskDims(List<BatTaskDim> insert_BatTaskDims);
	
	public int deleteBatTaskDimByTaskId(BatTaskDim batTaskDim);

}
