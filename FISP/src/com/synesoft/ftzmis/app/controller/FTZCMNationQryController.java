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

import com.synesoft.ftzmis.app.model.FTZCMNationQryForm;
import com.synesoft.ftzmis.domain.model.SysNationInfo;
import com.synesoft.ftzmis.domain.service.FTZCMNationService;


/**
 *  国别代码查询
 * @author NB19
 *
 */
@Controller
@RequestMapping(value = "FTZCMNationQry")
public class FTZCMNationQryController {

	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FTZCMNationService ftzcmnattionservice;
	@ModelAttribute
	public FTZCMNationQryForm setUpForm() {
		return new FTZCMNationQryForm();
	}

	@RequestMapping("Qry")
	public String query(Model model, FTZCMNationQryForm form,
			@PageableDefaults Pageable pageable) {
		logger.info("国别代码查询...");
		
		SysNationInfo sysNationInfo = new SysNationInfo();		
		sysNationInfo.setNationShortName(form.getNationShortName()); //设置查询条件
		sysNationInfo.setNationCode(form.getNationCode());
		sysNationInfo.setNationNumThree(form.getNationNumThree());
		
		Page<SysNationInfo> page = ftzcmnattionservice.queryMetaPage(pageable,sysNationInfo);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("国别代码查询结束...");
			return "ftzmis/FTZCMNation_Qry";
		}
		logger.info("国别代码查询结束...");
		return "ftzmis/FTZCMNation_Qry";
	}
	
}
