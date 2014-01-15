package com.synesoft.fisp.app.dp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;

import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.dp.model.DpTableDtlDtlForm;
import com.synesoft.fisp.app.dp.model.DpTableDtlDtlForm.DpTableDtlDtlFormInit;
import com.synesoft.fisp.domain.model.DpTableDtl;
import com.synesoft.fisp.domain.service.dp.DpTableDtlService;

/**
 * 表定义维护-明细详细
 * @date 2013-11-15
 * @author yyw
 * 
 */

@Controller
@RequestMapping(value = "DP_Table_Dtl_Dtl")
public class DP_Table_Dtl_DtlController {

	private static final LogUtil log = new LogUtil(DP_Table_Dtl_DtlController.class);

	@Autowired
	private DpTableDtlService dpTableDtlService;

	@ModelAttribute
	public DpTableDtlDtlForm setForm() {
		return new DpTableDtlDtlForm();
	}
	
	@RequestMapping("Init")
	public String init(@Validated({DpTableDtlDtlFormInit.class}) DpTableDtlDtlForm form, 
			BindingResult result, Model model) {
		log.info("init...");

		// validate parameter in DpTableDtlForm
		if (result.hasErrors()) {
			log.error("[e.dp.table.0055] colId cannot be empty");
			return "dp/Dp_Table_Qry";
		}

		try {
			DpTableDtl dpTableDtl = dpTableDtlService.transQueryDpTableDtl(form.getDpTableDtl().getColId());
			
			form.setTableName(form.getTableName());
			form.setDpTableDtl(dpTableDtl);
			
		} catch (BusinessException e) {
			log.error("[e.dp.table] Failed to search the detail information, forward to error page");
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/Dp_Table_Dtl";
		}
		
		log.info("searching the detail information success, display it on the page");
		return "dp/Dp_Table_Dtl_Dtl";
	}
	

}
