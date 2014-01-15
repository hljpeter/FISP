package com.synesoft.sysrunner.app.BatchManage.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm;
import com.synesoft.sysrunner.domain.model.BatDimInfo;
import com.synesoft.sysrunner.domain.service.BatchManageService;

@Controller
@RequestMapping(value = "BMG_DIM_Qry")
public class BMG_DIM_QryController {
	
	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public BatchManageForm setUpForm() {
		return new BatchManageForm();
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("Qry")
	public String dimQry(BatchManageForm batchManageForm, @PageableDefaults Pageable pageable, Model model) {
		BatDimInfo query_batDimInfo = new BatDimInfo();
		Page page = batchManageServ.queryBatDimInfoByPage(pageable, query_batDimInfo);
		model.addAttribute("page",page);
		if (page.getContent().size() < 0) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			return "sysrunner/BMG_DIM_Qry";
		}
		
		return "sysrunner/BMG_DIM_Qry";
	}
	
	@RequestMapping("Del")
	public String dimDelete(BatchManageForm batchManageForm, Model model,HttpServletRequest request) {
		BatDimInfo del_batDimInfo = new BatDimInfo();
		del_batDimInfo.setDimTypeId(batchManageForm.getDel_dimTypeId());
		del_batDimInfo.setDimensionId(batchManageForm.getDel_dimensionId());
		
		int i = batchManageServ.deleteBatDimInfo(del_batDimInfo);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0023"));
			return "forward:/BMG_DIM_Qry/Qry";
		}
		CommonUtil.reflashChangedSimgleCodelist("BMG_DIM_Type", request);
		model.addAttribute(ResultMessages.success().add("i.sysrunner.0012"));
		return "forward:/BMG_DIM_Qry/Qry";
	}
	
	@RequestMapping("Dtl")
	public String dimDetail(@RequestParam("dimTypeId") String dimTypeId,@RequestParam("dimensionId") String dimensionId,BatchManageForm batchManageForm, Model model) {
		BatDimInfo query_batDimInfo = new BatDimInfo();
		query_batDimInfo.setDimTypeId(dimTypeId);
		query_batDimInfo.setDimensionId(dimensionId);
		BatDimInfo result_BatDimInfo = batchManageServ.queryBatDimInfo(query_batDimInfo);
		batchManageForm.setBatDimInfo(result_BatDimInfo);
		return "sysrunner/BMG_DIM_Dtl";
	}
	
	
	@Resource
	private BatchManageService batchManageServ;

}
