package com.synesoft.sysrunner.app.BatchMonitor.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synesoft.sysrunner.app.BatchMonitor.model.BatchMonitorForm;
import com.synesoft.sysrunner.common.constant.DateUtil;
import com.synesoft.sysrunner.domain.service.BatchMonitorService;
import com.synesoft.sysrunner.domain.vo.BatTaskRtmVO;


@Controller
@RequestMapping(value = "BMR_TASK")
public class BMR_TASKController {
	
	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public BatchMonitorForm setUpForm() {
		return new BatchMonitorForm();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("Init")
	public String taskMonitorInit(BatchMonitorForm batchMonitorForm, Model model) {
		String date =batchMonitorServ.getBatCtl().getBatDate();
		batchMonitorForm.setQuery_bat_date(DateUtil.getdateAddSplit(date));
		batchMonitorForm.setDay_task_flag("1");
		batchMonitorForm.setMonth_task_flag("1");
		batchMonitorForm.setSeason_task_flag("1");
		batchMonitorForm.setYear_task_flag("1");
		batchMonitorForm.setCus_task_flag("1");
		Map map = batchMonitorServ.getBatTaskRtmList(date.replace("-", ""));
		model.addAttribute("dayTaskRtmList",
				(List<BatTaskRtmVO>) map.get("dayTaskRtmList"));
		model.addAttribute("monthTaskRtmList",
				(List<BatTaskRtmVO>) map.get("monthTaskRtmList"));
		model.addAttribute("seasonTaskRtmList",
				(List<BatTaskRtmVO>) map.get("seasonTaskRtmList"));
		model.addAttribute("yearTaskRtmList",
				(List<BatTaskRtmVO>) map.get("yearTaskRtmList"));
		model.addAttribute("cusTaskRtmList",
				(List<BatTaskRtmVO>) map.get("cusTaskRtmList"));
		return "sysrunner/BMR_TASK";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("Qry")
	public String taskMonitor(BatchMonitorForm batchMonitorForm, Model model) {
		Map map = batchMonitorServ.getBatTaskRtmList(DateUtil.getFormatDateRemoveSprit(batchMonitorForm.getQuery_bat_date()));
		model.addAttribute("dayTaskRtmList",
				(List<BatTaskRtmVO>) map.get("dayTaskRtmList"));
		model.addAttribute("monthTaskRtmList",
				(List<BatTaskRtmVO>) map.get("monthTaskRtmList"));
		model.addAttribute("seasonTaskRtmList",
				(List<BatTaskRtmVO>) map.get("seasonTaskRtmList"));
		model.addAttribute("yearTaskRtmList",
				(List<BatTaskRtmVO>) map.get("yearTaskRtmList"));
		model.addAttribute("cusTaskRtmList",
				(List<BatTaskRtmVO>) map.get("cusTaskRtmList"));
		return "sysrunner/BMR_TASK";
	}

	@Resource
	private BatchMonitorService batchMonitorServ;
}
