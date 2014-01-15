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

import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm;
import com.synesoft.sysrunner.common.constant.CommonConst;
import com.synesoft.sysrunner.domain.service.BatchManageService;

@Controller
@RequestMapping(value = "BMG_STEP_CFG")
public class BMG_STEP_CFGController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public BatchManageForm setUpForm() {
		return new BatchManageForm();
	}

	@RequestMapping("Init")
	public String stepCfgInit(BatchManageForm batchManageForm, Model model) {
		DpImpCfg dic = new DpImpCfg();
		DpMppCfg dmc = new DpMppCfg();
		DpExpCfg dex = new DpExpCfg();
		List<DpImpCfg> dpImpCfgs = batchManageServ.queryDpImpCfgList(dic);
		List<DpMppCfg> dpMppCfgs = batchManageServ.queryDpMppCfgList(dmc);
		List<DpExpCfg> dpExpCfgs = batchManageServ.queryDpExpCfgList(dex);
		model.addAttribute("impList", dpImpCfgs);
		model.addAttribute("mppList", dpMppCfgs);
		model.addAttribute("expList", dpExpCfgs);

		model.addAttribute("impListFlag", true);

		model.addAttribute("mppListFlag", true);

		model.addAttribute("expListFlag", true);

		return "sysrunner/BMG_STEP_CFG";
	}

	@RequestMapping("Qry")
	public String stepCfgQry(BatchManageForm batchManageForm, Model model) {
		DpImpCfg dic = new DpImpCfg();
		DpMppCfg dmc = new DpMppCfg();
		DpExpCfg dex = new DpExpCfg();
		dic.setComments(batchManageForm.getQuery_cfg_name());
		dmc.setMppName(batchManageForm.getQuery_cfg_name());
		dex.setComments(batchManageForm.getQuery_cfg_name());
		List<DpImpCfg> dpImpCfgs = new ArrayList<DpImpCfg>();
		List<DpMppCfg> dpMppCfgs = new ArrayList<DpMppCfg>();
		List<DpExpCfg> dpExpCfgs = new ArrayList<DpExpCfg>();
		if (CommonConst.CFG_TYPE_IMPORT.equals(batchManageForm
				.getQuery_cfg_type())) {
			dpImpCfgs = batchManageServ.queryDpImpCfgList(dic);
			model.addAttribute("impListFlag", true);

		} else if (CommonConst.CFG_TYPE_MPPING.equals(batchManageForm
				.getQuery_cfg_type())) {
			dpMppCfgs = batchManageServ.queryDpMppCfgList(dmc);
			model.addAttribute("mppListFlag", true);
		} else if (CommonConst.CFG_TYPE_EXPORT.equals(batchManageForm
				.getQuery_cfg_type())) {
			dpExpCfgs = batchManageServ.queryDpExpCfgList(dex);
			model.addAttribute("expListFlag", true);
		} else {
			dpImpCfgs = batchManageServ.queryDpImpCfgList(dic);
			dpMppCfgs = batchManageServ.queryDpMppCfgList(dmc);
			dpExpCfgs = batchManageServ.queryDpExpCfgList(dex);
			model.addAttribute("impListFlag", true);
			model.addAttribute("mppListFlag", true);
			model.addAttribute("expListFlag", true);
		}

		model.addAttribute("impList", dpImpCfgs);
		model.addAttribute("mppList", dpMppCfgs);
		model.addAttribute("expList", dpExpCfgs);
		return "sysrunner/BMG_STEP_CFG";
	}

	@Resource
	private BatchManageService batchManageServ;

}
