package com.synesoft.sysrunner.app.BatchManage.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm;
import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm.BMG_DIM_Add;
import com.synesoft.sysrunner.domain.model.BatDimInfo;
import com.synesoft.sysrunner.domain.service.BatchManageService;

@Controller
@RequestMapping(value = "BMG_DIM_Add")
public class BMG_DIM_AddController {
	
	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public BatchManageForm setUpForm() {
		return new BatchManageForm();
	}
	
	@RequestMapping("Init")
	public String taskDimAddInit(BatchManageForm batchManageForm, Model model) {
		
		return "sysrunner/BMG_DIM_Add";
	}
	
	@RequestMapping("SubmitDim")
	public String dimAdd(
			@Validated({ BMG_DIM_Add.class }) BatchManageForm batchManageForm,
			BindingResult result, Model model,HttpServletRequest request) {
		if (result.hasErrors()) {
			return "sysrunner/BMG_DIM_Add";
		}
		
		BatDimInfo query_BatDimInfo = new BatDimInfo();
		query_BatDimInfo.setDimTypeId(batchManageForm.getBatDimInfo().getDimTypeId());
		query_BatDimInfo.setDimensionId(batchManageForm.getBatDimInfo().getDimensionId());
		BatDimInfo result_BatDimInfo = batchManageServ.queryBatDimInfo(query_BatDimInfo);
		if(null != result_BatDimInfo){
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0027"));
			return "sysrunner/BMG_DIM_Add";
		}else{
			batchManageServ.insertBatDimInfo(batchManageForm.getBatDimInfo());
			CommonUtil.reflashChangedSimgleCodelist("BMG_DIM_Type", request);
			model.addAttribute(ResultMessages.success().add("i.sysrunner.0013"));
		}
		return "sysrunner/BMG_DIM_Add";
	}
	
	@Resource
	private BatchManageService batchManageServ;
}
