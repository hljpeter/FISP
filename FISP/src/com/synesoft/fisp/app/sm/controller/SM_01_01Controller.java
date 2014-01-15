package com.synesoft.fisp.app.sm.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
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
public class SM_01_01Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(SM_01_01Controller.class);

	@ModelAttribute
	public OrgInfForm setUpForm() {
		return new OrgInfForm();
	}
	@RequestMapping("01/init")
	public String init() {
		logger.info("init...");
		return "sm/SM_01_01";
	}

	@RequestMapping("01/search")
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
		return "sm/SM_01_01";
	}

	@RequestMapping("01/del")
	public String del(OrgInfForm form,
			BindingResult result, Model model) {
		logger.info("start del ...");
		if (result.hasErrors()) {
			return "forward:/sm01/01/search";
		}
		try {
			orgInfMaintenanceService.transDel(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "forward:/sm01/01/search";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0003")));
		return "forward:/sm01/01/search";
	}
	
	@Autowired
	private OrgInfMaintenanceService orgInfMaintenanceService;

}
