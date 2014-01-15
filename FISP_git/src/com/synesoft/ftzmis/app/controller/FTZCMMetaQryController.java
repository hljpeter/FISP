package com.synesoft.ftzmis.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.domain.model.SysDataDict;
import com.synesoft.ftzmis.app.model.FTZCMMetaQryForm;
import com.synesoft.ftzmis.domain.service.FTZCMMetaService;


/**
 *  交易性质查询
 * @author NB19
 *
 */
@Controller
@RequestMapping(value = "FTZCMMetaQry")
public class FTZCMMetaQryController {

	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FTZCMMetaService ftzcmmetaservice;
	@ModelAttribute
	public FTZCMMetaQryForm setUpForm() {
		return new FTZCMMetaQryForm();
	}

	@RequestMapping("Qry")
	public String query(Model model, FTZCMMetaQryForm form,
			@PageableDefaults Pageable pageable) {
		logger.info("交易性质查询...");
		
		SysDataDict sysDataDict = new SysDataDict();		
		sysDataDict.setMetaName(form.getMetaName());
		sysDataDict.setMetaVal(form.getMetaVal());	
		Page<SysDataDict> page = ftzcmmetaservice.queryMetaPage(pageable,sysDataDict);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("交易性质查询结束...");
			return "ftzmis/FTZCMMeta_Qry";
		}
		logger.info("交易性质查询结束...");
		return "ftzmis/FTZCMMeta_Qry";
	}
	
}
