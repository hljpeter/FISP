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

import com.synesoft.fisp.app.dp.model.DP_Mpp_TableQryForm;
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.service.dp.DP_Mpp_Service;

@Controller
@RequestMapping("DP_Mpp_TableQry")
public class DP_Mpp_TableQryController {

	private static final Logger logger = LoggerFactory
			.getLogger(DP_Mpp_TableQryController.class);

	@ModelAttribute
	public DP_Mpp_TableQryForm setUpForm() {
		return new DP_Mpp_TableQryForm();
	}

	@RequestMapping("Init")
	public String init(Model model) {
		logger.info("init...");
		return "dp/DP_Mpp_TableQry";
	}

	@RequestMapping("Qry")
	public String query(Model model, DP_Mpp_TableQryForm form,
			@PageableDefaults Pageable pageable) {
		logger.info("query...");
		DpTable dpTable = new DpTable();
		dpTable.setTableName(form.getQuery_tableName());
		dpTable.setTableDesc(form.getQuery_tableDesc());

		Page<DpTable> page = dp_Mpp_Service.queryDpTablePage(pageable, dpTable);

		model.addAttribute("page", page);

		return "dp/DP_Mpp_TableQry";

	}

	@Resource
	private DP_Mpp_Service dp_Mpp_Service;

}
