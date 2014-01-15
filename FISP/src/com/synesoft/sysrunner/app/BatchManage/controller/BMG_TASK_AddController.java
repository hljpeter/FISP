package com.synesoft.sysrunner.app.BatchManage.controller;

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
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm;
import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm.BMG_TASK_Add;
import com.synesoft.sysrunner.common.util.StringUtil;
import com.synesoft.sysrunner.domain.model.BatTaskInfo;
import com.synesoft.sysrunner.domain.service.BatchManageService;

@Controller
@RequestMapping(value = "BMG_TASK_Add")
public class BMG_TASK_AddController {
	
	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public BatchManageForm setUpForm() {
		return new BatchManageForm();
	}

	@RequestMapping("Init")
	public String taskAddInit(BatchManageForm batchManageForm, Model model) {
		return "sysrunner/BMG_TASK_Add";
	}
	
	@RequestMapping("SubmitTask")
	public String taskAdd(
			@Validated({ BMG_TASK_Add.class }) BatchManageForm batchManageForm,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "sysrunner/BMG_TASK_Add";
		}
		try {
			List<BatTaskInfo> batTaskInfos = batchManageServ.queryAllTaskListForMax();
			if (null != batTaskInfos && batTaskInfos.size() > 0) {
				String maxID_before = batTaskInfos.get(batTaskInfos.size() - 1)
						.getTaskId();
				int max_before = Integer.parseInt(maxID_before);
				int max_after = max_before + 1;
				String maxID = StringUtil.addZeroForNum(max_after + "", 5);
				batchManageForm.getBatTaskInfo().setTaskId(maxID.trim());
			} else {
				String maxID = "00001";
				batchManageForm.getBatTaskInfo().setTaskId(maxID.trim());
			}

			batchManageServ.transTimeFmtRemove(batchManageForm);
			BatTaskInfo batTaskInfo = batchManageForm.getBatTaskInfo();
			
			if(null==batTaskInfo.getDimTypeId() || "".equals(batTaskInfo.getDimTypeId())){
				batTaskInfo.setDimTypeId(" ");
			}
			
			if(null ==batTaskInfo.getTaskStartDay()){
				batTaskInfo.setTaskStartDay(Short.parseShort("0"));
			}
			
			if(null == batTaskInfo.getTaskStartTn()){
				batTaskInfo.setTaskStartTn(Short.parseShort("0"));
			}
			
			if(null == batTaskInfo.getTaskEndTime() || "".equals(batTaskInfo.getTaskEndTime())){
				batTaskInfo.setTaskEndTime(" ");
			}
			
			if(null == batTaskInfo.getTaskPollingInterval()){
				batTaskInfo.setTaskPollingInterval(Integer.parseInt("0"));
			}

			int i = batchManageServ.insertTaskInfo(batTaskInfo);
			if (i < 1) {
				model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
				batchManageServ.transTimeFmtAdd(batchManageForm);
				return "sysrunner/BMG_TASK_Add";
			}
		} catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			batchManageServ.transTimeFmtAdd(batchManageForm);
			return "sysrunner/BMG_TASK_Add";
		}
		batchManageServ.transTimeFmtAdd(batchManageForm);
		model.addAttribute(ResultMessages.success().add("i.sysrunner.0006"));
		return "sysrunner/BMG_TASK_Add";
	}

	@Resource
	private BatchManageService batchManageServ;

}
