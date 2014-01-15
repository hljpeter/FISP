package com.synesoft.sysrunner.app.BatchManage.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm;
import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm.BMG_STEP_Add;
import com.synesoft.sysrunner.common.util.StringUtil;
import com.synesoft.sysrunner.domain.model.BatStepInfo;
import com.synesoft.sysrunner.domain.model.BatTaskInfo;
import com.synesoft.sysrunner.domain.service.BatchManageService;
import com.synesoft.sysrunner.domain.vo.BatStepInfoVO;

@Controller
@RequestMapping(value = "BMG_STEP_Add")
public class BMG_STEP_AddController {
	
	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public BatchManageForm setUpForm() {
		return new BatchManageForm();
	}
	
	@RequestMapping("Init")
	public String stepAddInit(@RequestParam("taskId") String taskId,
			BatchManageForm batchManageForm, Model model) {
		BatStepInfo stepInfo = new BatStepInfo();
		stepInfo.setTaskId(taskId);
		BatStepInfoVO batStepVO = batchManageServ.queryBatStepRelyExclInfo(taskId);
		List<BatStepInfo> unDependencyStepListShow = new ArrayList<BatStepInfo>();
		List<BatStepInfo> unExcludeStepListShow = new ArrayList<BatStepInfo>();
		batchManageForm.setDependencyStepList(new ArrayList<String>());
		batchManageForm.setDependencyStepListShow(new ArrayList<BatStepInfo>());
		batchManageForm.setExcludeStepList(new ArrayList<String>());
		batchManageForm.setExcludeStepListShow(new ArrayList<BatStepInfo>());
		if (null != batStepVO.getUnDependencyStepList()) {
			for (int i = 0; i < batStepVO.getUnDependencyStepList().size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageServ.queryBatStepInfo(
						taskId, batStepVO.getUnDependencyStepList().get(i));
				unDependencyStepListShow.add(stepInfo_tmp);
			}
			batchManageForm.setUnDependencyStepList(batStepVO
					.getUnDependencyStepList());
			batchManageForm
					.setUnDependencyStepListShow(unDependencyStepListShow);
		}
		if (null != batStepVO.getUnExcludeStepList()) {
			for (int i = 0; i < batStepVO.getUnExcludeStepList().size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageServ.queryBatStepInfo(
						taskId, batStepVO.getUnDependencyStepList().get(i));
				unExcludeStepListShow.add(stepInfo_tmp);
			}
			batchManageForm.setUnExcludeStepList(batStepVO
					.getUnExcludeStepList());
			batchManageForm
					.setUnExcludeStepListShow(unExcludeStepListShow);
		}
		
		if (null != batchManageForm.getUnDependencyStepListShow()
				&& batchManageForm.getUnDependencyStepListShow().size() > 0) {
			for (int i = 0; i < batchManageForm.getUnDependencyStepListShow()
					.size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageForm
						.getUnDependencyStepListShow().get(i);
				BatTaskInfo taskInfo = batchManageServ
						.getBatTaskInfo(stepInfo_tmp.getTaskId());
				stepInfo_tmp.setTaskName(taskInfo.getTaskName());
			}
		}
		if (null != batchManageForm.getUnExcludeStepListShow()
				&& batchManageForm.getUnExcludeStepListShow().size() > 0) {
			for (int i = 0; i < batchManageForm.getUnExcludeStepListShow()
					.size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageForm
						.getUnExcludeStepListShow().get(i);
				BatTaskInfo taskInfo = batchManageServ
						.getBatTaskInfo(stepInfo_tmp.getTaskId());
				stepInfo_tmp.setTaskName(taskInfo.getTaskName());
			}
		}

		BatTaskInfo taskInfo = batchManageServ.getBatTaskInfo(taskId);
		stepInfo.setTaskName(taskInfo.getTaskName());
		batchManageForm.setBatStepInfo(stepInfo);
		return "sysrunner/BMG_STEP_Add";
	}
	
