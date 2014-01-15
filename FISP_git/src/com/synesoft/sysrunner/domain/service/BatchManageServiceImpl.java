package com.synesoft.sysrunner.domain.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm;
import com.synesoft.sysrunner.common.constant.DateUtil;
import com.synesoft.sysrunner.domain.model.BatDimInfo;
import com.synesoft.sysrunner.domain.model.BatStepExclKey;
import com.synesoft.sysrunner.domain.model.BatStepInfo;
import com.synesoft.sysrunner.domain.model.BatStepRelyKey;
import com.synesoft.sysrunner.domain.model.BatTaskDim;
import com.synesoft.sysrunner.domain.model.BatTaskInfo;
import com.synesoft.sysrunner.domain.model.DpBatMpp;
import com.synesoft.sysrunner.domain.repository.BatchManageRepository;
import com.synesoft.sysrunner.domain.vo.BatStepInfoVO;

@Service
public class BatchManageServiceImpl implements BatchManageService {

	@Override
	public Page<BatTaskInfo> queryBatTaskInfoByPage(Pageable pageable,
			BatTaskInfo query_BatTaskInfo) {
		Page<BatTaskInfo> page = null;
		page = batchManageRepos.queryBatTaskInfoByPage(pageable,
				query_BatTaskInfo);
		return page;
	}

	@Override
	@Transactional
	public int deleteTaskInfo(BatTaskInfo del_batTaskInfo) {
		int result = 0;
		BatDimInfo query_batDimInfo = new BatDimInfo();
		query_batDimInfo.setDimensionId(del_batTaskInfo.getTaskId().trim());
		query_batDimInfo.setDimTypeId(" ");
		List<BatDimInfo> batDimInfos = batchManageRepos
				.queryBatDimInfoByDimId(query_batDimInfo);
		if (null != batDimInfos) {
			for (int i = 0; i < batDimInfos.size(); i++) {
				batchManageRepos.deleteBatDimInfo(batDimInfos.get(i));
			}
		}

		BatTaskDim query_BatTaskDim = new BatTaskDim();
		query_BatTaskDim.setDimTypeId(" ");
		query_BatTaskDim.setDimensionId(del_batTaskInfo.getTaskId());
		query_BatTaskDim.setTaskId(del_batTaskInfo.getTaskId());
		List<BatTaskDim> batTaskDims = batchManageRepos
				.queryBatTaskDimExcludeType(query_BatTaskDim);
		for (int i = 0; i < batTaskDims.size(); i++) {
			batchManageRepos.deleteBatTaskDim(batTaskDims.get(i));
		}

		result = batchManageRepos.delStepRelyByTaskId(del_batTaskInfo
				.getTaskId());
		result = batchManageRepos.delStepByTaskId(del_batTaskInfo.getTaskId()) + 1;
		result = batchManageRepos.deleteTaskInfo(del_batTaskInfo) + 1;
		return result;
	}

	@Override
	public BatTaskInfo getBatTaskInfo(String taskId) {
		return batchManageRepos.getBatTaskInfo(taskId);
	}

	@Override
	public Page<BatStepInfo> queryBatStepInfoByPage(Pageable pageable,
			BatStepInfo query_BatStepInfo) {
		Page<BatStepInfo> page = null;
		page = batchManageRepos.queryBatStepInfoByPage(pageable,
				query_BatStepInfo);
		return page;
	}

	@Override
	public List<BatTaskInfo> queryAllTaskListForMax() {
		return batchManageRepos.queryAllTaskListForMax();
	}

	@Override
	@Transactional
	public int insertTaskInfo(BatTaskInfo batTaskInfo) {

		BatTaskDim query_BatTaskDim = new BatTaskDim();
		query_BatTaskDim.setDimTypeId(batTaskInfo.getDimTypeId());
		query_BatTaskDim.setDimensionId(batTaskInfo.getTaskId());
		query_BatTaskDim.setTaskId(batTaskInfo.getTaskId());
		List<BatTaskDim> batTaskDims = batchManageRepos
				.queryBatTaskDimExcludeType(query_BatTaskDim);
		for (int i = 0; i < batTaskDims.size(); i++) {
			batchManageRepos.deleteBatTaskDim(batTaskDims.get(i));
		}

		return batchManageRepos.insertTaskInfo(batTaskInfo);
	}

