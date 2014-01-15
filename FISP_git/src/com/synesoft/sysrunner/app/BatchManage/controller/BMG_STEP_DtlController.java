package com.synesoft.sysrunner.app.BatchManage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm;
import com.synesoft.sysrunner.common.constant.CommonConst;
import com.synesoft.sysrunner.domain.model.BatStepInfo;
import com.synesoft.sysrunner.domain.model.BatTaskInfo;
import com.synesoft.sysrunner.domain.model.DpBatMpp;
import com.synesoft.sysrunner.domain.service.BatchManageService;
import com.synesoft.sysrunner.domain.vo.BatStepInfoVO;

@Controller
@RequestMapping(value = "BMG_STEP_Dtl")
public class BMG_STEP_DtlController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public BatchManageForm setUpForm() {
		return new BatchManageForm();
	}

	@RequestMapping("Init")
	public String stepDetail(@RequestParam("taskId") String taskId,
			@RequestParam("stepId") String stepId,
			BatchManageForm batchManageForm, Model model) {
		BatStepInfoVO batStepInfoVO = batchManageServ.queryBatStepVOInfo(
				taskId, stepId);
		BatStepInfo stepInfo = batStepInfoVO.getBatStepInfo();
		BatTaskInfo taskInfo = batchManageServ.getBatTaskInfo(stepInfo
				.getTaskId());
		stepInfo.setTaskName(taskInfo.getTaskName());
		batchManageForm.setBatStepInfo(stepInfo);
		batchManageForm.setDependencyStepList(batStepInfoVO
				.getDependencyStepList());
		batchManageForm.setUnDependencyStepList(batStepInfoVO
				.getUnDependencyStepList());
		batchManageForm.setExcludeStepList(batStepInfoVO.getExcludeStepList());
		batchManageForm.setUnExcludeStepList(batStepInfoVO
				.getUnExcludeStepList());
		List<BatStepInfo> unDependencyStepListShow = new ArrayList<BatStepInfo>();
		List<BatStepInfo> dependencyStepListShow = new ArrayList<BatStepInfo>();
		List<BatStepInfo> excludeStepListShow = new ArrayList<BatStepInfo>();
		List<BatStepInfo> unExcludeStepListShow = new ArrayList<BatStepInfo>();
		if (null != batchManageForm.getUnDependencyStepList()) {
			for (int i = 0; i < batchManageForm.getUnDependencyStepList()
					.size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageServ.queryBatStepInfo(
						batchManageForm.getBatStepInfo().getTaskId(),
						batchManageForm.getUnDependencyStepList().get(i));
				unDependencyStepListShow.add(stepInfo_tmp);
			}
			batchManageForm
					.setUnDependencyStepListShow(unDependencyStepListShow);
		}
		if (null != batchManageForm.getDependencyStepList()) {
			for (int i = 0; i < batchManageForm.getDependencyStepList().size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageServ.queryBatStepInfo(
						batchManageForm.getBatStepInfo().getTaskId(),
						batchManageForm.getDependencyStepList().get(i));
				dependencyStepListShow.add(stepInfo_tmp);
			}
			batchManageForm.setDependencyStepListShow(dependencyStepListShow);
		}
		if (null != batchManageForm.getUnDependencyStepListShow()
				&& batchManageForm.getUnDependencyStepListShow().size() > 0) {
			for (int i = 0; i < batchManageForm.getUnDependencyStepListShow()
					.size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageForm
						.getUnDependencyStepListShow().get(i);

				stepInfo_tmp.setTaskName(taskInfo.getTaskName());
			}
		}
		if (null != batchManageForm.getDependencyStepListShow()
				&& batchManageForm.getDependencyStepListShow().size() > 0) {
			for (int i = 0; i < batchManageForm.getDependencyStepListShow()
					.size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageForm
						.getDependencyStepListShow().get(i);
				stepInfo_tmp.setTaskName(taskInfo.getTaskName());
			}
		}

		if (null != batchManageForm.getUnExcludeStepList()) {
			for (int i = 0; i < batchManageForm.getUnExcludeStepList().size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageServ.queryBatStepInfo(
						batchManageForm.getBatStepInfo().getTaskId(),
						batchManageForm.getUnExcludeStepList().get(i));
				unExcludeStepListShow.add(stepInfo_tmp);
			}
			batchManageForm.setUnExcludeStepListShow(unExcludeStepListShow);
		}
		if (null != batchManageForm.getExcludeStepList()) {
			for (int i = 0; i < batchManageForm.getExcludeStepList().size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageServ.queryBatStepInfo(
						batchManageForm.getBatStepInfo().getTaskId(),
						batchManageForm.getExcludeStepList().get(i));
				excludeStepListShow.add(stepInfo_tmp);
			}
			batchManageForm.setExcludeStepListShow(excludeStepListShow);
		}
		if (null != batchManageForm.getUnExcludeStepListShow()
				&& batchManageForm.getUnExcludeStepListShow().size() > 0) {
			for (int i = 0; i < batchManageForm.getUnExcludeStepListShow()
					.size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageForm
						.getUnExcludeStepListShow().get(i);
				stepInfo_tmp.setTaskName(taskInfo.getTaskName());
			}
		}
		if (null != batchManageForm.getExcludeStepListShow()
				&& batchManageForm.getExcludeStepListShow().size() > 0) {
			for (int i = 0; i < batchManageForm.getExcludeStepListShow().size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageForm
						.getExcludeStepListShow().get(i);
				stepInfo_tmp.setTaskName(taskInfo.getTaskName());
			}
		}
		
		DpBatMpp result_DpBatMpp = batchManageServ.quertDpBatMpp(taskId, stepId);
		if(null != result_DpBatMpp){
			String cfgName="";
			if(CommonConst.CFG_TYPE_IMPORT.equals(result_DpBatMpp.getMapType())){
				DpImpCfg dpImpCfg = batchManageServ.queryDpImpCfg(result_DpBatMpp.getDpCfgId());
				if(null!=dpImpCfg){
					cfgName=dpImpCfg.getComments();
				}
			}else if(CommonConst.CFG_TYPE_MPPING.equals(result_DpBatMpp.getMapType())){
				DpMppCfg dpMppCfg = batchManageServ.queryDpMppCfgByMppId(result_DpBatMpp.getDpCfgId());
				if(null!=dpMppCfg){
					cfgName=dpMppCfg.getMppName();
				}
			}else if(CommonConst.CFG_TYPE_EXPORT.equals(result_DpBatMpp.getMapType())){
				DpExpCfg dpExpCfg = batchManageServ.queryDpExpCfgByExpId(result_DpBatMpp.getDpCfgId());
				if(null!=dpExpCfg){
					cfgName=dpExpCfg.getComments();
				}
			}
			batchManageForm.getBatStepInfo().setDpCfgName(cfgName);
			batchManageForm.getBatStepInfo().setDpCfgId(result_DpBatMpp.getDpCfgId());
			batchManageForm.getBatStepInfo().setDpCfgType(result_DpBatMpp.getMapType());
		}

		return "sysrunner/BMG_STEP_Dtl";
	}

	@RequestMapping("Del")
	public String stepDelete(BatchManageForm batchManageForm, Model model) {
		// del selected step
		try {
			int i = batchManageServ.deleteStepInfo(
					batchManageForm.getDel_task_id(),
					batchManageForm.getDel_step_id());
			if (i < 1) {
				model.addAttribute(ResultMessages.error().add(
						"e.sysrunner.0002"));
				return "sysrunner/BMG_TASK_Upt";
			}
		} catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return "sysrunner/BMG_TASK_Upt";
		}
		model.addAttribute(ResultMessages.success().add("i.sysrunner.0003"));
		return "sysrunner/BMG_TASK_Upt";
	}

	@Resource
	private BatchManageService batchManageServ;

}