	@RequestMapping("SubmitStep")
	public String stepAdd(
			@Validated({ BMG_STEP_Add.class }) BatchManageForm batchManageForm,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			this.dataExpress(batchManageForm);
			return "sysrunner/BMG_STEP_Add";
		}
		try {
			List<BatStepInfo> batStepInfos = batchManageServ
					.queryBatStepInfoList(batchManageForm.getBatStepInfo()
							.getTaskId());
			if (null != batStepInfos && batStepInfos.size() > 0) {
				String maxID_before = batStepInfos.get(batStepInfos.size() - 1)
						.getStepId();
				int max_before = Integer.parseInt(maxID_before);
				int max_after = max_before + 1;
				String maxID = StringUtil.addZeroForNum(max_after + "", 5);
				batchManageForm.getBatStepInfo().setStepId(maxID);
			} else {
				String maxID = "00001";
				batchManageForm.getBatStepInfo().setStepId(maxID);
			}
			BatStepInfo stepInfo = batchManageForm.getBatStepInfo();
			if(null==stepInfo.getStepStartDay()){
				stepInfo.setStepStartDay(Short.parseShort("0"));
			}
			if(null==stepInfo.getSubStepNum()){
				stepInfo.setSubStepNum(1);
			}
			if(null==stepInfo.getPriority()){
				stepInfo.setPriority(new BigDecimal(0));
			}
			BatStepInfoVO batStepVO = new BatStepInfoVO();
			batStepVO.setBatStepInfo(stepInfo);
			batStepVO.setDependencyStepList(batchManageForm
					.getDependencyStepList());
			batStepVO.setExcludeStepList(batchManageForm.getExcludeStepList());
			int i = batchManageServ.insertStepInfo(batStepVO);
			if (i < 1) {
				model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
				this.dataExpress(batchManageForm);
				return "sysrunner/BMG_STEP_Add";
			}
		} catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			this.dataExpress(batchManageForm);
			return "sysrunner/BMG_STEP_Add";
		}
		this.dataExpress(batchManageForm);
		model.addAttribute(ResultMessages.success().add("i.sysrunner.0007"));
		return "sysrunner/BMG_STEP_Add";
	}
	
	private BatchManageForm dataExpress(BatchManageForm batchManageForm){
		List<BatStepInfo> unDependencyStepListShow = new ArrayList<BatStepInfo>();
		List<BatStepInfo> dependencyStepListShow = new ArrayList<BatStepInfo>();
		List<BatStepInfo> excludeStepListShow = new ArrayList<BatStepInfo>();
		List<BatStepInfo> unExcludeStepListShow = new ArrayList<BatStepInfo>();
		if (null != batchManageForm.getUnDependencyStepList()) {
			for (int i = 0; i < batchManageForm.getUnDependencyStepList()
					.size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageServ
						.queryBatStepInfo(batchManageForm
								.getBatStepInfo().getTaskId(),
								batchManageForm.getUnDependencyStepList()
										.get(i));
				unDependencyStepListShow.add(stepInfo_tmp);
			}
			batchManageForm
					.setUnDependencyStepListShow(unDependencyStepListShow);
		}
		if (null != batchManageForm.getDependencyStepList()) {
			for (int i = 0; i < batchManageForm.getDependencyStepList()
					.size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageServ
						.queryBatStepInfo(
								batchManageForm.getBatStepInfo().getTaskId(),
								batchManageForm.getDependencyStepList().get(
										i));
				dependencyStepListShow.add(stepInfo_tmp);
			}
			batchManageForm
					.setDependencyStepListShow(dependencyStepListShow);
		}
		if (null != batchManageForm.getUnDependencyStepListShow()
				&& batchManageForm.getUnDependencyStepListShow().size() > 0) {
			for (int i = 0; i < batchManageForm.getUnDependencyStepListShow()
					.size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageForm
						.getUnDependencyStepListShow().get(i);
				BatTaskInfo taskInfo = batchManageServ
						.getBatTaskInfo(stepInfo_tmp.getTaskId());
				stepInfo_tmp.setTaskName(taskInfo.getTaskName());
			}
		}
		if (null != batchManageForm.getDependencyStepListShow()
				&& batchManageForm.getDependencyStepListShow().size() > 0) {
			for (int i = 0; i < batchManageForm.getDependencyStepListShow()
					.size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageForm
						.getDependencyStepListShow().get(i);
				BatTaskInfo taskInfo = batchManageServ
						.getBatTaskInfo(stepInfo_tmp.getTaskId());
				stepInfo_tmp.setTaskName(taskInfo.getTaskName());
			}
		}
		
		
		if (null != batchManageForm.getUnExcludeStepList()) {
			for (int i = 0; i < batchManageForm.getUnExcludeStepList()
					.size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageServ
						.queryBatStepInfo(batchManageForm
								.getBatStepInfo().getTaskId(),
								batchManageForm.getUnExcludeStepList()
										.get(i));
				unExcludeStepListShow.add(stepInfo_tmp);
			}
			batchManageForm
					.setUnExcludeStepListShow(unExcludeStepListShow);
		}
		if (null != batchManageForm.getExcludeStepList()) {
			for (int i = 0; i < batchManageForm.getExcludeStepList()
					.size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageServ
						.queryBatStepInfo(
								batchManageForm.getBatStepInfo().getTaskId(),
								batchManageForm.getExcludeStepList().get(
										i));
				excludeStepListShow.add(stepInfo_tmp);
			}
			batchManageForm
					.setExcludeStepListShow(excludeStepListShow);
		}
		if (null != batchManageForm.getUnExcludeStepListShow()
				&& batchManageForm.getUnExcludeStepListShow().size() > 0) {
			for (int i = 0; i < batchManageForm.getUnExcludeStepListShow()
					.size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageForm
						.getUnExcludeStepListShow().get(i);
				BatTaskInfo taskInfo = batchManageServ
						.getBatTaskInfo(stepInfo_tmp.getTaskId());
				stepInfo_tmp.setTaskName(taskInfo.getTaskName());
			}
		}
		if (null != batchManageForm.getExcludeStepListShow()
				&& batchManageForm.getExcludeStepListShow().size() > 0) {
			for (int i = 0; i < batchManageForm.getExcludeStepListShow()
					.size(); i++) {
				BatStepInfo stepInfo_tmp = batchManageForm
						.getExcludeStepListShow().get(i);
				BatTaskInfo taskInfo = batchManageServ
						.getBatTaskInfo(stepInfo_tmp.getTaskId());
				stepInfo_tmp.setTaskName(taskInfo.getTaskName());
			}
		}
		return batchManageForm;
	}
	
	@Resource
	private BatchManageService batchManageServ;


}
