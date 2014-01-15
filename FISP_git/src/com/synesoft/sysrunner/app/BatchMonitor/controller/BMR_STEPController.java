package com.synesoft.sysrunner.app.BatchMonitor.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.fw.common.message.ResultMessages;

import cn.com.synesoft.jsynframe.batch.batcommand.BatchCommand;

import com.synesoft.fisp.domain.model.DpExpLog;
import com.synesoft.fisp.domain.model.DpImpLog;
import com.synesoft.fisp.domain.model.DpMppLog;
import com.synesoft.sysrunner.app.BatchMonitor.model.BatchMonitorForm;
import com.synesoft.sysrunner.common.constant.CommonConst;
import com.synesoft.sysrunner.domain.model.BatStepRtm;
import com.synesoft.sysrunner.domain.model.BatTaskInfo;
import com.synesoft.sysrunner.domain.model.DpBatMpp;
import com.synesoft.sysrunner.domain.service.BatchManageService;
import com.synesoft.sysrunner.domain.service.BatchMonitorService;

@Controller
@RequestMapping(value = "BMR_STEP")
public class BMR_STEPController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public BatchMonitorForm setUpForm() {
		return new BatchMonitorForm();
	}

	@RequestMapping("Init")
	public String stepMonitorInit(@RequestParam("taskId") String taskId,
			@RequestParam("runtimeId") String runtimeId,
			BatchMonitorForm batchMonitorForm, Model model) {
		List<BatStepRtm> batStepRtmList = batchMonitorServ.queryBatStepRtm(
				runtimeId, taskId);
		if (null != batStepRtmList && batStepRtmList.size() > 0) {
			for (int i = 0; i < batStepRtmList.size(); i++) {
				BatTaskInfo batTaskInfo = batchManageServ
						.getBatTaskInfo(taskId);
				batStepRtmList.get(i).setTaskName(batTaskInfo.getTaskName());
			}
		}
		model.addAttribute("stepList", batStepRtmList);
		batchMonitorForm.setTaskId(taskId);
		batchMonitorForm.setRuntimeId(runtimeId);
		return "sysrunner/BMR_STEP";
	}

	@RequestMapping("skipstep")
	public String skipstep(BatchMonitorForm batchMonitorForm,
			RedirectAttributes attr, Model model) {
		try {
			BatchCommand batchCommand = new BatchCommand();
			batchCommand.skipStep(
					Long.parseLong(batchMonitorForm.getRuntimeId()),
					batchMonitorForm.getTaskId());
		} catch (Exception e) {

		}

		model.addAttribute(ResultMessages.success().add("i.sysrunner.0008"));
		return "forward:/BMR_STEP/Init";
	}

	@RequestMapping("fixstep")
	public String fixstep(BatchMonitorForm batchMonitorForm,
			RedirectAttributes attr, Model model) {
		try {
			BatchCommand batchCommand = new BatchCommand();
			batchCommand.fixStep(
					Long.parseLong(batchMonitorForm.getRuntimeId()),
					batchMonitorForm.getTaskId());
		} catch (Exception e) {

		}

		model.addAttribute(ResultMessages.success().add("i.sysrunner.0009"));
		return "forward:/BMR_STEP/Init";
	}

	@RequestMapping("WorkInfo")
	public String stepWorkInfo(@RequestParam("stepId") String stepId,
			@RequestParam("taskId") String taskId,
			@RequestParam("runtimeId") String runtimeId,
			BatchMonitorForm batchMonitorForm, Model model) {
		
		DpBatMpp dpBatMpp =batchManageServ.quertDpBatMpp(taskId, stepId);
		if(null !=dpBatMpp){
			if(CommonConst.CFG_TYPE_IMPORT.equals(dpBatMpp.getMapType())){
				DpImpLog query_dpImpLog = new DpImpLog();
				query_dpImpLog.setRsv1(runtimeId);
				query_dpImpLog.setRsv2(stepId);
				DpImpLog result_dpImpLog = batchMonitorServ.queryDpImpLogByRunTimeId(query_dpImpLog);
				batchMonitorForm.setDpImpLog(result_dpImpLog);
				return "sysrunner/BMR_STEP_DpImpLog";
			}else if(CommonConst.CFG_TYPE_MPPING.equals(dpBatMpp.getMapType())){
				DpMppLog query_dpMppLog = new DpMppLog();
				query_dpMppLog.setRsv1(runtimeId);
				query_dpMppLog.setRsv2(stepId);
				DpMppLog result_dpMppLog = batchMonitorServ.queryDpMppLogByRunTimeId(query_dpMppLog);
				batchMonitorForm.setDpMppLog(result_dpMppLog);
				return "sysrunner/BMR_STEP_DpMppLog";
			}else if(CommonConst.CFG_TYPE_EXPORT.equals(dpBatMpp.getMapType())){
				DpExpLog query_DpExpLog = new DpExpLog();
				query_DpExpLog.setRsv1(runtimeId);
				query_DpExpLog.setRsv2(stepId);
				DpExpLog result_DpExpLog = batchMonitorServ.queryDpExpLogByRunTimeId(query_DpExpLog);
				batchMonitorForm.setDpExpLog(result_DpExpLog);
				return "sysrunner/BMR_STEP_DpExpLog";
			}else{
				model.addAttribute(ResultMessages.error().add("e.sysrunner.0021"));
				return "forward:/BMR_STEP/Init";
			}
		}else{
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0021"));
			return "forward:/BMR_STEP/Init";
		}
	}

	@Resource
	private BatchMonitorService batchMonitorServ;

	@Resource
	private BatchManageService batchManageServ;

}
