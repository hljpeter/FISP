package com.synesoft.fisp.app.dp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;

import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.dp.model.DpTableDtlForm;
import com.synesoft.fisp.app.dp.model.DpTableDtlForm.DpTableDtlFormInit;
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.DpTableDtl;
import com.synesoft.fisp.domain.service.dp.DpTableService;

/**
 * 表定义维护-详细
 * @date 2013-11-15
 * @author yyw
 * 
 */

@Controller
@RequestMapping(value = "DP_Table_Dtl")
public class DP_Table_DtlController {

	private static final LogUtil log = new LogUtil(DP_Table_DtlController.class);

	@Autowired
	private DpTableService dpTableService;

	@ModelAttribute
	public DpTableDtlForm setForm() {
		return new DpTableDtlForm();
	}
	
	@RequestMapping("Init")
	public String init(@Validated({DpTableDtlFormInit.class}) DpTableDtlForm form, 
			BindingResult result, @PageableDefaults Pageable pageable, Model model) {
		log.info("init...");

		// validate parameter in DpTableDtlForm
		if (result.hasErrors()) {
			log.error("[e.dp.table.0027] tableId cannot be empty");
			return "dp/Dp_Table_Qry";
		}

		try {
			List<Object> list = dpTableService.transQueryDetail(pageable, form.getDpTable().getTableId());
			
			// getting object
			DpTable dpTable = (DpTable) list.get(0);
			dpTable.setCreateTime(DateUtil.getFormatdateAddSplit(dpTable.getCreateTime()));
			dpTable.setUpdateTime(DateUtil.getFormatdateAddSplit(dpTable.getUpdateTime()));
			
			@SuppressWarnings("unchecked")
			Page<DpTableDtl> page = (Page<DpTableDtl>) list.get(1);

			form.setDpTable(dpTable);
			model.addAttribute("page", page);
			
		} catch (BusinessException e) {
			log.error("[e.dp.table] Failed to search the detail information, forward to error page");
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/Dp_Table_Qry";
		}
		
		log.info("searching the detail information success, display it on the page");
		return "dp/Dp_Table_Dtl";
	}
	

}
