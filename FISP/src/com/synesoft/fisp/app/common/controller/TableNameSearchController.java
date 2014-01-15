package com.synesoft.fisp.app.common.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;

import com.synesoft.fisp.app.common.model.TableNameSearchForm;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.service.dp.DpTableService;

/**
 * 表名的查询
 * @date 2013-11-14
 * @author yyw
 * 
 */

@Controller
@RequestMapping(value = "search")
public class TableNameSearchController {

	private static final LogUtil log = new LogUtil(TableNameSearchController.class);

	@Autowired
	private DpTableService dpTableService;

	@ModelAttribute
	public TableNameSearchForm setForm() {
		return new TableNameSearchForm();
	}
	
	@RequestMapping("tablename/init")
	public String init() {
		log.info("init...");
		return "common/tablenamesearch";
	}
	
	@RequestMapping("tablename/search")
	public String search(TableNameSearchForm form, @PageableDefaults Pageable pageable, 
			Model model) {
		log.info("search tablename start...");
		
		// getting parameters from DpImpQryForm 
		DpTable dpTable = form.getDpTable();
		
		try {
			Page<DpTable> page = dpTableService.transQueryDpTablePage(pageable, dpTable);
		
			model.addAttribute("page", page);
		} catch (BusinessException e){
			log.info("[w.dp.0001] No data");
			model.addAttribute("infomsg", e.getResultMessages());
			return "common/tablenamesearch";
		}

		log.info("searching information success, dispaly result on the page");
		return "common/tablenamesearch";
	}

}
