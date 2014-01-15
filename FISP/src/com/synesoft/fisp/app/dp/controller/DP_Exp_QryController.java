package com.synesoft.fisp.app.dp.controller;

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

import com.synesoft.fisp.app.dp.model.DP_Exp_QryForm;
import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.service.dp.DP_Exp_Service;

@Controller
@RequestMapping("DP_Exp_Qry")
public class DP_Exp_QryController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(DP_Exp_QryController.class);

	@ModelAttribute
	public DP_Exp_QryForm setUpForm() {
		return new DP_Exp_QryForm();
	}

	@RequestMapping("Init")
	public String init(Model model, DP_Exp_QryForm form) {
		return "dp/DP_Exp_Qry";
	}
	
	@RequestMapping("Qry")
	public String query(Model model, DP_Exp_QryForm form,
			@PageableDefaults Pageable pageable) {
		logger.info("query...");

		// trans form to queryObject
		DpExpCfg dpExpCfg = new DpExpCfg();
		dpExpCfg.setBranchId(form.getQuery_branchId());
		dpExpCfg.setTableName(form.getQuery_tableName());
		dpExpCfg.setFileName(form.getQuery_fileName());

		// query DpMppCfg page list
		Page<DpExpCfg> page = dP_Exp_Service.queryDpExpCfgPage(pageable,
				dpExpCfg);

		if (page.getContent().size() > 0) {
			model.addAttribute("page", page);
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
		}

		return "dp/DP_Exp_Qry";
	}
	
	@RequestMapping("Del")
	public String del(Model model, DP_Exp_QryForm form) {
		logger.info("del...");
		// trans form to queryObject
		DpExpCfg dpExpCfg = new DpExpCfg();
		dpExpCfg.setExpId(form.getDel_ExpId());

		// check the mppCfg exist or not
		DpExpCfg result_DpExpCfg = dP_Exp_Service.queryDpExpCfgByExpId(dpExpCfg);

		if (null == result_DpExpCfg) {
			model.addAttribute(ResultMessages.info().add("e.dp.mpp.0001"));
			return "forward:/DP_Mpp_Qry/Qry";
		}

		// del mppCfg and mppCfgDtl
		int i = dP_Exp_Service.delDpExpCfg(dpExpCfg);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.dp.mpp.0002"));
			return "forward:/DP_Exp_Qry/Qry";
		}
		model.addAttribute(ResultMessages.success().add("i.dp.mpp.0001"));

		return "forward:/DP_Exp_Qry/Qry";
	}
	
	@Resource
	private DP_Exp_Service dP_Exp_Service;

}
