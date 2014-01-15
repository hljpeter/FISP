package com.synesoft.fisp.app.dp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.dp.model.DpTableMdfForm;
import com.synesoft.fisp.app.dp.model.DpTableMdfForm.DpTableMdfFormInit;
import com.synesoft.fisp.domain.model.DBTableInfo;
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.DpTableDtl;
import com.synesoft.fisp.domain.service.DBTableInfoService;
import com.synesoft.fisp.domain.service.dp.DpTableService;

/**
 * 表定义维护-修改
 * @date 2013-11-18
 * @author yyw
 * 
 */
@Controller
@RequestMapping(value = "DP_Table_Mdf")
public class DP_Table_MdfController {

	private static final LogUtil log = new LogUtil(DP_Table_MdfController.class);

	@Autowired
	private DpTableService dpTableService;
	@Autowired
	private DBTableInfoService dbTableInfoService;
	
	@ModelAttribute
	public DpTableMdfForm setForm() {
		return new DpTableMdfForm();
	}
	
	@RequestMapping("Init")
	public String init(@Validated({DpTableMdfFormInit.class}) DpTableMdfForm form, 
			BindingResult result, Model model) {
		log.info("init...");

		// validate parameter in DpTableMdfForm
		if (result.hasErrors()) {
			log.error("[e.dp.table.0027] tableId cannot be empty");
			return "dp/Dp_Table_Mdf";
		}

		try {
			// search the table information
			List<Object> list2 = dpTableService.transQueryDetailForUpt(form.getDpTable().getTableId());
			DpTable dpTable = (DpTable) list2.get(0);
			Boolean flag = (Boolean) list2.get(2);
			if (!flag) {
				model.addAttribute("infomsg", ResultMessages.success().add(ResultMessage
						.fromCode("e.dp.table.0037", dpTable.getTableName())));
			}
	
			// search table table in database
			DBTableInfo dbTableInfo = dbTableInfoService.transQueryTable(dpTable.getTableName());
			if (null == dbTableInfo) {
				model.addAttribute("errmsg", ResultMessages.success().add(ResultMessage
						.fromCode("e.dp.table.0032", dpTable.getTableName())));
			}
			
			form.setDbTableInfo(dbTableInfo);
			form.setDpTable(dpTable);
			form.setDpTableList((List<DpTableDtl>) list2.get(1));
			form.setFlag(flag);
		} catch (BusinessException e) {
			log.error("[e.dp.table] Failed to search the detail information, forward to error page");
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/Dp_Table_Mdf";
		}
		
		log.info("searching the detail information success, display it on the page");
		return "dp/Dp_Table_Mdf";
	}

	@RequestMapping("Mdf")
	public String mdf(DpTableMdfForm form, Model model) {
		log.info("start mdf ...");

		// getting parameters from DpTableMdfForm 
		DpTable dpTable = form.getDpTable();
		List<DpTableDtl> list = form.getDpTableList();
		
		try {
			dpTableService.transUpt(dpTable, list);
		} catch (BusinessException e) {
			log.error("[e.dp.imp] Failed to update record, forward to error page");
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/Dp_Table_Mdf";
		}

		log.info("[i.dp.table.0039] updating record success. ");
		model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage
				.fromCode("i.dp.table.0039", dpTable.getTableName(), list == null? 0:list.size())));
		return "dp/Dp_Table_Mdf";
	}
	
}
