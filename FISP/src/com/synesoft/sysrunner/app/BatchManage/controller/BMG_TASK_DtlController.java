package com.synesoft.sysrunner.app.BatchManage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm;
import com.synesoft.sysrunner.domain.model.BatStepInfo;
import com.synesoft.sysrunner.domain.model.BatTaskInfo;
import com.synesoft.sysrunner.domain.service.BatchManageService;

@Controller
@RequestMapping(value = "BMG_TASK_Dtl")
public class BMG_TASK_DtlController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public BatchManageForm setUpForm() {
		return new BatchManageForm();
	}

	@RequestMapping("Init")
	public String taskDetailInit(@RequestParam("taskId") String taskId,
			BatchManageForm batchManageForm, Model model) {

		BatTaskInfo query_batTaskInfo = new BatTaskInfo();
		query_batTaskInfo.setTaskId(taskId);
		BatTaskInfo result_batTaskInfo = batchManageServ.getBatTaskInfo(taskId);
		batchManageForm.setBatTaskInfo(result_batTaskInfo);
		BatStepInfo query_BatStepInfo = new BatStepInfo();
		query_BatStepInfo.setTaskId(taskId);
		List<BatStepInfo> batStepInfos = batchManageServ
				.queryBatStepInfoList(query_BatStepInfo);
		model.addAttribute("list", batStepInfos);
		batchManageServ.transTimeFmtAdd(batchManageForm);

		return "sysrunner/BMG_TASK_Dtl";
	}

	@Resource
	private BatchManageService batchManageServ;
}
