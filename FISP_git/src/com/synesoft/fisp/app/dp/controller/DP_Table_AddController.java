package com.synesoft.fisp.app.dp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.utils.JsonUtils;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.dp.model.DpTableAddForm;
import com.synesoft.fisp.app.dp.model.DpTableAddForm.DpTableAddFormAdd;
import com.synesoft.fisp.domain.model.DBTableInfo;
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.DpTableDtl;
import com.synesoft.fisp.domain.model.vo.DBTableInfoVO;
import com.synesoft.fisp.domain.service.DBTableInfoService;
import com.synesoft.fisp.domain.service.dp.DpTableService;

/**
 * 表定义维护-新增
 * @date 2013-11-15
 * @author yyw
 * 
 */
@Controller
@RequestMapping(value = "DP_Table_Add")
public class DP_Table_AddController {

	private static final LogUtil log = new LogUtil(DP_Table_AddController.class);

	@Autowired
	private DpTableService dpTableService;
	@Autowired
	private DBTableInfoService dbTableInfoService;
	
	@ModelAttribute
	public DpTableAddForm setForm() {
		return new DpTableAddForm();
	}
	
	@RequestMapping("Init")
	public String init(DpTableAddForm form, Model model) {
		log.info("init...");
		
		// only init the table information form database
		List<DBTableInfo> list = dbTableInfoService.transQueryTableList();
		form.setDbTableList(list);
		
		return "dp/Dp_Table_Add";
	}

	@RequestMapping("Add")
	public String add(@Validated({DpTableAddFormAdd.class}) DpTableAddForm form, BindingResult result, 
			Model model) {
		log.info("start add ...");

		// getting parameters from DpTableAddForm 
		DpTable dpTable = form.getDpTable();
		List<DpTableDtl> list = form.getDpTableList();
		
		try {
			// only init the table information form database
			List<DBTableInfo> dbList = dbTableInfoService.transQueryTableList();
			form.setDbTableList(dbList);
			
			dpTableService.transAdd(dpTable, list);
		} catch (BusinessException e) {
			log.error("[e.dp.imp] Failed to add record, forward to error page");
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/Dp_Table_Add";
		}

		log.info("[i.dp.table.0036] Add the table information success. result[tablename=" 
				+ dpTable.getTableName() + ", detail=" + (list == null? 0:list.size()) + "]");
		model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage
				.fromCode("i.dp.table.0036", dpTable.getTableName(), list == null? 0:list.size())));

		return "dp/Dp_Table_Add";
	}
	
	@RequestMapping("Add/searchsync")
	public @ResponseBody String search(@RequestParam("tableName") String tableName) {
		log.info("search filename start...");
		
		List<DBTableInfoVO> list = dbTableInfoService.transQueryTableColList(tableName);
		if (null == list) {
			return null;
		}

		try {
			return JsonUtils.listToJson(list);
		} catch (Exception e) {
			return null;
		}
	}
	
}