	@Override
	@Transactional
	public int updateTaskInfo(BatTaskInfo mod_batTaskInfo) {

		BatTaskDim query_BatTaskDim = new BatTaskDim();
		query_BatTaskDim.setDimTypeId(mod_batTaskInfo.getDimTypeId());
		query_BatTaskDim.setDimensionId(mod_batTaskInfo.getTaskId());
		query_BatTaskDim.setTaskId(mod_batTaskInfo.getTaskId());
		List<BatTaskDim> batTaskDims = batchManageRepos
				.queryBatTaskDimExcludeType(query_BatTaskDim);
		for (int i = 0; i < batTaskDims.size(); i++) {
			batchManageRepos.deleteBatTaskDim(batTaskDims.get(i));
		}

		return batchManageRepos.updateTaskInfo(mod_batTaskInfo);
	}

	@Override
	public List<BatStepInfo> queryBatStepInfoList(BatStepInfo query_BatStepInfo) {
		return batchManageRepos.queryBatStepInfoList(query_BatStepInfo);
	}

	@Override
	public BatStepInfoVO queryBatStepRelyExclInfo(String taskId) {
		BatStepInfoVO batStepVO = new BatStepInfoVO();
		List<BatStepInfo> result_BatStepInfos = batchManageRepos
				.queryAllTaskStepList(taskId);
		if (null != result_BatStepInfos && result_BatStepInfos.size() > 0) {
			List<String> unDependencyStepList = new ArrayList<String>();
			List<String> unExcludeStepList = new ArrayList<String>();
			for (int i = 0; i < result_BatStepInfos.size(); i++) {
				unDependencyStepList
						.add(result_BatStepInfos.get(i).getStepId());
				unExcludeStepList.add(result_BatStepInfos.get(i).getStepId());
			}
			batStepVO.setUnDependencyStepList(unDependencyStepList);
			batStepVO.setUnExcludeStepList(unExcludeStepList);
		}
		return batStepVO;
	}

	@Override
	public BatStepInfo queryBatStepInfo(String taskId, String stepId) {
		return batchManageRepos.queryBatStepInfo(taskId, stepId);
	}

	@Override
	public List<BatStepInfo> queryBatStepInfoList(String taskId) {
		return batchManageRepos.queryBatStepInfoList(taskId);
	}

