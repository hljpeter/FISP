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

import com.synesoft.fisp.app.dp.model.DP_Mpp_QryForm;
import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.fisp.domain.service.dp.DP_Mpp_Service;

@Controller
@RequestMapping("DP_Mpp_Qry")
public class DP_Mpp_QryController {

	private static final Logger logger = LoggerFactory
			.getLogger(DP_Mpp_QryController.class);

	@ModelAttribute
	public DP_Mpp_QryForm setUpForm() {
		return new DP_Mpp_QryForm();
	}

	@RequestMapping("Init")
	public String init(Model model) {
		logger.info("init...");
		return "dp/DP_Mpp_Qry";
	}

	@RequestMapping("Qry")
	public String query(Model model, DP_Mpp_QryForm form,
			@PageableDefaults Pageable pageable) {
		logger.info("query...");

		// trans form to queryObject
		DpMppCfg dpMppCfg = new DpMppCfg();
		dpMppCfg.setBranchId(form.getQuery_branchId().trim());
		dpMppCfg.setProcType(form.getQuery_procType().trim());
		dpMppCfg.setDestTable(form.getQuery_destTable().trim());
		dpMppCfg.setSrcTable(form.getQuery_srcTable().trim());
		dpMppCfg.setMppName(form.getQuery_mppName().trim());

		// query DpMppCfg page list
		Page<DpMppCfg> page = dp_Mpp_Service.queryDpMppCfgPage(pageable,
				dpMppCfg);

		if (page.getContent().size() > 0) {
			model.addAttribute("page", page);
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			return "dp/DP_Mpp_Qry";
		}

		return "dp/DP_Mpp_Qry";
	}

	@RequestMapping("Del")
	public String del(Model model, DP_Mpp_QryForm form) {
		logger.info("del...");
		// trans form to queryObject
		DpMppCfg dpMppCfg = new DpMppCfg();
		dpMppCfg.setMppId(form.getDel_MppId());

		// check the mppCfg exist or not
		DpMppCfg result_DpMppCfg = dp_Mpp_Service
				.queryDpMppCfgByMppId(dpMppCfg);

		if (null == result_DpMppCfg) {
			model.addAttribute(ResultMessages.error().add("e.dp.mpp.0001"));
			return "forward:/DP_Mpp_Qry/Qry";
		}

		// del mppCfg and mppCfgDtl
		int i = dp_Mpp_Service.delMppCfg(dpMppCfg);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.dp.mpp.0002"));
			return "forward:/DP_Mpp_Qry/Qry";
		}
		
		model.addAttribute(ResultMessages.success().add("i.dp.mpp.0001"));
		return "forward:/DP_Mpp_Qry/Qry";
	}

	@Resource
	private DP_Mpp_Service dp_Mpp_Service;

}
