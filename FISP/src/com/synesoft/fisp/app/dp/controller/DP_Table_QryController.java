package com.synesoft.fisp.app.dp.controller;


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
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.dp.model.DpTableQryForm;
import com.synesoft.fisp.app.dp.model.DpTableQryForm.DpTableQryFormDel;
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.service.dp.DpTableService;

/**
 * 表定义维护-查询
 * @date 2013-11-15
 * @author yyw
 * 
 */

@Controller
@RequestMapping(value = "DP_Table_Qry")
public class DP_Table_QryController {

	private static final LogUtil log = new LogUtil(DP_Table_QryController.class);

	@Autowired
	private DpTableService dpTableService;

	@ModelAttribute
	public DpTableQryForm setForm() {
		return new DpTableQryForm();
	}
	
	@RequestMapping("Init")
	public String init() {
		log.info("init...");
		return "dp/Dp_Table_Qry";
	}

	@RequestMapping("Qry")
	public String search(DpTableQryForm form, @PageableDefaults Pageable pageable, Model model) {
		log.info("start search ...");
		
		// getting parameters from DpTableQryForm 
		DpTable DpTable = form.getDpTable();
		
		// search records, display result on the page
		try {
			Page<DpTable> page= dpTableService.transQueryDpTablePage(pageable, DpTable);
			model.addAttribute("page", page);
		} catch (BusinessException e){
			log.info("[w.dp.0001] No data");
			model.addAttribute("infomsg", e.getResultMessages());
			return "dp/Dp_Table_Qry";
		}

		log.info("searching information success, dispaly result on the page");
		return "dp/Dp_Table_Qry";
	}

	@RequestMapping("Del")
	public String del(@Validated({DpTableQryFormDel.class}) DpTableQryForm form, 
			BindingResult result, Model model) {
		log.info("start del ...");
		
		// validate parameter in DpTableForm
		if (result.hasErrors()) {
			log.error("[e.dp.table.0027] TableId cannot be empty");
			return "dp/Dp_Table_Qry";
		}

		try {
			dpTableService.transDel(form.getDpTable());
		} catch (BusinessException e) {
			log.error("[e.dp.table] Failed to delete record, forward to error page, param[id=" 
					+ form.getDpTable().getTableId() + "]");
			model.addAttribute("errmsg", e.getResultMessages());
			return "forward:/DP_Table_Qry/Qry";
		}
		
		log.info("[i.dp.table.0028] Deleting record success. param[id=" + form.getDpTable().getTableId() + "]");
		model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.dp.table.0028")));
		
		return "forward:/DP_Table_Qry/Qry";
	}
	
}
