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

import com.synesoft.fisp.domain.model.SysRegionInfo;
import com.synesoft.ftzmis.app.model.FTZCMRegionQryForm;
import com.synesoft.ftzmis.domain.service.FTZCMRegionService;

/**
 * 国内地区代码查询
 * @author NB19
 *
 */
@Controller
@RequestMapping(value = "FTZCMRegionQry")
public class FTZCMRegionQryController {

	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FTZCMRegionService ftzcmRegionService;
	@ModelAttribute
	public FTZCMRegionQryForm setUpForm() {
		return new FTZCMRegionQryForm();
	}

	@RequestMapping("Qry")
	public String query(Model model, FTZCMRegionQryForm form,
			@PageableDefaults Pageable pageable) {
		logger.info("国内地区代码查询...");
		
		SysRegionInfo sysRegionInfo = new SysRegionInfo();
		sysRegionInfo.setRegionInfo(form.getRegionInfo());
		sysRegionInfo.setRegionNum(form.getRegionNum());
		
		Page<SysRegionInfo> page = ftzcmRegionService.queryRegionPage(pageable,sysRegionInfo);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("国内地区代码查询结束...");
			return "ftzmis/FTZCMRG_Qry";
		}
		logger.info("国内地区代码查询结束...");
		return "ftzmis/FTZCMRG_Qry";
	}
	
}