	@Override
	@Transactional
	public int insertStepInfo(BatStepInfoVO batStepInfoVO)
			throws BusinessException {
		int result1 = batchManageRepos.insertStepInfo(batStepInfoVO
				.getBatStepInfo());
		int result2 = this.insertNewStepRelyInfo(batStepInfoVO);
		int result3 = this.insertNewStepExclInfo(batStepInfoVO);

		BatStepInfo batStepInfo = batStepInfoVO.getBatStepInfo();
		DpBatMpp result_dpBatMpp = batchManageRepos.quertDpBatMpp(
				batStepInfo.getTaskId(), batStepInfo.getStepId());
		if (null == result_dpBatMpp) {
			DpBatMpp insert_DpBatMpp = new DpBatMpp();
			insert_DpBatMpp.setBatTaskId(batStepInfo.getTaskId());
			insert_DpBatMpp.setBatStepId(batStepInfo.getStepId());
			insert_DpBatMpp.setMapType(batStepInfo.getDpCfgType());
			insert_DpBatMpp.setDpCfgId(batStepInfo.getDpCfgId());
			batchManageRepos.insertDpBatMpp(insert_DpBatMpp);
		} else {
			DpBatMpp update_DpBatMpp = new DpBatMpp();
			update_DpBatMpp.setBatTaskId(batStepInfo.getTaskId());
			update_DpBatMpp.setBatStepId(batStepInfo.getStepId());
			update_DpBatMpp.setMapType(batStepInfo.getDpCfgType());
			update_DpBatMpp.setDpCfgId(batStepInfo.getDpCfgId());
			batchManageRepos.updateDpBatMpp(update_DpBatMpp);
		}

		if (3 == result1 + result2 + result3) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	@Transactional
	public int insertNewStepRelyInfo(BatStepInfoVO batStepInfoVO) {
		List<String> dependencyStepList = batStepInfoVO.getDependencyStepList();
		if (null == dependencyStepList || dependencyStepList.size() == 0) {
			return 1;
		} else {
			BatStepInfo stepInfo = batStepInfoVO.getBatStepInfo();
			int count = 0;
			for (int i = 0; i < dependencyStepList.size(); i++) {
				BatStepRelyKey stepRelyKey_tmp = new BatStepRelyKey();
				stepRelyKey_tmp.setTaskId(stepInfo.getTaskId());
				stepRelyKey_tmp.setStepId(stepInfo.getStepId());
				stepRelyKey_tmp.setRelyStepId(dependencyStepList.get(i));
				int result = batchManageRepos
						.insertStepRelyInfo(stepRelyKey_tmp);
				count += result;
			}
			if (count != dependencyStepList.size()) {
				ResultMessages messages = ResultMessages.error();
				messages.add("e.sysrunner.0009");
				throw new BusinessException(messages);
			}
			return 1;
		}
	}

	@Override
	@Transactional
	public int insertNewStepExclInfo(BatStepInfoVO batStepInfoVO) {
		List<String> excludeStepList = batStepInfoVO.getExcludeStepList();
		if (null == excludeStepList || excludeStepList.size() == 0) {
			return 1;
		} else {
			BatStepInfo stepInfo = batStepInfoVO.getBatStepInfo();
			int count = 0;
			for (int i = 0; i < excludeStepList.size(); i++) {
				List<BatStepExclKey> batStepExclKeys = batchManageRepos
						.queryAllStepExclList();
				BigDecimal group_Id;
				if (null != batStepExclKeys && batStepExclKeys.size() > 0) {
					BigDecimal maxID_before = batStepExclKeys.get(
							batStepExclKeys.size() - 1).getGroupId();
					group_Id = maxID_before.add(new BigDecimal(1));
				} else {
					group_Id = new BigDecimal(0);
				}
				BatStepExclKey stepExclKey_tmp = new BatStepExclKey();
				stepExclKey_tmp.setTaskId(stepInfo.getTaskId());
				stepExclKey_tmp.setStepId(stepInfo.getStepId());
				stepExclKey_tmp.setGroupId(group_Id);
				int result1 = batchManageRepos
						.insertStepExclInfo(stepExclKey_tmp);
				stepExclKey_tmp.setStepId(excludeStepList.get(i));
				int result2 = batchManageRepos
						.insertStepExclInfo(stepExclKey_tmp);
				if (2 == result1 + result2) {
					count += 1;
				}

			}
			if (count != excludeStepList.size()) {
				ResultMessages messages = ResultMessages.error();
				messages.add("e.sysrunner.0010");
				throw new BusinessException(messages);
			}
			return 1;
		}
	}

	@Override
	public BatStepInfoVO queryBatStepVOInfo(String taskId, String stepId) {
		BatStepInfoVO batStepVO = new BatStepInfoVO();
		BatStepInfo result_BatStepInfo = batchManageRepos.queryBatStepInfo(
				taskId, stepId);
		batStepVO.setBatStepInfo(result_BatStepInfo);
		List<BatStepInfo> batStepAllList = batchManageRepos
				.queryBatStepInfoList(taskId);
		// 组装依赖列表信息
		List<BatStepRelyKey> batStepRelyList = batchManageRepos
				.queryBatStepRelyList(taskId, stepId);
		List<BatStepRelyKey> batRelyStepList = batchManageRepos
				.queryRelyStepList(taskId, stepId);
		List<String> dependencyStepList = new ArrayList<String>();
		List<String> unDependencyStepList = new ArrayList<String>();
		if (null != batStepAllList && batStepAllList.size() > 0) {
			if (null != batStepRelyList && batStepRelyList.size() > 0) {
				for (int i = 0; i < batStepAllList.size(); i++) {
					BatStepInfo batStepInfo_tmp = batStepAllList.get(i);
					if (batStepInfo_tmp.getStepId().equals(stepId)) {
						batStepAllList.remove(i);
						i = i - 1;
						continue;
					}
					for (int j = 0; j < batStepRelyList.size(); j++) {
						BatStepRelyKey batStepRelyKey_tmp = batStepRelyList
								.get(j);
						if (batStepRelyKey_tmp.getRelyStepId().equals(
								batStepInfo_tmp.getStepId())) {
							dependencyStepList.add(batStepInfo_tmp.getStepId());
							batStepAllList.remove(batStepInfo_tmp);
							i = i - 1;
							continue;
						}
					}
					for (int k = 0; k < batRelyStepList.size(); k++) {
						BatStepRelyKey batStepRelyKey_tmp = batRelyStepList
								.get(k);
						if (batStepRelyKey_tmp.getStepId().equals(
								batStepInfo_tmp.getStepId())) {
							batStepAllList.remove(batStepInfo_tmp);
							i = i - 1;
							continue;
						}
					}
				}

				batStepVO.setDependencyStepList(dependencyStepList);
				for (int i = 0; i < batStepAllList.size(); i++) {
					unDependencyStepList.add(batStepAllList.get(i).getStepId());
				}
				batStepVO.setUnDependencyStepList(unDependencyStepList);

			} else {
				if (null != batRelyStepList && batRelyStepList.size() > 0) {
					for (int i = 0; i < batStepAllList.size(); i++) {
						BatStepInfo batStepInfo_tmp = batStepAllList.get(i);
						if (batStepInfo_tmp.getStepId().equals(stepId)) {
							batStepAllList.remove(i);
							i = i - 1;
							continue;
						}
						for (int k = 0; k < batRelyStepList.size(); k++) {
							BatStepRelyKey batStepRelyKey_tmp = batRelyStepList
									.get(k);
							if (batStepRelyKey_tmp.getStepId().equals(
									batStepInfo_tmp.getStepId())) {
								batStepAllList.remove(batStepInfo_tmp);
								i = i - 1;
								continue;
							}
						}
					}
				} else {
					for (int i = 0; i < batStepAllList.size(); i++) {
						BatStepInfo batStepInfo_tmp = batStepAllList.get(i);
						if (batStepInfo_tmp.getStepId().equals(stepId)) {
							batStepAllList.remove(i);
							i = i - 1;
							continue;
						}
					}
				}
				batStepVO.setDependencyStepList(dependencyStepList);
				for (int i = 0; i < batStepAllList.size(); i++) {
					unDependencyStepList.add(batStepAllList.get(i).getStepId());
				}
				batStepVO.setUnDependencyStepList(unDependencyStepList);
			}
		} else {
			batStepVO.setDependencyStepList(dependencyStepList);
			batStepVO.setUnDependencyStepList(unDependencyStepList);
		}

		// 组装排他列表信息
		List<String> excludeStepList = new ArrayList<String>();
		List<String> unExcludeStepList = new ArrayList<String>();
		List<BatStepExclKey> batStepExclKeys = batchManageRepos
				.queryStepExclListByKey(taskId, stepId);
		List<BatStepInfo> batStepAllList2 = batchManageRepos
				.queryBatStepInfoList(taskId);
		if (null != batStepAllList2 && batStepAllList2.size() > 0) {
			if (null != batStepExclKeys && batStepExclKeys.size() > 0) {
				for (int i = 0; i < batStepAllList2.size(); i++) {
					for (int j = 0; j < batStepExclKeys.size(); j++) {
						if (batStepAllList2.get(i).getStepId()
								.equals(batStepExclKeys.get(j).getStepId())) {
							batStepAllList2.remove(i);
							if (i == 0) {
								i = 0;
							} else {
								i = i - 1;
							}
							continue;
						}
						if (stepId.equals(batStepAllList2.get(i).getStepId())) {
							batStepAllList2.remove(i);
							if (i == 0) {
								i = 0;
							} else {
								i = i - 1;
							}
							continue;
						}
					}
				}
				for (int i = 0; i < batStepAllList2.size(); i++) {
					unExcludeStepList.add(batStepAllList2.get(i).getStepId());
				}
				for (int i = 0; i < batStepExclKeys.size(); i++) {
					excludeStepList.add(batStepExclKeys.get(i).getStepId());
				}
				batStepVO.setExcludeStepList(excludeStepList);
				batStepVO.setUnExcludeStepList(unExcludeStepList);

			} else {
				for (int i = 0; i < batStepAllList2.size(); i++) {
					if (stepId.equals(batStepAllList2.get(i).getStepId())) {
						continue;
					} else {
						unExcludeStepList.add(batStepAllList2.get(i)
								.getStepId());
					}
				}
				batStepVO.setExcludeStepList(excludeStepList);
				batStepVO.setUnExcludeStepList(unExcludeStepList);
			}
		} else {
			batStepVO.setExcludeStepList(excludeStepList);
			batStepVO.setUnExcludeStepList(unExcludeStepList);
		}

		return batStepVO;
	}

	@Override
	@Transactional
	public int deleteStepInfo(String taskId, String stepId) {
		// query this step is relied by other step or not
		List<BatStepRelyKey> batStepRelyKeysList = batchManageRepos
				.queryRelyStepList(taskId, stepId);
		if (null != batStepRelyKeysList && batStepRelyKeysList.size() > 0) {
			ResultMessages messages = ResultMessages.error();
			StringBuffer err = new StringBuffer();
			for (int i = 0; i < batStepRelyKeysList.size(); i++) {
				BatStepRelyKey batStepRelyKey_tmp = batStepRelyKeysList.get(i);
				err.append(batStepRelyKey_tmp.getStepId() + ",");
			}
			messages.add("e.sysrunner.0001", err);
			throw new BusinessException(messages);
		} else {
			batchManageRepos.deleteStepRelyByStepId(taskId, stepId);
			batchManageRepos.deleteStepExclByStepId(taskId, stepId);
			return batchManageRepos.deleteStepInfo(taskId, stepId);
		}
	}

	@Override
	@Transactional
	public int updateStepInfo(BatStepInfoVO batStepInfoVO) {
		ResultMessages messages = ResultMessages.error();
		List<String> dependencyStepStringList = batStepInfoVO
				.getDependencyStepList();
		List<BatStepInfo> batStepInfos = new ArrayList<BatStepInfo>();
		BatStepInfo batStepInfo = batStepInfoVO.getBatStepInfo();
		if (null != dependencyStepStringList
				&& dependencyStepStringList.size() > 0) {
			for (int i = 0; i < dependencyStepStringList.size(); i++) {
				BatStepInfo stepInfo_tmp = this.queryBatStepInfo(
						batStepInfo.getTaskId(),
						dependencyStepStringList.get(i));
				batStepInfos.add(stepInfo_tmp);
			}
		}

		if (null != batStepInfos && batStepInfos.size() > 0) {
			int relyCount = 0;
			batchManageRepos.deleteStepRelyByStepId(batStepInfo.getTaskId(),
					batStepInfo.getStepId());
			for (int i = 0; i < batStepInfos.size(); i++) {
				BatStepRelyKey stepRelytmp = new BatStepRelyKey();
				stepRelytmp.setTaskId(batStepInfo.getTaskId());
				stepRelytmp.setStepId(batStepInfo.getStepId());
				stepRelytmp.setRelyStepId(batStepInfos.get(i).getStepId());
				batchManageRepos.insertStepRelyInfo(stepRelytmp);
				relyCount++;
			}
			if (relyCount != batStepInfos.size()) {
				messages.add("e.sysrunner.0004");
				throw new BusinessException(messages);
			}
		} else {
			batchManageRepos.deleteStepRelyByStepId(batStepInfo.getTaskId(),
					batStepInfo.getStepId());
		}

		batchManageRepos.deleteStepExclByStepId(batStepInfo.getTaskId(),
				batStepInfo.getStepId());

		List<String> excludeStepStringList = batStepInfoVO.getExcludeStepList();
		if (null != excludeStepStringList && excludeStepStringList.size() > 0) {
			List<BatStepExclKey> batStepExclKeys = batchManageRepos
					.queryAllStepExclList();
			BigDecimal group_Id;
			if (null != batStepExclKeys && batStepExclKeys.size() > 0) {
				BigDecimal maxID_before = batStepExclKeys.get(
						batStepExclKeys.size() - 1).getGroupId();
				group_Id = maxID_before.add(new BigDecimal(1));
			} else {
				group_Id = new BigDecimal(0);
			}
			int exclCount = 0;
			for (int i = 0; i < excludeStepStringList.size(); i++) {

				BatStepExclKey batStepExclKey = new BatStepExclKey();
				batStepExclKey.setTaskId(batStepInfo.getTaskId());
				batStepExclKey.setStepId(excludeStepStringList.get(i));
				batStepExclKey.setGroupId(group_Id);
				batchManageRepos.insertStepExclInfo(batStepExclKey);
				exclCount++;
			}
			if (exclCount != excludeStepStringList.size()) {
				messages.add("e.sysrunner.0011");
				throw new BusinessException(messages);
			}
		}

		DpBatMpp result_dpBatMpp = batchManageRepos.quertDpBatMpp(
				batStepInfo.getTaskId(), batStepInfo.getStepId());
		if (null == result_dpBatMpp) {
			DpBatMpp insert_DpBatMpp = new DpBatMpp();
			insert_DpBatMpp.setBatTaskId(batStepInfo.getTaskId());
			insert_DpBatMpp.setBatStepId(batStepInfo.getStepId());
			insert_DpBatMpp.setMapType(batStepInfo.getDpCfgType());
			insert_DpBatMpp.setDpCfgId(batStepInfo.getDpCfgId());
			batchManageRepos.insertDpBatMpp(insert_DpBatMpp);
		} else {
			DpBatMpp update_DpBatMpp = new DpBatMpp();
			update_DpBatMpp.setBatTaskId(batStepInfo.getTaskId());
			update_DpBatMpp.setBatStepId(batStepInfo.getStepId());
			update_DpBatMpp.setMapType(batStepInfo.getDpCfgType());
			update_DpBatMpp.setDpCfgId(batStepInfo.getDpCfgId());
			batchManageRepos.updateDpBatMpp(update_DpBatMpp);
		}

		int result = batchManageRepos.updateStepInfo(batStepInfo);
		if (result < 1) {
			messages.add("e.sysrunner.0005");
			throw new BusinessException(messages);
		}
		return result;
	}

	@Override
	public List<DpExpCfg> queryDpExpCfgList(DpExpCfg dpExpCfg) {
		return batchManageRepos.queryDpExpCfgList(dpExpCfg);
	}

	@Override
	public List<DpImpCfg> queryDpImpCfgList(DpImpCfg dpImpCfg) {
		return batchManageRepos.queryDpImpCfgList(dpImpCfg);
	}

	@Override
	public List<DpMppCfg> queryDpMppCfgList(DpMppCfg mppCfg) {
		return batchManageRepos.queryDpMppCfgList(mppCfg);
	}

	@Override
	public DpBatMpp quertDpBatMpp(String taskId, String stepId) {
		return batchManageRepos.quertDpBatMpp(taskId, stepId);
	}

	@Override
	public DpExpCfg queryDpExpCfgByExpId(String id) {
		DpExpCfg dpExpCfg = new DpExpCfg();
		dpExpCfg.setExpId(id);
		return batchManageRepos.queryDpExpCfgByExpId(dpExpCfg);
	}

	@Override
	public DpImpCfg queryDpImpCfg(String id) {
		return batchManageRepos.queryDpImpCfg(id);
	}

	@Override
	public DpMppCfg queryDpMppCfgByMppId(String id) {
		DpMppCfg dpMppCfg = new DpMppCfg();
		dpMppCfg.setMppId(id);
		return batchManageRepos.queryDpMppCfgByMppId(dpMppCfg);
	}

	@Override
	public Page<BatDimInfo> queryBatDimInfoByPage(Pageable pageable,
			BatDimInfo query_BatDimInfo) {
		Page<BatDimInfo> page = null;
		page = batchManageRepos.queryBatDimInfoByPage(pageable,
				query_BatDimInfo);
		return page;
	}

	@Override
	public BatDimInfo queryBatDimInfo(BatDimInfo batDimInfo) {
		return batchManageRepos.queryBatDimInfo(batDimInfo);
	}

	@Override
	public BatTaskDim queryBatTaskDim(BatTaskDim batTaskDim) {
		return batchManageRepos.queryBatTaskDim(batTaskDim);
	}

	@Override
	@Transactional
	public int insertBatTaskDim(BatTaskDim batTaskDim) {
		return batchManageRepos.insertBatTaskDim(batTaskDim);
	}

	@Override
	@Transactional
	public int updateBatTaskDim(BatTaskDim batTaskDim) {
		return batchManageRepos.updateBatTaskDim(batTaskDim);
	}

	@Override
	public BatDimInfo queryBatDimInfoByTypeId(BatDimInfo batDimInfo) {
		return batchManageRepos.queryBatDimInfoByTypeId(batDimInfo);
	}

	@Override
	@Transactional
	public int deleteBatDimInfo(BatDimInfo batDimInfo) {
		return batchManageRepos.deleteBatDimInfo(batDimInfo);
	}

	@Override
	@Transactional
	public int insertBatDimInfo(BatDimInfo batDimInfo) {
		return batchManageRepos.insertBatDimInfo(batDimInfo);
	}

	@Override
	public List<BatTaskDim> queryBatTaskDims(BatTaskDim batTaskDim) {
		return batchManageRepos.queryBatTaskDims(batTaskDim);
	}

	@Override
	public List<BatDimInfo> queryBatDimInfos(BatDimInfo batDimInfo) {
		return batchManageRepos.queryBatDimInfos(batDimInfo);
	}

	@Override
	@Transactional
	public int addBatTaskDims(List<BatTaskDim> insert_BatTaskDims) {
		int insert_count = 0;
		if (null != insert_BatTaskDims) {
			BatTaskDim batTaskDim = new BatTaskDim();
			batTaskDim.setTaskId(insert_BatTaskDims.get(0).getTaskId().trim());
			batTaskDim.setDimTypeId(insert_BatTaskDims.get(0).getDimTypeId()
					.trim());
			this.deleteBatTaskDimByTaskId(batTaskDim);
			for (int i = 0; i < insert_BatTaskDims.size(); i++) {
				this.insertBatTaskDim(insert_BatTaskDims.get(i));
				insert_count++;
			}
			if (insert_count == insert_BatTaskDims.size()) {
				return 1;
			} else {
				return 0;
			}
		}
		return 0;
	}

	@Override
	@Transactional
	public int deleteBatTaskDimByTaskId(BatTaskDim batTaskDim) {
		return batchManageRepos.deleteBatTaskDimByTaskId(batTaskDim);
	}

	public BatchManageForm transTimeFmtRemove(BatchManageForm form) {
		BatTaskInfo batTaskInfo = form.getBatTaskInfo();
		if (null != batTaskInfo) {
			batTaskInfo.setTaskStartTime(DateUtil
					.getFormatTimeRemoveColon(batTaskInfo.getTaskStartTime()));
			batTaskInfo.setTaskEndTime(DateUtil
					.getFormatTimeRemoveColon(batTaskInfo.getTaskEndTime()));
		}
		form.setQuery_taskStartTimeStart(DateUtil.getFormatTimeRemoveColon(form
				.getQuery_taskStartTimeStart()));
		form.setQuery_taskStartTimeEnd(DateUtil.getFormatTimeRemoveColon(form
				.getQuery_taskStartTimeEnd()));
		return form;
	}

	public BatchManageForm transTimeFmtAdd(BatchManageForm form) {
		BatTaskInfo batTaskInfo = form.getBatTaskInfo();
		if (null != batTaskInfo) {
			batTaskInfo.setTaskStartTime(DateUtil
					.getFormatTimeAddColon(batTaskInfo.getTaskStartTime()));
			batTaskInfo.setTaskEndTime(DateUtil
					.getFormatTimeAddColon(batTaskInfo.getTaskEndTime()));
		}
		form.setQuery_taskStartTimeStart(DateUtil.getFormatTimeAddColon(form
				.getQuery_taskStartTimeStart()));
		form.setQuery_taskStartTimeEnd(DateUtil.getFormatTimeAddColon(form
				.getQuery_taskStartTimeEnd()));
		return form;
	}

	@Resource
	protected BatchManageRepository batchManageRepos;

}
