package com.synesoft.sysrunner.app.BatchManage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm;
import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm.BMG_TASK_Add;
import com.synesoft.sysrunner.domain.model.BatStepInfo;
import com.synesoft.sysrunner.domain.model.BatTaskInfo;
import com.synesoft.sysrunner.domain.service.BatchManageService;

@Controller
@RequestMapping(value = "BMG_TASK_Upt")
public class BMG_TASK_UptController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public BatchManageForm setUpForm() {
		return new BatchManageForm();
	}

	@RequestMapping("Init")
	public String taskUpdateInit(@RequestParam("taskId") String taskId,
			@RequestParam("ReflashFlag") String ReflashFlag,
			BatchManageForm batchManageForm, Model model) {
		if (!"1".equals(ReflashFlag)) {
			BatTaskInfo query_batTaskInfo = new BatTaskInfo();
			query_batTaskInfo.setTaskId(taskId);
			BatTaskInfo result_batTaskInfo = batchManageServ
					.getBatTaskInfo(taskId);
			batchManageForm.setBatTaskInfo(result_batTaskInfo);
		}
		if ("1".equals(ReflashFlag)) {
			batchManageServ.transTimeFmtRemove(batchManageForm);
		}
		BatStepInfo query_BatStepInfo = new BatStepInfo();
		query_BatStepInfo.setTaskId(taskId);
		List<BatStepInfo> batStepInfos = batchManageServ
				.queryBatStepInfoList(query_BatStepInfo);
		model.addAttribute("list", batStepInfos);
		batchManageServ.transTimeFmtAdd(batchManageForm);
		return "sysrunner/BMG_TASK_Upt";
	}

	@RequestMapping("SubmitTask")
	public String taskUpt(
			@Validated({ BMG_TASK_Add.class }) BatchManageForm batchManageForm,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "sysrunner/BMG_TASK_Upt";
		}
		batchManageServ.transTimeFmtRemove(batchManageForm);
		BatTaskInfo mod_batTaskInfo = new BatTaskInfo();
		BeanUtils.copyProperties(batchManageForm.getBatTaskInfo(), mod_batTaskInfo);
		// update modifed task
		if (null == mod_batTaskInfo.getDimTypeId()
				|| "".equals(mod_batTaskInfo.getDimTypeId())) {
			mod_batTaskInfo.setDimTypeId(" ");
		}
		if (null == mod_batTaskInfo.getTaskStartDay()) {
			mod_batTaskInfo.setTaskStartDay(Short.parseShort("0"));
		}

		if (null == mod_batTaskInfo.getTaskStartTn()) {
			mod_batTaskInfo.setTaskStartTn(Short.parseShort("0"));
		}

		if (null == mod_batTaskInfo.getTaskEndTime()
				|| "".equals(mod_batTaskInfo.getTaskEndTime())) {
			mod_batTaskInfo.setTaskEndTime(" ");
		}

		if (null == mod_batTaskInfo.getTaskPollingInterval()) {
			mod_batTaskInfo.setTaskPollingInterval(Integer.parseInt("0"));
		}

		int i = batchManageServ.updateTaskInfo(mod_batTaskInfo);

		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0003"));
			batchManageServ.transTimeFmtAdd(batchManageForm);
			return "sysrunner/BMG_TASK_Upt";
			
		}

		model.addAttribute(ResultMessages.success().add("i.sysrunner.0001"));
		return "forward:/BMG_TASK_Upt/Init?taskId="+mod_batTaskInfo.getTaskId()+"&ReflashFlag=";
	}

	@Resource
	private BatchManageService batchManageServ;
}
