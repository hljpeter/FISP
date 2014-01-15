package com.synesoft.fisp.app.sm.controller;


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
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.sm.model.OrgInfForm;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.service.sm.OrgInfMaintenanceService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "sm01")
public class SM_01_07Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(SM_01_07Controller.class);

	@ModelAttribute
	public OrgInfForm setUpForm() {
		return new OrgInfForm();
	}
	@RequestMapping("07/init")
	public String init() {
		logger.info("init...");
		return "sm/SM_01_07";
	}

	@RequestMapping("07/search")
	public String search(OrgInfForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		String orgId=StringUtil.trim(listForm.getOrgid());
		String orgName=StringUtil.trim(listForm.getOrgname());
		OrgInf orgInf=listForm.getOrgInf();
		if(orgInf==null){
			orgInf=new OrgInf();
		}
		orgInf.setOrgid(orgId);
		orgInf.setOrgname(orgName);
		
		Page<OrgInf> page= orgInfMaintenanceService.transQueryOrgInfList(pageable, orgInf);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.sm.0001")));
		}
		return "sm/SM_01_07";
	}
	
	@Autowired
	private OrgInfMaintenanceService orgInfMaintenanceService;

}
