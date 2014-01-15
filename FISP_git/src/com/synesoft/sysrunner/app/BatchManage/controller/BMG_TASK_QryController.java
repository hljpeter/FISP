package com.synesoft.sysrunner.app.BatchManage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm;
import com.synesoft.sysrunner.common.constant.DateUtil;
import com.synesoft.sysrunner.domain.model.BatTaskInfo;
import com.synesoft.sysrunner.domain.service.BatchManageService;

@Controller
@RequestMapping(value = "BMG_TASK_Qry")
public class BMG_TASK_QryController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public BatchManageForm setUpForm() {
		return new BatchManageForm();
	}

	@RequestMapping("Init")
	public String init(BatchManageForm batchManageForm, Model model) {
		return "sysrunner/BMG_TASK_Qry";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("Qry")
	public String query(BatchManageForm batchManageForm,
			@PageableDefaults Pageable pageable, Model model) {
		batchManageServ.transTimeFmtRemove(batchManageForm);

		BatTaskInfo query_batTaskInfo = new BatTaskInfo();
		query_batTaskInfo.setTaskName(batchManageForm.getQuery_task_name());
		query_batTaskInfo
				.setTaskStartType(batchManageForm.getQuery_task_type());
		query_batTaskInfo.setTaskStartTimeStart(batchManageForm
				.getQuery_taskStartTimeStart());
		query_batTaskInfo.setTaskStartTimeEnd(batchManageForm
				.getQuery_taskStartTimeEnd());
		if(null!=batchManageForm.getQuery_task_start_day() && !"".equals(batchManageForm.getQuery_task_start_day())){
			query_batTaskInfo.setTaskStartDay(Short.parseShort(batchManageForm.getQuery_task_start_day()));
		}

		Page page = batchManageServ.queryBatTaskInfoByPage(pageable,
				query_batTaskInfo);
		List<BatTaskInfo> taskInfos = page.getContent();
		for (int i = 0; i < taskInfos.size(); i++) {
			BatTaskInfo taskInfo = taskInfos.get(i);
			taskInfo.setTaskStartTime(DateUtil.getFormatTimeAddColon(taskInfo
					.getTaskStartTime()));
		}

		if (page.getContent().size() > 0) {
			model.addAttribute("page", page);
			batchManageServ.transTimeFmtAdd(batchManageForm);
			return "sysrunner/BMG_TASK_Qry";
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			batchManageServ.transTimeFmtAdd(batchManageForm);
			return "sysrunner/BMG_TASK_Qry";
		}
	}

	@RequestMapping("Del")
	public String taskDelete(BatchManageForm batchManageForm, Model model) {

		BatTaskInfo del_batTaskInfo = new BatTaskInfo();
		del_batTaskInfo.setTaskId(batchManageForm.getDel_task_id());
		// del selected task
		int i = batchManageServ.deleteTaskInfo(del_batTaskInfo);

		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0002"));
			return "forward:/BMG_TASK_Qry/Qry";
		}
		model.addAttribute(ResultMessages.success().add("i.sysrunner.0002"));
		return "forward:/BMG_TASK_Qry/Qry";
	}

	@Resource
	private BatchManageService batchManageServ;

}
